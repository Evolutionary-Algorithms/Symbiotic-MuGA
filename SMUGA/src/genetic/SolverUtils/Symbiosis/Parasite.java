/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.SolverUtils.Symbiosis;

import genetic.gene.GeneBinary;
import java.util.ArrayList;
import java.util.Random;
import operator.mutation.bitString.M_FlipBitWave;
import problem.Individual;
import utils.BitField;
import utils.Funcs;

/**
 *
 * @author Zulu
 */
public class Parasite extends Individual {

//    public static int SIZE_OF_GENOME = 20;
    public static int MINIMUM_LENGHT = 2;
    static Random rnd = new Random();
    public int SIZE_OF_HOST;
    private int position;
     public Parasite(){
         this(8);
     }

    public Parasite(int size) {
        super(MAXIMIZE);
        addGene(new GeneBinary(MINIMUM_LENGHT));
        SIZE_OF_HOST = size;
        fillRandom();
        
    }

    public Parasite(int pos, BitField bits, int size) {
        super(MAXIMIZE);
        SIZE_OF_HOST = size;
        setBits(bits);
        setPosition(pos);
    }

    @Override
    public boolean isBest() {
        return false;
    }

    /**
     * devolve um array com as mutaç?es de um parasita
     *
     * @return
     */
    public ArrayList<Parasite> mutate() {
        ArrayList<Parasite> pop = new ArrayList<>();
        Parasite mutant;
        //para todas as copias
        for (int i = 0; i < getNumCopies(); i++) {
            // fazer um clone com o numero de copia
            mutant = this.getClone();
            mutant.setNumCopys(i + 1);
            switch (rnd.nextInt(3)) {
                case 0:
                    mutant.mutateBits();
                    pop.add(mutant);
                    break;
                case 1:
                    //partir os parasitas
                    Parasite brk = mutant.breakGenome();
                    if (brk != null) {
                        pop.add(brk);
                    }
                    pop.add(mutant);
                default:
                    //mutar a posicao
                    mutant.mutatePosition();
                    pop.add(mutant);
                    break;
            }//switch
        }//copias
        return pop;
    }

    /**
     * O tamanho do salto depende do numero de copias
     */
    public void mutatePosition() {
        //saltar para a frente
        setPosition(rnd.nextInt(SIZE_OF_HOST/getNumCopies()));
    }

    public boolean mutateBits() {
        double prob = (double) getNumCopies() / (double) SIZE_OF_HOST
                + M_FlipBitWave.waveFunctionSin(this.getNumCopies());
        boolean mutation = false;
        BitField bits = getStringBits();
        for (int i = 0; i < bits.size(); i++) {
            if (rnd.nextDouble() < prob) {
                bits.invertBit(i);
                mutation = true;
            }
        }
        if (mutation) {
            setBits(bits);
        }
        return mutation;
    }

    public Parasite breakGenome() {
        int size = getStringBits().size();
        double prob = Math.pow(Math.pow(size, getNumCopies()) / (double) (SIZE_OF_HOST), 3);
//        double prob = Math.pow(size * size / (double) (SIZE_OF_GENOME), 3);
        //Se parte de os elementos forem maiores que MINIMUM_LENGHT
        // a probsabilidade de partir aumenta com o tamanho
        if (size >= 2 * MINIMUM_LENGHT && rnd.nextDouble() < prob) {
            int cutPos = rnd.nextInt(size - 2 * MINIMUM_LENGHT + 1) + MINIMUM_LENGHT;
            BitField bits = getStringBits();
            BitField bits2 = bits.getSubBitField(cutPos, size - 1);
            BitField bits1 = bits.getSubBitField(0, cutPos - 1);
            this.setBits(bits1);
            return new Parasite(getPosition() + cutPos, bits2 , SIZE_OF_HOST);
        }
        return null;
    }

    public void join(Parasite lastParasite) {
        // first is always the one in minimum poosition
        if (lastParasite.getPosition() < getPosition()) {
            lastParasite.join(this);
            return;
        }
//------------------------------------------------------------------------------        
//se nao se tocarem nao existe uniao
//------------------------------------------------------------------------------        
        if (getPosition() + sizeInBits() < lastParasite.getPosition()) {
            return;
        }
        BitField bits1 = getStringBits();
        BitField bits2 = lastParasite.getStringBits();

        //se se tocarem juntam-se
        if (getPosition() + sizeInBits() == lastParasite.getPosition()) {
            //unit o segundo ao primeiro
            bits1.appendBits(bits2);
            this.setBits(bits1);
            //eliminar o segundo
            lastParasite = null;
            return;
        }
//------------------------------------------------------------------------------        
// se estiver incorporado trocar bits entre eles atraves de recombinaç?o uniforme
// O primeiro perde a ultima parte para o segundo         
//------------------------------------------------------------------------------        
        if (lastParasite.getPosition() + lastParasite.sizeInBits() <= getPosition() + sizeInBits()) {
            int gap = lastParasite.getPosition() - getPosition();
            for (int i = 0; i < bits2.size(); i++) {
                //recombinaç?o uniforme - trocar bits
                if (rnd.nextBoolean()) {
                    boolean aux = bits1.getBit(i + gap);
                    bits1.setBit(i + gap, bits2.getBit(i));
                    bits2.setBit(i, aux);
                }

            }
            //partir o parasita maior
            BitField b3 = bits1.getSubBitField(lastParasite.position - position + lastParasite.sizeInBits(), sizeInBits() - 1);
            BitField b1 = bits1.getSubBitField(0, lastParasite.position - position + lastParasite.sizeInBits() - 1);
            setBits(b1);
            //juntar a parte de cima ao parasita 2
            bits2.appendBits(b3);
            lastParasite.setBits(bits2);
            return;
        }
//------------------------------------------------------------------------------        
// se houver sobreposicao trocar bits na sobreposicao
// tornar um maior e o ouro mais pequeno
// trocar bits entre eles na zona de contacto atraves de recombinaç?o uniforme
//-------------------------------------------------
        BitField p1 = bits1.getSubBitField(0, lastParasite.getPosition() - getPosition() - 1);
        BitField p2 = bits1.getSubBitField(lastParasite.getPosition() - getPosition(), bits1.size() - 1);
        BitField p3 = bits2.getSubBitField(0, getPosition() + sizeInBits() - lastParasite.getPosition() - 1);
        BitField p4 = bits2.getSubBitField(getPosition() + sizeInBits() - lastParasite.getPosition(), lastParasite.sizeInBits() - 1);

        //------------------------------------------------------
        // cruzamento uniforme entre p2 e p3
        for (int i = 0; i < p2.size(); i++) {
            //recombinaç?o uniforme - trocar bits
            if (rnd.nextBoolean()) {
                boolean aux = p1.getBit(i);
                p1.setBit(i, p2.getBit(i));
                p2.setBit(i, aux);
            }
        }
        //------------------------------------------------------
        // this cresce
        // lastparasite diminui
        bits1.appendBits(p4);
        this.setBits(bits1);
        bits2 = new BitField(p3);
        lastParasite.setBits(bits2);
    }

    public static void main(String[] args) {
        Parasite p1 = new Parasite(2, new BitField("00000000000000000"),128);
        Parasite p2 = new Parasite(12, new BitField("1111111111"),128);
        System.out.println(" p1 : " + p1.toStringGenotype());
        System.out.println(" p2 : " + p2.toStringGenotype());
        p1.join(p2);
        System.out.println("\n p1 : " + p1.toStringGenotype());
        System.out.println(" p2 : " + p2.toStringGenotype());
    }
//--------------------------------------------------------------------------
//--------------------------------------------------------------------------
//--------------------------------------------------------------------------
//--------------------------------------------------------------------------
//--------------------------------------------------------------------------
//--------------------------------------------------------------------------

    @Override
    protected double fitness() {
        throw new UnsupportedOperationException("Direct evaluation nor Allowed");
    }

    @Override
    public final void fillRandom() {
        genome.clear();
        genome.addGene(new GeneBinary(rnd.nextInt(MINIMUM_LENGHT * 2) + MINIMUM_LENGHT));
        setPosition(rnd.nextInt(SIZE_OF_HOST));
    }

    @Override
    public int compareGenotype(Individual i2) {
        Parasite p = (Parasite) i2;
        if (getPosition() != p.getPosition()) {
            return getPosition() - p.getPosition();
        }
        return super.compareGenotype(i2);
    }

    /**
     * @param pos the pos to set
     */
    public final void setPosition(int pos) {
        //multiplying by 1000 we ensure the position is always a positiver number
        // rounding about chromossom lenght
        this.position = Math.abs((SIZE_OF_HOST * 1000 + pos) % SIZE_OF_HOST);
    }

    public final void setBits(BitField bits) {
        //clear old genome
        genome.clear();
        //genome of parasite is always lesser than
        // the genome of the host
        if (bits.getNumberOfBits() > SIZE_OF_HOST / 4.0) {
            //size in bits is truncated to the size of host
            bits = bits.getSubBitField(0, SIZE_OF_HOST / 4);
        }
        //update bits of parasite 
        genome.addGene(new GeneBinary(bits));
    }

    @Override
    public String toStringGenotype() {
        return toString();
    }

    @Override
    public String toStringPhenotype() {
        final StringBuilder txt = new StringBuilder();
        if (isEvaluated) {
            txt.append(Funcs.DoubleToString(fitness, 20));
        } else {
            txt.append(Funcs.SetStringSize("NOT EVALUATED!", 20));
        }
        txt.append(" = ");
        //number of copies
        String elem = "";
        if (this.numCopys > 1) {
            elem = "[" + Funcs.IntegerToString(numCopys, 4) + "]";
        }
        txt.append(Funcs.SetStringSize(elem, 6));
        txt.append("\t<" + Funcs.IntegerToString(getPosition(), 4) + "> ");
        txt.append(getStringBits());
        return txt.toString();
    }

    public String toString() {
        final StringBuilder txt = new StringBuilder();
        if (isEvaluated) {
            txt.append(Funcs.DoubleToString(fitness, 20));
        } else {
            txt.append(Funcs.SetStringSize("NOT EVALUATED!", 20));
        }
        txt.append(" = ");
        //number of copies
        String elem = "";
        if (this.numCopys > 1) {
            elem = "[" + Funcs.IntegerToString(numCopys, 4) + "]";
        }
        txt.append(Funcs.SetStringSize(elem, 6));
        txt.append("\t<" + Funcs.IntegerToString(getPosition(), 4) + "> ");
        txt.append(getStringGenome());
        return txt.toString();
    }

    public static final char EMPTY = '.';
    public static final char ONE = '1';
    public static final char ZERO = '0';

    public String getStringGenome() {
        BitField bits = getStringBits();
        final StringBuilder txt = new StringBuilder();
        for (int i = 0; i < SIZE_OF_HOST; i++) {
            txt.append(EMPTY);
        }
        for (int i = 0; i < bits.size(); i++) {
            txt.setCharAt((getPosition() + i) % SIZE_OF_HOST, bits.getBit(i) ? ONE : ZERO);
        }
        return txt.toString();
    }

    @Override
    public Parasite getClone() {
        Parasite clone = (Parasite) super.getClone();
        clone.SIZE_OF_HOST = SIZE_OF_HOST;
        clone.setPosition(getPosition());
        clone.setBits(getStringBits());        
        return clone;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }
}
