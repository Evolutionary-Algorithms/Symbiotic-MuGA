///****************************************************************************/
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
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package problem.bitString.Hierarchical;

import java.util.Random;
import static problem.bitString.Hierarchical.HTrap1.HTrap3;
import utils.BitField;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public class HTrap_Random extends HTrap1 {

    static Random rnd = new Random();
    public static int[] bitOrder;

    static {
        shuffleOrder();
    }

    /**
     * randomize the bit order of HIFF_K function
     */
    public static void shuffleOrder() {
        bitOrder = utils.Array.createLinearArray(SIZE);
        utils.Array.shuffle(bitOrder);       
    }

    public HTrap_Random() {
        if (SIZE != bitOrder.length) {
            shuffleOrder();
        }
    }

    @Override
    public void setParameters(String param) {
        super.setParameters(param);
        shuffleOrder();
    }

    public BitField getShuffledBits() {
        BitField bits = getStringBits();
//        System.out.println("\nBITS " + bits);
        BitField ordered = new BitField(SIZE);
        for (int i = 0; i < bitOrder.length; i++) {
                ordered.setBit(i,bits.getBit(bitOrder[i]));

        }
        return ordered;
    }

    @Override
    protected double fitness() {
        BitField bits = getShuffledBits();
        return bits.getNumberOfBits() + HTrap_Value(bits.toString(), 1, bits.getNumberOfBits());
    }

    @Override
    public String toStringPhenotype() {
        final StringBuilder txt = new StringBuilder();
        String elem = "";
        if (this.numCopys > 1) {
            elem = "[" + Funcs.IntegerToString(numCopys, 4) + "]";
        }
        txt.append(Funcs.SetStringSize(elem, 8));
        if (isEvaluated) {
            txt.append(Funcs.DoubleToString(fitness, 24));
        } else {
            txt.append(Funcs.SetStringSize(" NOT EVALUATED ! ", 24));
        }
        txt.append(" = "); 
        String ordered = getShuffledBits().toString();
        txt.append( HTrap3(ordered));
        txt.append( " \tbits: " + size[LEVEL] );
        if (isBest()) {
            txt.append( " [OPTIMUM]");
        }
        return txt.toString();       
    }
}
