/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.SolverUtils.CoEvolution;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.util.Iterator;
import operator.mutation.Mutation;
import operator.mutation.permutation.M_SwapGenes;
import operator.recombination.Recombination;
import operator.recombination.permutation.PMX;
import operator.replacement.Decimation;
import operator.replacement.Replacement;
import operator.rescaling.AdaptiveCeiling;
import operator.rescaling.Rescaling;
import operator.selection.Selection;
import operator.selection.Tournament;
import problem.Individual;
import utils.BitField;

/**
 *
 * @author Antonio
 */
public class CE_Positions {

    BitField bestBits;
    Individual evaluator;
    public int EVALUATIONS = 0;
    Selection select = new Tournament();
    Recombination crossover = new PMX();
    Mutation mutation = new M_SwapGenes();
//    Replacement replace = new Decimation();
    Rescaling rescale = new AdaptiveCeiling();
    public Population parents = new MultiPopulation();
    Population offspring = new MultiPopulation();
//    public Positions bestIndividual;
    boolean elitism = false;

    public CE_Positions(Individual template, int SIZE_POP, boolean elitism) {
        Positions.template = template;
        bestBits = template.getStringBits();
        this.elitism = elitism;

        select.setParameters(SIZE_POP / 3 + " " + 4);
        crossover.setParameters("0.5");
        mutation.setProbability(0.01);
        rescale.setParameters("2");
//        replace.setParameters(SIZE_POP / 3 + " " + 4);
        createFirstPopulation(SIZE_POP);
        Positions linear = new Positions();
        linear.setLinearPositions();
        parents.addGenotype(linear);
        setTemplate(template);

    }

    private void createFirstPopulation(int SIZE_POP) {
        Positions ind = new Positions();
        int[] linear = utils.Array.createLinearArray(ind.getNumGenes());
        ind.setGeneValues(linear);
        parents.clear();
        parents.addGenotype(ind);
        while (parents.size() < SIZE_POP) {
            Positions pos = (Positions) parents.getRandomGenotype().getClone();
            mutation.doMutation(pos);
            parents.addGenotype(pos);
        }
//        System.out.println("PARENTS " + parents);

    }

    public double evaluate(Positions ind) {
        int[] pos = ind.getGeneValues();
        BitField bits = new BitField(pos.length);
        for (int i = 0; i < pos.length; i++) {
            bits.setBit(i, bestBits.getBit(pos[i]));
        }
        evaluator.setBits(bits);
        evaluator.evaluate();
        EVALUATIONS++;
        return evaluator.getFitness();
    }

    public final void setTemplate(Individual ind) {
        //sao iguais
        if (bestBits.equals(ind.getStringBits())) {
            return;
        }

        this.evaluator = ind.getClone();
        this.bestBits = ind.getStringBits().getClone();

        Iterator<Individual> it = parents.getIterator();
        while (it.hasNext()) {
            Positions p = (Positions) it.next();
            p.setFitness(evaluate(p));
        }
//        Individual best = parents.getBestGenotype();
//        System.out.println(" \t Best IND " + ind.getFitness() + " Best POS :" + best.getFitness());
    }

//   public void evolve(){
//       offspring = select.execute(parents);
//       //offspring = crossover.execute(offspring);
//       offspring = mutation.execute(offspring);
//       parents = replace.evaluteAndReplace(parents, offspring);
//       parents = rescale.execute(parents);
//   }
    public void evolve() {
        offspring = parents.getCleanCopie();
        //size of original population
        int sizePop = parents.getNumGenotypes();
        //select best individuals
        Population selected = select.execute(parents);
        offspring.appendPopulation(selected);
        if (elitism) {
            offspring.addGenotype(parents.getBestGenotype().getClone());
        }
        //complete population
        int index = 0;
//        mutation.setProbability(4.0/parents.getGenotype(0).getNumGenes());
        while (offspring.getNumGenotypes() < sizePop) {
            //select one individual sequencially
            index = (index + 1) % selected.size();
            Individual mutant = offspring.getGenotype(index);
            for (int i = 0; i < mutant.getNumCopies(); i++) {
                Positions ind = (Positions) mutant.getClone();
                ind.setNumCopys(i + 1);
//            mutant.setNumCopys(1);
                //apply mutation
                mutation.doMutation(ind);
//                MutationPositions.mutate(ind, ind.bestBits);
                if (ind.evaluate()) {
                    EVALUATIONS++;
                }
                ind.setNumCopys(1);
                //introduce individual in the new population
                if (offspring.getNumGenotypes() < sizePop) {
                    offspring.addIndividual(ind);
                } else {
                    break;
                }
            }
        }
        //rescale number of copies
        parents = rescale.execute(offspring);
    }

    public void evolve(int iterations) {
        for (int i = 0; i < iterations; i++) {
            evolve();
        }
    }
}
