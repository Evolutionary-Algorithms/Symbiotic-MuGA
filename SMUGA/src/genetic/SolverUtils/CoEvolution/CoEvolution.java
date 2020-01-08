///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///**** or support.                                                    ****/
///**** \   If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package genetic.SolverUtils.CoEvolution;

import genetic.SolverUtils.SimpleSolver;
import genetic.population.Population;
import genetic.stopCriteria.NoStop;
import java.util.StringTokenizer;
import problem.Individual;

import statistics.SolverStatistic;

/**
 *
 * @author ZULU
 */
public class CoEvolution extends SimpleSolver {

    public static int ITERATIONS_STRUCTURE_EVOLUTION = 4;
    public static int ITERATIONS_POPULATION_EVOLUTION = 8;
    public static boolean ELISTISM_STRUCTURE = false;
    CE_Bits solverBits;
    CE_Positions solverStructure;

    public void setPopulation(Population pop) {
        template = pop.getGenotype(0).getClone();
        createCooperationSolvers(template, pop.size());
        solverBits.setSelect(select);
        solverBits.setMutate(mutate);
        solverBits.setRecombine(recombine);
        solverBits.setReplace(replace);
        solverBits.setRescaling(rescale);
        solverBits.setStop(new NoStop());

        GENERATION = 0;
        EVALUATIONS = 0;
        this.stats = new SolverStatistic(title);
        //----------------------------------------------
        parents = solverBits.parents;
        offspring = solverBits.offspring;
        selected = solverBits.selected;
        hallOfFame = parents.getGoods();
        this.stats.addSolverToStats(solverBits, 0);
//        updateSolverStats();
    }

    public void createCooperationSolvers(Individual ind, int size) {
        //Criar o solver dos bits  
        solverStructure = new CE_Positions(ind, size, ELISTISM_STRUCTURE);
        //Criar o solver dos bits
        solverBits = new CE_Bits(ind, size);
    }

    @Override
    public Population evolve(Population original) {

        solverBits.evolve(ITERATIONS_POPULATION_EVOLUTION);
        
        Individual plain = solverBits.parents.getBestGenotype().getClone();
//        Individual plain = solverBits.bestIndividualFound;
        solverBits.removeStructure(plain);
        solverBits.setTemplate(plain);

        solverStructure.evolve(ITERATIONS_STRUCTURE_EVOLUTION);

           Positions bestp = (Positions) solverStructure.parents.getBestGenotype().getClone();
            solverBits.updateStructure(bestp.getGeneValues());
        

        EVALUATIONS = solverBits.EVALUATIONS + solverStructure.EVALUATIONS;
        return solverBits.parents;
    }

    @Override
    public String getAlgorithm() {
        StringBuilder str = new StringBuilder();
        str.append("\n Coevolution Genetic Algorithm Solver");
        str.append("\n 1 - create POPULATION");
        str.append("\n 2 - Create STRUCTURE");
        str.append("\n 3 - until STOP criteria");
        str.append("\n    3.1 - Evolve STRUCTURE <" + ITERATIONS_STRUCTURE_EVOLUTION + "> iterations");
        str.append("\n    3.2 - set best Structure in POPULATION (ELITISM : " + (ELISTISM_STRUCTURE ? 1 : 0) + " )");
        str.append("\n    3.3 - Evolve POPULATION <" + ITERATIONS_POPULATION_EVOLUTION + "> iterations");
        str.append("\n    3.4 - set best Individual in Structure");
        str.append("\n");
        str.append("\nparameters <ITER_STRUCT> <ITER_POP> <ELITISM>");
        str.append("\n<ITER_STRUCT> number of iterations in STRUCTURE");
        str.append("\n<ITER_POP>    number of iterations in POPULATION");
        str.append("\n<ELISTM>  elitism in STRUCTURE population (0/1)");
        return str.toString();
    }

    public void evolve(int runs) {
        for (int i = 0; i < runs; i++) {
//            System.out.print(i + "\tEVALS " + EVALUATIONS / 1000.0);
            parents = evolve(solverBits.parents);
            if (parents.getBestGenotype().isBest()) {
                break;
            }
        }
    }

    @Override
    public void updateSolverStats() {
        EVALUATIONS = solverBits.EVALUATIONS + solverStructure.EVALUATIONS;
        //update Hall of Fame
        hallOfFame.add(solverBits.parents.getGoods());
        this.parents = solverBits.parents;
        this.select = solverBits.select;
        this.offspring = solverBits.offspring;
        //update stats
        stats.addSolverToStats(this, stopCriteria.getCurrentValue());
        //update stop criteria
        stopCriteria.updateValue(this);

    }

    public void restartSolver() {
        super.restartSolver();
        createCooperationSolvers(template, solverBits.parents.size());
        resetStats();
    }

    @Override
    public void setParameters(String params) {
        StringTokenizer par = new StringTokenizer(params);
        if (par.hasMoreElements()) {
            try {
                ITERATIONS_STRUCTURE_EVOLUTION = Integer.parseInt(par.nextToken());
            } catch (Exception ex) {
                ITERATIONS_STRUCTURE_EVOLUTION = 1;
            }
        }
        if (par.hasMoreElements()) {
            try {
                ITERATIONS_POPULATION_EVOLUTION = Integer.parseInt(par.nextToken());
            } catch (Exception ex) {
                ITERATIONS_POPULATION_EVOLUTION = 2;
            }
        }
        if (par.hasMoreElements()) {
            try {
                ELISTISM_STRUCTURE = par.nextToken().charAt(0) == '1' ? true : false;
            } catch (Exception ex) {
                ELISTISM_STRUCTURE = false;;
            }
        }

    }

    @Override
    public String getParameters() {
        return ITERATIONS_STRUCTURE_EVOLUTION + " "
                + ITERATIONS_POPULATION_EVOLUTION + " "
                + (ELISTISM_STRUCTURE ? 1 : 0);
    }

//    public static void main(String[] args) {
//        Debug.IN_DEBUG = false;
//        CoEvolution ce = new CoEvolution();
//        int SIZE_OF_POP = 64;
//
//        F4.NUMBER_OF_BLOCKS = 10;
//        Individual ind = new F4Random();
////        ind = new F4();
//
//        F3.NUMBER_OF_BLOCKS = 100;
////        ind = new F3();
//
//        HIFF_K.LEVEL = 10;
////        ind = new HIFF_K();
////        ind = new HIFF_Random();
//
//        Trap.GENE_SIZE = 10;
//        Trap.NUMBER_OF_BLOCKS = 10;
////        ind = new TrapRandom();
//
//        Bipolar.NUMBER_OF_BLOCKS = 1;
////        ind = new Bipolar();
//
//        HXOR.LEVEL = 9;
////        ind = new HXOR();
//        ce.createCooperationSolvers(ind, SIZE_OF_POP);
//        System.out.println("Problem : " + ind.getGenomeInformation());
//        ce.evolve(10000);
//        System.out.println(ce.EVALUATIONS + " => " + ce.solverBits.bestIndividualFound);
//
//    }
}