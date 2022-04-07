package com.xk.aps.utils.ga;

/**
 * @author sy
 * @date 2022/3/14 - 14:24
 */
public class GeneticAlgorithmTest extends GeneticAlgorithm{
    public static final int NUM = 1 << 24;

    /**
     * 基因长度24位
     */
    public GeneticAlgorithmTest() {
        super(25);
    }

    @Override
    public double changeX(Chromosome chro) {
        // TODO Auto-generated method stub
        //return ((1.0 * chro.getNum() / NUM) * 100) + 6;
        return ((1.0 * chro.getNum() / NUM) * 10) + 0;
    }

    @Override
    public double caculateY(double x) {
        // TODO Auto-generated method stub
        //return 100 - Math.log(x);
        return x + 4.0*Math.sin(5*x)+9*Math.cos(4*x);
    }

    public static void main(String[] args) {
        GeneticAlgorithmTest test = new GeneticAlgorithmTest();
        test.caculte();
    }

}
