///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso                             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****************************************************************************/
///****************************************************************************/
///****     This software was built with the purpose of investigating      ****/
///****     and learning. Its use is free and is not provided any          ****/
///****     guarantee or support.                                          ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package utils;

import genetic.SolverUtils.SimpleSolver;
import genetic.Solver.Genetic_Algorithm;
import javax.swing.JFrame;

/**
 * this class is a container of one solver used to forms call SetupSolver Form
 * and change the type of solver without lose the original reference of form
 * solver
 *
 * @author ZULU
 */
public class SolverContainer {

    public SimpleSolver solver;
    public JFrame mainForm;

    public SolverContainer(SimpleSolver solver, JFrame form) {
        this.solver = solver;
        this.mainForm = form;
    }

    public SolverContainer() {
        solver = new Genetic_Algorithm();
    }
}
