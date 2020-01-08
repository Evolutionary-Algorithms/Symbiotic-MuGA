/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.stopCriteria;

import genetic.SolverUtils.SimpleSolver;

/**
 *
 * @author manso
 */
public class Evaluation extends StopCriteria {

    boolean done = false;

    public Evaluation(int maxEvaliations) {
        super(maxEvaliations);
         done = false;
    }

    public Evaluation() {
        this(250000);
    }

    @Override
    public void updateValue(SimpleSolver s) {
        //set current value
        setCurrentValue(s.EVALUATIONS);
    }

    @Override
    public boolean isDone(SimpleSolver s) {    
        updateValue(s);
        // System.out.println("EVALUATIONS " + getMaxValue() + " <= " + getCurrentValue() + " = " + (getMaxValue() <= getCurrentValue()));
        return done || getMaxValue() <= getCurrentValue();
    }

    @Override
    public String getName() {
        return "Evaluations";
    }

    @Override
    public void stop() {
        done = true;
    }
    
    @Override
    public void reset() {
        done = false;
        this.currentValue=0;
    }
}
