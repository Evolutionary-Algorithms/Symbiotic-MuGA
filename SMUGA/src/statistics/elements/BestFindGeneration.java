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
public class BestFindGeneration extends AbstractStatsElement {
     boolean bestFound = false;
    public BestFindGeneration() {
    }

    @Override
    public double execute(SimpleSolver s) {
       //get last Element
         BestFindGeneration last =  (BestFindGeneration)s.getStats().getLastElement(this);
        // if best is not reached
        if (last == null || !last.bestFound) {
            setValue(s.GENERATION );
            int val = s.getHallOfFame().getNumberOFBestIndividuals();
            if (val > 0) {
                bestFound = true;
            }
        }else{
             setValue(last.value);
             bestFound = true;
        }

        return getValue();
    }

     @Override
    public String toString() {
        return "Generations to Find Best";
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
