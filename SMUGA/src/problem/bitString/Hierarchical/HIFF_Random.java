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
import static problem.bitString.Hierarchical.HIFF_K.K_ARY;
import static problem.bitString.Hierarchical.HIFF_K.LEVEL;
import static problem.bitString.Hierarchical.HIFF_K.SIZE;
import utils.BitField;

/**
 *
 * @author ZULU
 */
public class HIFF_Random extends HIFF_K {

    static Random rnd = new Random();
    public static int[] bitOrder;

    static {
        shuffleOrder();
    }

    /**
     * randomize the bit order of HIFF_K function
     */
    public static void shuffleOrder() {
        SIZE = (int) Math.pow(K_ARY, LEVEL);
        bitOrder = utils.Array.createLinearArray(SIZE);
        utils.Array.shuffle(bitOrder);       
    }

    public HIFF_Random() {
        if (SIZE != (int) Math.pow(K_ARY, LEVEL)) {
            shuffleOrder();
        }
    }

    public HIFF_Random(String bits, int k_ary) {
        super(bits);
        if (k_ary != K_ARY || SIZE != bits.length()) {
            K_ARY = k_ary;
            SIZE = bits.length();
            shuffleOrder();
        }
    }

    @Override
    public void setParameters(String param) {
        super.setParameters(param);
        shuffleOrder();
    }

    public String getShuffledBits() {
        BitField bits = getStringBits();
//        System.out.println("\nBITS " + bits);
        StringBuilder txt = new StringBuilder(SIZE);
        for (int i = 0; i < bitOrder.length; i++) {
            if (bits.getBit(bitOrder[i])) {
                txt.append("1");
            } else {
                txt.append("0");
            }
        }
//        System.out.println("XBIT " + txt);
        return txt.toString();
    }

    @Override
    protected double fitness() {
        String bits = getShuffledBits();
        return bits.length() + HIFF_Value(bits, 1);
    }

    public static void main(String[] args) {
        HIFF_Random h = new HIFF_Random("00011111", 2);
        h.evaluate();
        System.out.println(h.toStringGenotype());
        System.out.println(h.toStringPhenotype());
    }

    @Override
    public String toStringPhenotype() {
        String bits = getStringBits().toString();
        String ordered = getShuffledBits();
        StringBuilder txt = new StringBuilder();
        txt.append(fitness + " = ");
//        txt.append(bits);
//        txt.append(" [Ordered] ");
//        txt.append(ordered);
        txt.append(" " + HIFF(ordered));
        if (isBest()) {
            txt.append(" [OPTIMUM]");
        }
        return txt.toString();
    }
}
