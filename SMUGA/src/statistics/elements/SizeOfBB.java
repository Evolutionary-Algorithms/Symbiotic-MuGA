/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.elements;

import genetic.Solver.Symbiosis;
import genetic.SolverUtils.SimpleSolver;
import genetic.SolverUtils.Symbiosis.Parasite;
import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;
import problem.bitString.Deceptive.Deceptive;

/**
 *
 * @author manso
 */
public class SizeOfBB extends AbstractStatsElement {

    public SizeOfBB() {
    }

    public SizeOfBB(SimpleSolver s) {
        execute(s);
    }

    public double execute(SimpleSolver solver) {
        double meanSize = 0;
        int num = 0;
        try {
            Symbiosis s = (Symbiosis) solver;
            Population parasites = s.parasitesPop;
            Deceptive problem = (Deceptive) s.getTemplate();

            Iterator<Individual> it = parasites.getIterator();
            while (it.hasNext()) {
                Parasite p = (Parasite) it.next();
                if (problem.isParasiteGood(p)) {
                    meanSize += p.sizeInBits();
                    num++;
                }
            }
            if (num > 0) {
                meanSize = meanSize / num;
            }
        } catch (Exception ex) {
            meanSize = 0;
        }
        setValue(meanSize);
        return getValue();
    }

    public String toString() {
        return getName() + getValue();
    }

    @Override
    public String getName() {
        return "Size of Parasite Building Blocks";
    }

    /**
     * specify if the higher value is better used in mean comparison from
     * statistics
     *
     * @return true if higher value is better
     */
    public boolean isMaximumBetter() {
        return Individual.typeOfOptimization;
    }

}
