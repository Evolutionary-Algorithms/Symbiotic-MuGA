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
package problem.permutation.TSP;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author ZULU
 */
public class SimplesTSP extends AbstractTSP {

    static int[] best = {0, 1, 2, 3, 4, 5};
    static double[] x = {0,0,1,2,2,1};
    static double[] y = {2,1,0,1,2,3};

    public SimplesTSP() {
        super(x, y, best);
    }
    public String toTSPString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i <x.length; i++) {            
            str.append( "\t" +genome[i] + "\t");
        }
        str.append("\n");
        for (int i = 0; i <x.length; i++) {
            Point2D p = getPoint(genome[i]);
            str.append( "(" + p.getX() + "," + p.getY() + ")\t ");
        }
        return str.toString();
    }
}
