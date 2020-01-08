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

import genetic.population.Population;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import problem.Individual;
import utils.BitField;

/**
 *
 * @author Zulu
 */
public class SymbioticCollaboration {

    public Random rnd = new Random();

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    
     private boolean constainsGene(BitField hostAllels,Parasite parasite) {
        //genes do parasita
        BitField parasiteAllels = parasite.getGenome().getGene(0).getAlels();
        for (int i = 0; i < parasiteAllels.size(); i++) {
            if (hostAllels.getBit((parasite.getPosition() + i) % hostAllels.size()) != parasiteAllels.getBit(i)) {
                return false;
            }
        }
        return true;
    }
    
     /**
     * RANKS dos parasitas [ N/2 . . . .-N/2 +1 ]
     *
     * @param parasite
     * @param sortedPop
     * @return
     */
    public double calculateParasiteFitness(Parasite parasite, ArrayList<BitField> sortedPop) {
        int SIZE = sortedPop.size();
        double fitness = 0;
        int numInd = 0;
        //percorrer a lista 
        for (int i = 0; i < sortedPop.size(); i++) {
            //se for compativel
            if (constainsGene(sortedPop.get(i), parasite)) {
                fitness += SIZE - i * 2;
                numInd++;
            }
        }
        //gene desconhecido
        if (numInd == 0) {
            //atribuir um valor médio
            return sortedPop.size() / (parasite.sizeInBits() + 0.0);
        }
        return (parasite.sizeInBits() * fitness);
    }
       
    
    public void evaluateParasites(Population parasites, Population bits) {
        //array ordenado da populacao
        ArrayList<Individual> sortedPop = bits.getSortedArray();
        //bits da populaç?o
        //NOTE: optimizacao do codigo
        ArrayList<BitField> bitsPop = new ArrayList<>();
        for (Individual ind : sortedPop) {
            bitsPop.add(ind.getStringBits());
        }
        //--------------------------------------------------------------------

        //iterador para os parasitas
        Iterator<Individual> it = parasites.getIterator();
        while (it.hasNext()) {
            Parasite p = (Parasite) it.next();
            double fitness = calculateParasiteFitness(p , bitsPop);
            //double fitness = calculateMeanOfRanks(p, sortedPop);
            //premiar a dimensao
            p.setFitness(fitness);
        }
    }

    public Population getColaboration(Population pop, Population parasites) {
        Iterator<Individual> itPar;
//        while (itPar.hasNext()) {
//            Parasite2 par = (Parasite2) itPar.next();
//            par.setFitness(par.getFitness() * 0.9);
//        }
        Parasite parasiteTemplate = (Parasite)parasites.getGenotype(0);
        CollaborationMask symbMask = new CollaborationMask(parasiteTemplate);
        Population symb = pop.getCleanCopie();
        Iterator<Individual> it = pop.getSortedIterable().iterator();
        int INDIVIDUAL_RANK_POSITION = 0;
        while (it.hasNext()) {
            Individual ind = it.next();
            INDIVIDUAL_RANK_POSITION++;
            for (int i = 1; i <= ind.getNumCopies(); i++) {
                Individual template = ind.getClone();               
                //bits para a colaboracao
                BitField cloneBits = template.getStringBits();
                //initalize mask
                symbMask.clear(parasiteTemplate);
                //testar os parasitas
                itPar = parasites.getRandomIterable().iterator();
                //-------------------------------------------------------------
                //APLICAR OS PARASITAS
                //--------------------------------------------------------------
                while (itPar.hasNext()) {
                    Parasite par = (Parasite) itPar.next();
                    double prob = (1.0 * i * INDIVIDUAL_RANK_POSITION) / pop.size();
                    //se passar na mascara
                    if (rnd.nextDouble() < prob) {
                        //se o gene nao existir e puder colaborar
                        if (!constainsGene(cloneBits, par) && symbMask.canColaborate(par)) {
                            //marcar a mascara e aplicar os bits
                            symbMask.applyColaboration(par, cloneBits);
                            //conttruir um novo individuo com os novos bits
                            Individual clone = template.getClone();
                            clone.setBits(cloneBits.getClone());
                            //adicionar o individiduo a populacao simbionte
                            symb.addGenotype(clone);                            
                        }
                    }

                }
                //fazer o symbionte
            }//numero de copias
        }
        //avaliar os simbiontes
        symb.evaluate();
        return symb;
    }

}
