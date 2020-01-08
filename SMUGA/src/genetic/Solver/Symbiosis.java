/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.Solver;

import genetic.SolverUtils.Symbiosis.SymbioticCollaboration;
import genetic.SolverUtils.Symbiosis.Parasite;
import genetic.population.Population;
import java.util.ArrayList;
import java.util.StringTokenizer;
import operator.selection.Selection;
import operator.selection.Tournament;

/**
 *
 * @author Zulu
 */
public class Symbiosis extends Genetic_Algorithm {

    public static int ITERATIONS_ISOLATION = 16;
    public static int SIZE_OF_PARASITES = 32;
    public static int SELECTED_POPULATION = 16;
    //population of parasites
    public Population parasitesPop;
    //population of symbionts
    Population symbionts;
    //dabase de Genes
    SymbioticCollaboration collaboration;

    int iterationsInIsolation = ITERATIONS_ISOLATION;
    int sizeOfParasites = SIZE_OF_PARASITES;
    int selectedPopulation = SELECTED_POPULATION;

    public Population getParasites() {
        return parasitesPop;
    }

    @Override
    public void setPopulation(Population pop) {
        super.setPopulation(pop);
        int SIZE_OF_HOST = parents.getGenotype(0).getStringBits().size();
        parasitesPop = pop.getCleanCopie();
        parasitesPop.createPopulation(sizeOfParasites, new Parasite(SIZE_OF_HOST));
        //--------Evalaution and Symbiosis -------------------------
        collaboration = new SymbioticCollaboration();
        parents.evaluate();
        //----------------------------------------------

    }

    @Override
    public void restartSolver() {

        super.restartSolver();
        int SIZE_OF_HOST = parents.getGenotype(0).getStringBits().size();
        parasitesPop = parents.getCleanCopie();
        parasitesPop.createPopulation(sizeOfParasites, new Parasite(SIZE_OF_HOST));
        //--------GENE DATABASE -------------------------
        collaboration = new SymbioticCollaboration();
        parents.evaluate();
//        geneDB.insert(parents);
        collaboration.evaluateParasites(parasitesPop, parents);
        //----------------------------------------------
        resetStats();
    }

    /**
     * recombine parasites
     *
     * @param pop
     * @return
     */
    public Population recombine(Population pop) {
        Population newPop = pop.getCleanCopie();
        while (pop.getNumGenotypes() > 0) {
            Parasite p1 = (Parasite) pop.removeRandomIndividual().getClone();
            if (pop.getNumGenotypes() == 0) {
                newPop.addGenotype(p1);
                break;
            }
            Parasite p2 = (Parasite) pop.removeRandomIndividual().getClone();
            //try to join individuals
            p1.join(p2);
            if (p1 != null) {
                newPop.addGenotype(p1);
            }
            if (p2 != null) {
                newPop.addGenotype(p2);
            }
        }

        return newPop;
    }

    public void evolveParasites() {
        Selection sel = new Tournament((int) (parasitesPop.size() / 2), 3);
//        best genotypes
        Population selPAr = sel.execute(parasitesPop, (int) (parasitesPop.size() / 2));
        Population offParasites = recombine(selPAr);
        offParasites.appendPopulation(selPAr);
        while (offParasites.size() < parasitesPop.size()) {
            //parasita selecionado
            Parasite p = (Parasite) offParasites.getRandomGenotype().getClone();
            //mutantes do parasita
            ArrayList<Parasite> mutants = p.mutate();
            for (Parasite mut : mutants) {
                if (!offParasites.contains(mut)) {
                    offParasites.addGenotype(mut);
                }
            }
        }
        offParasites = rescale.execute(offParasites);
        collaboration.evaluateParasites(parasitesPop, parents);
        collaboration.evaluateParasites(offParasites, parents);
        parasitesPop = replace.execute(parasitesPop, offParasites);
        parasitesPop = rescale.execute(parasitesPop);

    }

    public Population evolveHosts(Population original) {
        selected = select.execute(original);
        offspring = recombine.execute(selected.getClone());
        offspring = mutate.execute(offspring);
        offspring = reparation.execute(offspring);
        offspring.evaluate();
        parents = replace.execute(original, offspring);
        parents = rescale.execute(parents);
        //-----------------------------------------E V A L U A T I O N S------
        EVALUATIONS += offspring.getEvaluations();
        //-----------------------------------------E V A L U A T I O N S------
        offspring = selected;
        return parents;
    }

    //-----------------------------------------------------------------------------
    @Override
    public Population evolve(Population original) {
        if (GENERATION % iterationsInIsolation == 0) {
            Population evalBits = select.execute(parents, selectedPopulation);
            symbionts = collaboration.getColaboration(evalBits, parasitesPop);
            //-----------------------------------------E V A L U A T I O N S------
            EVALUATIONS += symbionts.getEvaluations();
//            //-----------------------------------------E V A L U A T I O N S------
            parents = replace.execute(parents, symbionts);
            parents = rescale.execute(parents);
        }
        //evolve population
        parents = evolveHosts(parents);
        //evolve parasites
        evolveParasites();

        return parents;
    }
    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
    //-----------------------------------------------------------------------------

    @Override
    public String getAlgorithm() {
        StringBuilder txt = new StringBuilder();
        txt.append("Symbiotic Solver ");
        txt.append("<" + ITERATIONS_ISOLATION + "> < "
                + SIZE_OF_PARASITES + "> <"
                + SELECTED_POPULATION + ">");
        txt.append("\nALGORITHM:");
        txt.append("\n 1. Create Individulals Population IND_POP");
        txt.append("\n 2. Create Parasites Population PAR_POP");
        txt.append("\n 3. while not termination criteria");
        txt.append("\n     3.1 Repeat ITERATIONS times");
        txt.append("\n          3.1.1 evolve IND_POP");
        txt.append("\n          3.2.1 evolve PAR_POP");
        txt.append("\n     3.2 Apply Symbiosis");
        txt.append("\n          3.2.1 select SIZE_POP ind. from IND_POP");
        txt.append("\n          3.2.2 Produce Symbionts SYMB_POP by collaboration");
        txt.append("\n          3.2.3 IND_POP = replace(IND_POP, SYMB_POP)");
        txt.append("\n\nParameters <ITERATIONS> <SIZE_PAR> <SIZE_POP>");
        txt.append("\n<ITERATIONS> Iterations without symbiosys");
        txt.append("\n<SIZE_PAR> Size of Parasites Population");
        txt.append("\n<SIZE_POP> Size of Select individuals to symbiosys");
        return txt.toString();
    }

    public String getName() {
        return "Symbiotic MuGA";
    }

    @Override
    public void setParameters(String params) {
        StringTokenizer par = new StringTokenizer(params);
        if (par.hasMoreElements()) {
            try {
                ITERATIONS_ISOLATION = Integer.parseInt(par.nextToken());
            } catch (Exception ex) {
                ITERATIONS_ISOLATION = 1;
            }
        }
        if (par.hasMoreElements()) {
            try {
                SIZE_OF_PARASITES = Integer.parseInt(par.nextToken());
            } catch (Exception ex) {
                SIZE_OF_PARASITES = 2;
            }
        }
        if (par.hasMoreElements()) {
            try {
                SELECTED_POPULATION = Integer.parseInt(par.nextToken());
            } catch (Exception ex) {
                SELECTED_POPULATION = 2;
            }
        }

        this.iterationsInIsolation = ITERATIONS_ISOLATION;
        this.sizeOfParasites = SIZE_OF_PARASITES;
        this.selectedPopulation = SELECTED_POPULATION;

    }

    @Override
    public String getParameters() {
        return ITERATIONS_ISOLATION + " "
                + SIZE_OF_PARASITES + " "
                + SELECTED_POPULATION;
    }

    @Override
    public Symbiosis getClone() {
        //make a copy of the class
        Symbiosis solver = (Symbiosis) super.getClone();
        solver.parasitesPop = parasitesPop.getClone();
        solver.iterationsInIsolation = iterationsInIsolation;
        solver.sizeOfParasites = sizeOfParasites;
        solver.selectedPopulation = selectedPopulation;
        //dabase de Genes
        return solver;
    }

    public String getInfo() {
        String inf = "Symbiosis <" + iterationsInIsolation + " >< "
                + sizeOfParasites + " >< " + selectedPopulation + " >\n";
        return inf + super.getInfo();
    }
}
