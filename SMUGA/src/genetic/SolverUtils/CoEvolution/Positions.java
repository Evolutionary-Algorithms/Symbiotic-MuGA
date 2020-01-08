/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.SolverUtils.CoEvolution;

import java.util.Arrays;
import problem.Individual;
import problem.PRM_Individual;
import problem.bitString.Deceptive.Deceptive;
import utils.BitField;

/**
 *
 * @author Zulu
 */
public class Positions extends PRM_Individual{
    public static Individual template = new Deceptive();
    public  BitField bestBits ;
    private  Individual evaluator ;
    
    

    public Positions() {
        super(MAXIMIZE, template.getGenome().getNumberTotalOfBits());
        evaluator = template.getClone();
        bestBits = evaluator.getStringBits();
    }
    
    public void setLinearPositions(){
        for (int i = 0; i < genome.length; i++) {
            genome[i]=i;            
        }
    }
    public void setEvaluator(Individual ind){
        evaluator = ind.getClone();
        bestBits = evaluator.getStringBits();
        isEvaluated=false;
    }
    

    @Override
    protected double fitness() {
        getEvaluator();
        return evaluator.getFitness();
    }       
    
    public Individual getEvaluator(){
        BitField bits = new BitField(genome.length);
        for (int i = 0; i < genome.length; i++) {
           bits.setBit(i,bestBits.getBit(genome[i]));
        }
        evaluator.setBits(bits);
        evaluator.evaluate();
        return evaluator;
    }
    
    @Override
    public String toStringGenotype(){
        StringBuilder txt = new StringBuilder();
        txt.append(evaluator.toStringGenotype());
        txt.append(" \t ");
        txt.append(Arrays.toString(genome));
        return txt.toString();
    }
    @Override
    public String toStringPhenotype(){
        StringBuilder txt = new StringBuilder();
        txt.append(evaluator.toStringPhenotype());
        txt.append(" \t ");
        txt.append(Arrays.toString(genome));
        return txt.toString();
    }
      
    @Override
    public Positions getClone(){
        Positions clone = (Positions)super.getClone();
        clone.evaluator = evaluator.getClone();
        clone.bestBits = bestBits;
        return clone;
    }
    
}
