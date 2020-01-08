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

import genetic.gene.Gene;
import utils.BitField;

/**
 *
 * @author ZULU
 */
public class DeceptivePairIntertwined extends Deceptive {

     @Override
    public boolean isBest() {
        return fitness == getNumGenes()* (getGene(0).getNumBits() + 2);
    }
    
     @Override
    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("Deceptive Pair Intertwined Problem <").append(NUMBER_OF_BLOCKS).append("> <").append(SIZE_OF_BLOCK).append(">");
        buf.append("\nTwo deceptive functions (a and B ) twisted in the same block ");
        buf.append("\n\t a - First Deceptive \n\t B - Second Deceptive");
        buf.append("\nDeceptive Pattern:\n");
        for (int i = 0; i < getNumGenes(); i++) {
            for (int j = 0; j < getGene(0).getNumBits(); j+=2) {
                buf.append("aB");
            }
            buf.append(" ");
        }
        buf.append("\n\nParameters <#BLOCKS> <SIZE>");
        buf.append("\n     <#BLOCKS> number of blocks");
        buf.append("\n     <SIZE>  size of block (even)");
        return buf.toString();

    }

    //--------------------------------------------------------------------------
    @Override
    public int getBlockValue(int numGene) {
        Gene gene = getGene(numGene);
        int A = 0;
        int B = 0;
        int total = 0;
        //for each bit in block
        for (int j = 0; j < gene.getNumBits(); j += 2) {
            //compute BLOCK TRUE
            if (!gene.getAlels().getBit(j)) {
                A++;
            } //compute BLOCK FALSE
            if (!gene.getAlels().getBit(j + 1)) {
                B++;
            }
        }//bits  
//            System.out.println("GENE " + gene + " A = " + A + " B = " + B);
        //sum FALSE block   
        if (B > 0) {
            total += B;
        } else {
            total += (SIZE_OF_BLOCK / 2) + 1;
        }
        //sum TRUE block
        if (A > 0) {
            total += A;
        } else {
            total += (SIZE_OF_BLOCK / 2) + 1;
        }
        return total;
    }
}
