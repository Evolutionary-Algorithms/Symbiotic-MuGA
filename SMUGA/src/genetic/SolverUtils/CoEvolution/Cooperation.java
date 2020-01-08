/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.SolverUtils.CoEvolution;

import genetic.population.Population;
import java.util.Arrays;
import java.util.Iterator;
import problem.Individual;
import problem.PRM_Individual;
import problem.bitString.BinInt;
import utils.BitField;

/**
 *
 * @author Antonio
 */
public class Cooperation {

    /**
     * Avalia a colaboraç?o entre um individuo e uma posicao
     *
     * @param bits individuo com os bits
     * @param order individuo com as positions
     * @return valor da colaboracao
     */
    public static double fitness(Individual bits, PRM_Individual order) {
        BitField original = bits.getStringBits();
        BitField ordered = new BitField(original.getNumberOfBits());
        int[] positions = order.getGeneValues();
        for (int i = 0; i < ordered.getNumberOfBits(); i++) {
            ordered.setBit(i, original.getBit(positions[i]));
        }
        Individual evaluator = bits.getClone();
        evaluator.setBits(ordered);
        evaluator.evaluate();
        return evaluator.getFitness();
    }

    /**
     * calcula a colaboraç?o entre um individuo e uma posicao
     *
     * @param bits individuo com os bits
     * @param order individuo com as positions
     * @return colaboracao
     */
    public static Individual applyColaboration(Individual bits, PRM_Individual order) {
        BitField original = bits.getStringBits();
        BitField ordered = new BitField(original.getNumberOfBits());
        int[] positions = order.getGeneValues();
        for (int i = 0; i < ordered.getNumberOfBits(); i++) {
            ordered.setBit(i, original.getBit(positions[i]));
        }
        Individual evaluator = bits.getClone();
        evaluator.setBits(ordered);
        return evaluator;
    }

    /**
     * calcula a colaboracao entre um individuo e uma posicao
     *
     * @param bits individuo com os bits
     * @param order individuo com as positions
     * @return colaboracao
     */
    public static Individual removeColaboration(Individual bits, PRM_Individual order) {
        BitField original = bits.getStringBits();
        BitField ordered = new BitField(original.getNumberOfBits());
        int[] positions = order.getGeneValues();
        for (int i = 0; i < ordered.getNumberOfBits(); i++) {
            ordered.setBit(positions[i], original.getBit(i));
        }
        Individual evaluator = bits.getClone();
        evaluator.setBits(ordered);
        return evaluator;
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    /**
     * remove a colaboracao entre um individuo e uma posicao
     *
     * @param bits individuo com os bits
     * @param order individuo com as positions
     * @return colaboracao
     */
    public static void removeColaboration(Individual bits, int[] positions) {
        BitField original = bits.getStringBits();
        BitField ordered = new BitField(original.getNumberOfBits());
        for (int i = 0; i < ordered.getNumberOfBits(); i++) {
            ordered.setBit(positions[i], original.getBit(i));
        }
        bits.setBits(ordered);
    }

    /**
     * calcula a colaboraç?o entre um individuo e uma posicao
     *
     * @param bits individuo com os bits
     * @param order individuo com as positions
     * @return colaboracao
     */
    public static void applyColaboration(Individual bits, int[] positions) {
        BitField original = bits.getStringBits();
        BitField ordered = new BitField(original.getNumberOfBits());
        for (int i = 0; i < ordered.getNumberOfBits(); i++) {
            ordered.setBit(i, original.getBit(positions[i]));
        }
        bits.setBits(ordered);
    }
    public static void changeColaboration(Individual bits, int[]applied, int []toApply) {
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
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    public static Population applyColaboration(Population pop, PRM_Individual order) {
        Population ordered = pop.getCleanCopie();
        Iterator<Individual> it = pop.getIterator();
        while (it.hasNext()) {
            ordered.addGenotype(applyColaboration(it.next(), order));
        }
        return ordered;
    }

    public static Population removeColaboration(Population pop, PRM_Individual order) {
        Population ordered = pop.getCleanCopie();
        Iterator<Individual> it = pop.getIterator();
        while (it.hasNext()) {
            ordered.addGenotype(removeColaboration(it.next(), order));
        }
        return ordered;
    }

    public static void main(String[] args) {
        Individual ind = new BinInt(100);
        int [] linear = utils.Array.createLinearArray(100);
        int shuffled[] = utils.Array.createLinearArray(100);
        utils.Array.shuffle(shuffled);

        ind.evaluate();
        System.out.println("\n\nORIGINAL " + ind); 
        
        applyColaboration(ind, shuffled);
        ind.evaluate();
        System.out.println("REVERSE  " + ind + "\nORDER " + Arrays.toString(shuffled)); 
        changeColaboration(ind, shuffled,linear);
        ind.evaluate();
        System.out.println("ORIGINAL " + ind); 
        
      



    }
}
