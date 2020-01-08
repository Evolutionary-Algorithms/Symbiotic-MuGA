///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2013                                             ****/
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
package genetic.SolverUtils.Symbiosis;

import utils.BitField;



/**
 *
 * @author Zulu
 */
public class CollaborationMask {

    StringBuilder mask;

    public CollaborationMask(Parasite p) {
        clear(p);
    }

    //limpar a mascara
    public final void clear(Parasite p) {
        mask = new StringBuilder(p.SIZE_OF_HOST);
        for (int i = 0; i < p.SIZE_OF_HOST; i++) {
            mask.append(Parasite.EMPTY);
        }
    }

    //verifica se pode colaborar
    // pode colaborar se nao existirem bits em conflito
    public boolean canColaborate(Parasite p) {
        BitField bits = p.getStringBits();
        for (int i = 0; i < bits.size(); i++) {
            int pos = (p.getPosition() + i) % p.SIZE_OF_HOST;
            if (mask.charAt(pos) == Parasite.EMPTY) {
                continue;
            }
            if (mask.charAt(pos) != bits.getBitChar(i)) {
                return false;
            }
        }
        return true;
    }
    //coloca o parasita no genoma
    public void applyColaboration(Parasite p, BitField genome) {
        //genome of parasite
        BitField bits = p.getStringBits();
        //position of parasite
        int positonParasite = p.getPosition();
        for (int i = 0; i < bits.size(); positonParasite++, i++) {
            //posicao no genoma
            int pos = positonParasite % p.SIZE_OF_HOST;
            //actauliza a string de colaboracao
            mask.setCharAt(pos,  bits.getBitChar(i));
            //introduzir o bit no genoma
            genome.setBit(pos, bits.getBit(i));
        }
    }
    
    public String toString(){               
        return mask.toString();
    }

}
