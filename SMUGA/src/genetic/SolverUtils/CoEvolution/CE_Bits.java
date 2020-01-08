/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.SolverUtils.CoEvolution;

import genetic.SolverUtils.SimpleSolver;
import genetic.population.Population;
import java.util.Iterator;
import java.util.Random;
import operator.mutation.bitString.M_FlipBitWave;
import operator.recombination.bitString.M_XHarem;
import operator.replacement.Decimation;
import operator.rescaling.AdaptiveCeiling;
import operator.selection.Tournament;
import problem.Individual;
import problem.bitString.Deceptive.Deceptive;
import utils.Array;
import utils.BitField;

/**
 *
 * @author Antonio
 */
public class CE_Bits extends SimpleSolver {

    Individual templateProblem = new Deceptive();
    public int[] positions;
    Random random = new Random();
    public Individual bestIndividualFound = null;

    public CE_Bits(Individual problem, int SIZE_POP) {


        //--------------------------------------------------
        setSelect(new Tournament(SIZE_POP, 4));
        setRecombine(new M_XHarem());
        setMutate(new M_FlipBitWave());
        setReplace(new Decimation());
        setRescaling(new AdaptiveCeiling());
        //--------------------------------------------------
        this.templateProblem = problem;
        select.setParameters(SIZE_POP + " " + 4);
        recombine.setParameters("0.5");
        rescale.setParameters("3");
        setProblem(SIZE_POP, problem);
        parents.createRandomPopulation(SIZE_POP, problem);
        parents.evaluate();
        bestIndividualFound = parents.getBestGenotype();
        EVALUATIONS = 0;
    }

    public final void setProblem(int SIZE_POP, Individual ind) {
        this.templateProblem = ind;
        positions = Array.createLinearArray(ind.getGenome().getNumberTotalOfBits());
        updateStructure(positions);
    }

    public void removeStructure(Individual ind) {
        BitField original = ind.getGenome().getBinString();
        BitField newI = new BitField(original.getNumberOfBits());
        for (int i = 0; i < positions.length; i++) {
            newI.setBit(positions[i], original.getBit(i));
        }
        ind.setBits(newI);
        //debug
        ind.setIsEvaluated(true);
    }

    public void updateStructure(int[] pos) {
        if( utils.Array.equals(pos, positions)){
            System.out.print(" #");
            return;
        }
        Population newPop = parents.getCleanCopie();
        double bestOld = parents.getBestGenotype().getFitness();
        Iterator<Individual> it = parents.getIterator();
        while (it.hasNext()) {
            Individual ind = it.next();
            changeStructure(ind, pos);
            ind.evaluate();
            EVALUATIONS++;
            newPop.addGenotype(ind);
        }
        System.arraycopy(pos, 0, positions, 0, pos.length);
        parents = newPop;
        double bestNew = parents.getBestGenotype().getFitness();
        if(bestNew < bestOld ){
            System.out.println("Catastrofe :" + ( bestNew / bestOld)*100 + "%");
        }else if(bestNew == bestOld ){
            System.out.println(" =");
        }else{
            System.out.println(" +");
        }
    }

    private void changeStructure(Individual ind, int[] pos) {
//        Cooperation.changeColaboration(ind, positions, pos);
        changeStructure(ind, positions, pos);
    }

    private void changeStructure(Individual bits, int[] applied, int[] toApply) {
//         Cooperation.changeColaboration(bits, applied, toApply);
        BitField original = bits.getStringBits();
        BitField ordered = new BitField(original.getNumberOfBits());
        //remove old colaboration
        for (int i = 0; i < ordered.getNumberOfBits(); i++) {
            ordered.setBit(applied[i], original.getBit(i));
        }
        //apply new colaboration
        for (int i = 0; i < ordered.getNumberOfBits(); i++) {
            original.setBit(i, ordered.getBit(toApply[i]));
        }
        //change bit structure of individual
        bits.setBits(original);
    }
    ///////////////////////////////////////////////////////////////////////////
    //-----------------------------------------------------------------------//
    ///////////////////////////////////////////////////////////////////////////    

    public void evolve(int iterations) {
        Individual best = parents.getBestGenotype();
//        Debug.print("\n BITS [" + best.getFitness() + "]");
        while (iterations > 0) {
            parents = evolve(parents);
            best = parents.getBestGenotype();
//            Debug.print("[" + best.getFitness() + "]");
            iterations--;
        }
//        Debug.println("");
    }

    @Override
    public Population evolve(Population original) {
        offspring = select.execute(parents);
        offspring = recombine.execute(offspring);
        offspring = mutate.execute(offspring);

        offspring.evaluate();
        EVALUATIONS += offspring.getNumGenotypes();
        parents = replace.execute(parents, offspring);
        parents = rescale.execute(parents);
        return parents;
    }

    @Override
    public String getAlgorithm() {
        return " Coevolution Bit Solver";
    }
}
