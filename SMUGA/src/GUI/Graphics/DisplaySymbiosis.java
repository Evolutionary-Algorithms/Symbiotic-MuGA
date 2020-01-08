/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import genetic.Solver.Symbiosis;
import genetic.SolverUtils.Symbiosis.Parasite;
import genetic.gene.Gene;
import genetic.population.Population;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import problem.Individual;
import problem.bitString.Deceptive.Deceptive;
import utils.BitField;

/**
 *
 * @author arm
 */
public class DisplaySymbiosis extends DisplayPopulation {

    int x0, y0, x1, y1;
    Deceptive deceptive = null;

    public void showFunc(Graphics gr) {
        if (getPop() == null) {
            return;
        }

        //deceptive
        if (getPop().getGenotype(0) instanceof Deceptive) {
            deceptive = (Deceptive) getPop().getGenotype(0);
        }

        x0 = 0;
        y0 = x0;
        x1 = this.getWidth();
        y1 = this.getHeight();
        gr.setColor(new Color(120, 120, 120));
        //gr.clearRect(0, 0, this.getWidth(), this.getHeight());
        gr.fill3DRect(x0, y0, x1, y1, true);
        //----------------------------------------------------------------
        //dimensions of Line of bits
        double dimY = (double) this.getHeight() / (getPop().size());
        dimY /= 1.9;
        int i = 0;
        for (Individual ind : getPop().getSortedIterable()) {
            displayBinaryIndividual(gr, ind, (int) (dimY * i++), (int) dimY);
        }

        Population parasites = ((Symbiosis) solver).getParasites();
        displayParasites(gr, parasites, 0, (int) (dimY * pop.size()) + 10, x1, y1);

    }

    protected void displayBinaryIndividual(Graphics gr, Individual ind, int py, int sizey) {
        //deceptive
        if (ind instanceof Deceptive) {
            deceptive = (Deceptive) ind;
        }
        BitField bits = ind.getStringBits();
        int BLOCKS = ind.getNumGenes();
        double dx = (double) this.getWidth() / (bits.getNumberOfBits() + BLOCKS);
        int separatorSize = (int) ((this.getWidth() - bits.getNumberOfBits() * (int) dx) / BLOCKS);
        int gap = 2;
        if (dx < 2) {
            gap = -1;
        }
        if (sizey > 4) {
            sizey = (sizey * 8) / 10;
        }
        int px = 0;
        for (int i = 0; i < ind.getNumGenes(); i++) {
            Gene gene = ind.getGene(i);
            boolean optimum = false;
            if (deceptive != null && deceptive.isGeneGood(i)) {
                optimum = true;
            }
            //print separator between genes
            px += separatorSize / 2;
            for (int j = 0; j < gene.getNumBits(); j++) {
//                if (optimum) {
//                    gr.setColor(Color.GREEN);
//                    gr.drawRect(px - 1, py - 1, (int) (dx - gap) + 2, sizey + 2);
//                }

                //draw alel
                gr.setColor(getColor(gene.getAlels().getBit(j), optimum));
                gr.fill3DRect(px, py, (int) (dx - gap), sizey, true);
                //increase px
                px += (int) dx;
            }
            //print another half of separator
            px += (separatorSize + 1) / 2;
        }
    }

    private void displayParasites(Graphics gr, Population pop, int x0, int y0, int x1, int y1) {
        gr.setColor(new Color(120, 120, 120));
        gr.fillRect(x0, y0, x1 - x0, y1 - y0);

        //dimensions of Line of bits
        double dimY = (y1 - y0) / (double) pop.size();
        int i = 0;
        Iterator<Individual> it = pop.getSortedIterable().iterator();
        while (it.hasNext()) {
            Parasite p = (Parasite) it.next();
//            System.out.println("PARASITE " + p);
            displayParasite(gr, solver.template, p, y0 + (int) (dimY * i++), (int) dimY);
        }
    }

    protected void displayParasite(Graphics gr, Individual ind, Parasite p, int py, int sizey) {
        String bits = p.getStringGenome();
        boolean isOptimum = false;
        if (deceptive != null && deceptive.isParasiteGood(p)) {
            isOptimum = true;
        }

        int BLOCKS = ind.getNumGenes();
        double dx = (double) this.getWidth() / (bits.length() + BLOCKS);
        int separatorSize = (int) ((this.getWidth() - bits.length() * (int) dx) / BLOCKS);
        int gap = 1;
        if (dx < 2) {
            gap = -1;
        }
        if (sizey > 4) {
            sizey = (sizey * 8) / 10;
        }

        //Display Background
        int px = 0;
        for (int i = 0; i < ind.getNumGenes(); i++) {
            Gene gene = ind.getGene(i);
            //print separator between genes
            px += separatorSize / 2;
            for (int j = 0; j < gene.getNumBits(); j++) {
                gr.setColor(getColor(bits.charAt(i * gene.getNumBits() + j), isOptimum));
                gr.fill3DRect(px, py, (int) (dx - gap), sizey, true);                
                gr.drawRect(px, py, (int) (dx - gap), sizey);

                //increase px
                px += (int) dx;
            }
            //print another half of separator
            px += (separatorSize + 1) / 2;
        }
    }

    private Color getColor(char ch) {
        switch (ch) {
            case Parasite.EMPTY:
                return Color.LIGHT_GRAY;
            case Parasite.ONE:
                return Color.BLACK;
            case Parasite.ZERO:
                return Color.WHITE;
            default:
                return Color.RED;
        }
    }

    static Color[][] color = {
        {new Color(150, 255, 150), new Color(0, 150, 0), new Color(120, 120, 120)},
        {new Color(255, 150, 150), new Color(150, 0, 0), new Color(20, 20, 20)},};

    private Color getColor(char ch, boolean optimum) {
        if (optimum) {
            if (ch == Parasite.ZERO) {
                return color[0][0];
            }
            if (ch == Parasite.ONE) {
                return color[0][1];
            }
            return color[0][2];
        }
        if (ch == Parasite.ZERO) {
            return color[1][0];
        }
        if (ch == Parasite.ONE) {
            return color[1][1];
        }
        return color[0][2];

    }

    private Color getColor(boolean ch, boolean optimum) {
        if (optimum) {
            if (ch) {
                return color[0][1];
            }
            return color[0][0];
        }
        if (ch) {
            return color[1][1];
        }
        return color[1][0];

    }

}
