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
package problem.bitString.Deceptive;

import genetic.SolverUtils.Symbiosis.Parasite;
import genetic.gene.Gene;
import genetic.gene.GeneBinary;
import java.util.Random;
import java.util.StringTokenizer;
import problem.Individual;
import utils.BitField;
import utils.DynamicLoad;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public class Deceptive extends Individual {
//size of deceptive block     

    public static int SIZE_OF_BLOCK = 8;            
    public static int NUMBER_OF_BLOCKS = 16;
    
    static Random rnd = new Random();

   public Deceptive(int number_of_blocks, int size_of_block) {
        super(MAXIMIZE);
        for (int i = 0; i < number_of_blocks; i++) {
            addGene(new GeneBinary(size_of_block));
        }

    }
    public Deceptive() {
        this(NUMBER_OF_BLOCKS,SIZE_OF_BLOCK);
    }

    @Override
    public boolean isBest() {
        return fitness == getNumGenes()* (getGene(0).getNumBits() + 1);
    }

    @Override
    public String toString() {
        return toStringGenotype();
    }

    @Override
    public String toStringGenotype() {
        StringBuilder txt = new StringBuilder();
        //number of copies
        String elem = "";
        if (this.getNumCopies() > 1) {
            elem = "[" + Funcs.IntegerToString(getNumCopies(), 4) + "]";
        }
        txt.append(Funcs.SetStringSize(elem, 8));

        if (this.isEvaluated()) {
            txt.append(Funcs.DoubleToString(fitness, 10));
        } else {
            txt.append(Funcs.SetStringSize("????", 10));
        }
        txt.append(" = ");
        for (int i = 0; i < getNumGenes(); i++) {
            txt.append(getGene(i).toBinString() + " ");
        }
        if (isBest()) {
            txt.append("BEST");
        }
        return txt.toString();
    }

    @Override
    public String toStringPhenotype() {
        StringBuilder txt = new StringBuilder();
        //number of copies
        String elem = "";
        if (this.getNumCopies() > 1) {
            elem = "[" + Funcs.IntegerToString(getNumCopies(), 4) + "]";
        }
        txt.append(Funcs.SetStringSize(elem, 8));

        if (this.isEvaluated()) {
            txt.append(Funcs.DoubleToString(fitness, 10));
        } else {
            txt.append(Funcs.SetStringSize("????", 10));
        }
        txt.append(" = ");

        for (int i = 0; i < getNumGenes(); i++) {
            txt.append(getBlockValue(i) + " ");
        }

        return txt.toString();

    }

    @Override
    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("Deceptive Problem <").append(getNumGenes()).append("> <").append(getGene(0).getNumBits()).append(">");
        buf.append("\nDeceptive Pattern:\n");
        for (int i = 0; i < getNumGenes(); i++) {
            for (int j = 0; j < getGene(0).getNumBits(); j++) {
                    buf.append("0");
            }
            buf.append(" ");
        }
        buf.append("\n\nParameters <BLOCKS> <SIZE>");
        buf.append("\n     <BLOCKS> number of blocks");
        buf.append("\n     <SIZE>  size of block ");
        return buf.toString();

    }

    @Override
    protected double fitness() {
        int total = 0;
        //for each block
        for (int i = 0; i < getNumGenes(); i++) {
            total += getBlockValue(i);
        }
        return total;
    }
    //--------------------------------------------------------------------------

    public int getBlockValue(int numGene) {
        Gene gene = getGene(numGene);
        int TOTAL = 0;
        //for each bit in block
        for (int i = 0; i < gene.getNumBits(); i++) {
            //compute FALSE bits
            if (! gene.getAlels().getBit(i) ) {
                TOTAL++;
            }//bits  
        }
        if (TOTAL > 0) {
            return TOTAL;
        } else {
            return getGene(numGene).getNumBits() + 1;
        }
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    @Override
    public String getParameters() {
        return getNumGenes() + " " + getGene(0).getNumBits();
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                NUMBER_OF_BLOCKS = Integer.parseInt(iter.nextToken());
                if (NUMBER_OF_BLOCKS <= 0) {
                    NUMBER_OF_BLOCKS = 4;
                }
            } catch (Exception e) {
                NUMBER_OF_BLOCKS = 4;
            }
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                SIZE_OF_BLOCK = Integer.parseInt(iter.nextToken());
                if (SIZE_OF_BLOCK <= 0) {
                    SIZE_OF_BLOCK = 8;
                }
            } catch (Exception e) {
                SIZE_OF_BLOCK = 8;
            }
        }
        initializePattern();
        this.restart();
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    protected void initializePattern() {
       for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            addGene(new GeneBinary(SIZE_OF_BLOCK));
        }
    }

    //--------------------------------------------------------------------------
    protected BitField getGeneBits(BitField bits, int gene) {
        int size = getGene(gene).getNumBits();
        return bits.getSubBitField(gene * size, (gene + 1) * size - 1);
    }

    public BitField getCorrectBits() {
        BitField b = getStringBits();
        for (int i = 0; i < b.size(); i++) {
            if (!b.getBit(i)) {
                b.setBit(i, false);
            } else {
                b.setBit(i, true);
            }
        }
        return b;
    }

    public boolean isGeneGood(int gene) {
        BitField b = getGene(gene).getAlels();
        for (int i = 0; i < b.size(); i++) {
            if (!b.getBit(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isParasiteGood(Parasite p) {
        BitField b = p.getStringBits();
        int pos = p.getPosition();
        int size = getNumGenes() * getGene(0).getNumBits();
        for (int i = 0; i < b.size(); i++) {
            int px = (i + pos) % size;
            if (!b.getBit(i)) {
                return false;
            }
        }
        return true;
    }
    
     /**
     * Clone of the individula
     *
     * @return clone
     */
    @Override
    public Individual getClone() {
        //make new individuals
        Individual ind = (Individual) DynamicLoad.makeObject(this);
        ind.getGenome().clear();
        for (int i = 0; i < genome.getNumGenes(); i++) {
            ind.addGene(genome.getGene(i).getClone());
        }
        
        ind.setFitness(fitness);
        ind.setIsEvaluated(isEvaluated);
        ind.setNumCopys(numCopys);
        return ind;
    }

}
