/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import statistics.Mean_Of_Solver;
import genetic.SolverUtils.SimpleSolver;
import genetic.Solver.Genetic_Algorithm;
import genetic.Solver.Symbiosis;
import utils.SolverContainer;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public class Run_MeanSolver extends javax.swing.JFrame implements Runnable {

    
    Thread autorun = null;
    boolean running = false;
    //solver and container
    SimpleSolver solver = new Symbiosis();
    SolverContainer frmSolver = new SolverContainer(solver, this);
    //mean solver    
    Mean_Of_Solver group = new Mean_Of_Solver();
    // population
    PanelOfPopulation displayPopulation = new PanelOfPopulation();
    //Stats
    PanelOfStatistics displayStats;
    //main meno of this frame
    JFrame mainMenu;

    /**
     * Creates new form RunSolver
     */
    public Run_MeanSolver(JFrame mainMenu) {
        initComponents();
        this.mainMenu = mainMenu;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayPopulation.setTitle("Hall of Fame Population");
        displayPopulation.setBounds(0, this.getHeight(), this.getWidth(), dim.height - this.getHeight() - 50);
        displayPopulation.setSolver(solver, solver.parents);
        displayPopulation.setVisible(true);
        displayStats = new PanelOfStatistics(solver);
        displayStats.setBounds(this.getWidth(), 0, dim.width - this.getWidth(), dim.height - 50);
        displayStats.setVisible(true);
        displaySolverEvolution();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSimulationName = new javax.swing.JTextField();
        btStartStop = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btSave = new javax.swing.JButton();
        btRestartSolver1 = new javax.swing.JButton();
        btStartStop1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtNumSolvers = new javax.swing.JTextField();
        sldNumSolvers = new javax.swing.JSlider();
        tpInfo = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtSolverEvolution = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtInfoSolver = new javax.swing.JTextArea();
        pbEvolution = new javax.swing.JProgressBar();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Run Mean Solver");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Simulation Name");

        txtSimulationName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtSimulationName.setForeground(new java.awt.Color(0, 51, 255));
        txtSimulationName.setText("D4Pair_X_Y_Z");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(txtSimulationName)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSimulationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btStartStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Evolution_64.png"))); // NOI18N
        btStartStop.setText("Start Evolution");
        btStartStop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btStartStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStartStopActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.GridLayout(4, 1, 5, 5));

        btSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/save_small_icon.png"))); // NOI18N
        btSave.setText("save");
        btSave.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });
        jPanel2.add(btSave);

        btRestartSolver1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Restart_32x32.png"))); // NOI18N
        btRestartSolver1.setText("Restart ");
        btRestartSolver1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btRestartSolver1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRestartSolver1ActionPerformed(evt);
            }
        });
        jPanel2.add(btRestartSolver1);

        btStartStop1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/setup_small32_icon.png"))); // NOI18N
        btStartStop1.setText("Setup");
        btStartStop1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btStartStop1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStartStop1ActionPerformed(evt);
            }
        });
        jPanel2.add(btStartStop1);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.GridLayout(2, 1));

        jPanel1.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        jLabel22.setText("# of Solvers");
        jPanel1.add(jLabel22);

        txtNumSolvers.setText("250");
        jPanel1.add(txtNumSolvers);

        jPanel3.add(jPanel1);

        sldNumSolvers.setMinimum(1);
        sldNumSolvers.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldNumSolversStateChanged(evt);
            }
        });
        jPanel3.add(sldNumSolvers);

        txtSolverEvolution.setEditable(false);
        txtSolverEvolution.setColumns(20);
        txtSolverEvolution.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        txtSolverEvolution.setRows(5);
        jScrollPane3.setViewportView(txtSolverEvolution);

        tpInfo.addTab("Evolution", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/solve_small_icon.png")), jScrollPane3); // NOI18N

        txtInfoSolver.setEditable(false);
        txtInfoSolver.setColumns(20);
        txtInfoSolver.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        txtInfoSolver.setRows(5);
        jScrollPane1.setViewportView(txtInfoSolver);

        tpInfo.addTab("   Solver  Setup          ", new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Dna_24x24.png")), jScrollPane1); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btStartStop, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tpInfo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbEvolution, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btStartStop, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pbEvolution, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tpInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setLayout(new java.awt.GridLayout(1, 2, 10, 10));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/home_64.png"))); // NOI18N
        jButton1.setText("Main Menu");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        btExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Exit_64.png"))); // NOI18N
        btExit.setText("Exit");
        btExit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExitActionPerformed(evt);
            }
        });
        jPanel4.add(btExit);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        //extracted solver from container
        solver = frmSolver.solver;

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayStats.setBounds(this.getWidth(), 0, dim.width - this.getWidth(), dim.height - 50);
        txtInfoSolver.setText(solver.getInfo());
    }//GEN-LAST:event_formWindowGainedFocus

    private void btExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExitActionPerformed
        running = false;
        mainMenu.dispose();
        displayStats.dispose();
        displayPopulation.dispose();
        this.dispose();
    }//GEN-LAST:event_btExitActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        running = false;
        mainMenu.setVisible(true);
        displayPopulation.dispose();
        displayStats.dispose();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void sldNumSolversStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldNumSolversStateChanged
        txtNumSolvers.setText(sldNumSolvers.getValue() + "");
    }//GEN-LAST:event_sldNumSolversStateChanged

    private void btStartStop1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStartStop1ActionPerformed
        tpInfo.setSelectedIndex(1);
        //--------------solver container --------------
        frmSolver = new SolverContainer(solver, this);
        //---------------------------------------------
        SetupSolver setup = new SetupSolver(frmSolver);
        setup.setVisible(true);
    }//GEN-LAST:event_btStartStop1ActionPerformed

    private void btRestartSolver1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRestartSolver1ActionPerformed
        //stop solver
        if (this.running) {
            running = false;
            group.stopMeanSolver();
            return;
        }
        int numSolvers = Integer.parseInt(txtNumSolvers.getText());
        group = new Mean_Of_Solver(solver, numSolvers);

        displayStats.setSolver(group);
        displayStats.setVisible(true);
        displayPopulation.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayStats.setBounds(this.getWidth(), 0, dim.width - this.getWidth(), dim.height - 50);
        displaySolverEvolution();
    }//GEN-LAST:event_btRestartSolver1ActionPerformed

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed

        String path = "./stats/" + Funcs.getNow("yyyy-MM-dd") + "/"
                + txtSimulationName.getText() + "/";
        group.saveToFile(path);


        displayStats.saveStatisticsImages(path + solver.getTemplate().getName() + "/");

    }//GEN-LAST:event_btSaveActionPerformed

    private void btStartStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStartStopActionPerformed
        tpInfo.setSelectedIndex(1);
        if (autorun == null) {
            int numSolvers = Integer.parseInt(txtNumSolvers.getText());
            group = new Mean_Of_Solver(solver, numSolvers);
            group.setTitle(txtSimulationName.getText());
            //-------------------------------------
            displayStats.setSolver(group);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            displayStats.setBounds(this.getWidth(), 0, dim.width - this.getWidth(), dim.height - 50);
            //---------------------------------------------------
            running = true;
            autorun = new Thread(this);
            autorun.start();
            btStartStop.setText("Stop Evolution");
        } else if (running == false && !group.isDone()) {
            btStartStop.setText("Stop Evolution");
            running = true;
            autorun = new Thread(this);
            autorun.start();
        } else {
            running = false;
            group.stopMeanSolver();
        }
    }//GEN-LAST:event_btStartStopActionPerformed

    public void displaySolverEvolution() {
        txtSolverEvolution.setText(group.getEvolutionInfo());
        txtSolverEvolution.setCaretPosition(0);
        pbEvolution.setValue((int) (group.getStop().getProgress() * 100));
        if (group != null) {
            displayPopulation.displayPopulation(group.parents, group);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btExit;
    private javax.swing.JButton btRestartSolver1;
    private javax.swing.JButton btSave;
    private javax.swing.JButton btStartStop;
    private javax.swing.JButton btStartStop1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JProgressBar pbEvolution;
    private javax.swing.JSlider sldNumSolvers;
    private javax.swing.JTabbedPane tpInfo;
    private javax.swing.JTextArea txtInfoSolver;
    private javax.swing.JTextField txtNumSolvers;
    private javax.swing.JTextField txtSimulationName;
    private javax.swing.JTextArea txtSolverEvolution;
    // End of variables declaration//GEN-END:variables

    public void updateEvolutionBar(final int value){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               pbEvolution.setValue(value);
            }
        });
    }
    
    
    @Override
    public void run() {
        running = true;
        while (running && !group.isDone()) {
            group.iterate();
            displaySolverEvolution();
        }
        btStartStop.setText("Start Evolution");
        running = false;
        if (group.isDone()) {
            autorun = null;
            btSaveActionPerformed(null);
        }
    }
}
