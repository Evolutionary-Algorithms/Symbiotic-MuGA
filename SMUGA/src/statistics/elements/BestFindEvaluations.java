/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.elements;

import genetic.SolverUtils.SimpleSolver;

/**
 *
 * @author manso
 */
public class BestFindEvaluations extends AbstractStatsElement {

    boolean bestFound = false;

    public BestFindEvaluations() {
    }

    @Override
    public double execute(SimpleSolver s) {
        //get last Element
        BestFindEvaluations last = (BestFindEvaluations) s.getStats().getLastElement(this);
        // if best is not reached
        if (last == null || !last.bestFound) {
            setValue(s.EVALUATIONS);
            int val = s.getHallOfFame().getNumberOFBestIndividuals();
            if (val > 0) {
                bestFound = true;
            }
        } else {
            setValue(last.value);
            bestFound = true;
        }

        return getValue();
    }

    public String toString() {
        return "Evaluations to Find Best";
    }

    @Override
    public String getName() {
        return toString();
    }
     /**
     * specify if the higher value is better
     * used in mean comparison from statistics
     * @return true if higher value is better
     */
    public boolean isMaximumBetter(){
        return false;
    }
}
