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
public class NoStop extends StopCriteria {

   
    public boolean isStoped;
    public NoStop() {
        super(1E200);
         isStoped = false;
    }

    @Override
    public void updateValue(SimpleSolver s) {
        //set current value
        
    }

    @Override
    public boolean isDone(SimpleSolver s) {
        updateValue(s);
        return isStoped;
    }
    @Override
    public void stop() {
       isStoped = false;
    }
}
