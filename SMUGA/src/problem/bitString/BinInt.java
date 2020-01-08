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
package problem.bitString;

import genetic.gene.Gene;
import genetic.gene.GeneBinary;
import java.math.BigDecimal;
import java.util.Random;
import java.util.StringTokenizer;
import problem.Individual;
import static problem.Individual.MAXIMIZE;
import static problem.Individual.setBest;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public class BinInt extends Individual {

    public static int SIZE_OF_GENE = 100;
    static Random rnd = new Random();

    public BinInt() {
        this(SIZE_OF_GENE);
    }

    public BinInt(int size) {
        super(MAXIMIZE);
        SIZE_OF_GENE = size;
        setBest(Math.pow(2, SIZE_OF_GENE) - 1);
        addGene(new GeneBinary(SIZE_OF_GENE));
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
            txt.append(Funcs.SetStringSize(fitness+"", 23));
        } else {
            txt.append(Funcs.SetStringSize("????", 23));
        }
        txt.append(" = " + getStringBits());
        return txt.toString();
    }

   

    @Override
    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("Bin Int Problem <").append(SIZE_OF_GENE).append(">");

        buf.append("\n\nParameters <#BITS>");
        buf.append("\n     <#BITS>  number of bits in gene ");
        return buf.toString();

    }

    @Override
    protected double fitness() {
        return getValue(0);
    }
    //--------------------------------------------------------------------------

    public double getValue(int numGene) {
        Gene gene = getGene(numGene);
        return gene.getAlels().getBigDecimal().doubleValue();
    }

    protected BigDecimal getBigFitness() {
        return new BigDecimal(getStringBits().toString());
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public String getParameters() {
        return ""+ SIZE_OF_GENE;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
       
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                SIZE_OF_GENE = Integer.parseInt(iter.nextToken());
                if (SIZE_OF_GENE <= 0) {
                    SIZE_OF_GENE = 8;
                }
            } catch (Exception e) {
                SIZE_OF_GENE = 8;
            }
        }
        //----------------------------------------------
        // Set new Genotype to this individual
        this.restart();
        //----------------------------------------------
    }

    public boolean isBest() {
        for (int i = 0; i < getNumGenes(); i++) {
            if (getGene(i).getAlels().getNumberOfOnes() != getGene(i).getAlels().getNumberOfBits()) {
                return false;
            }
        }
        return true;
    }

    /**
     * fitness comparation
     *
     * @param other x
     * @return x
     */
    @Override
    public int compareTo(Object other) {

        if (!(other instanceof BinInt)) {
            return super.compareTo(other);
        }

        BinInt tmp = (BinInt) other;
        if (!tmp.isEvaluated) {
            if (this.isEvaluated) {
                return 1;
            }
            return -1;
        }
        return this.getBigFitness().compareTo(tmp.getBigFitness());

    }
    //--------------------------------------------------------------------------

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            BinInt b = new BinInt(10);
            b.evaluate();
            System.out.println(b);

        }
    }
}