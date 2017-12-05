package net.azhel.algorithm.genetic;

import java.util.Arrays;
import java.util.Random;

public class Chromosome implements Cloneable{
    private final static int BINARY_SIZE_ARGUMENT = 10;
    private final static double MUTATE_PERCENT = 0.5d;

    private String chromosome;
    private int countGen;
    private double valueFitness;


    private static Random random = new Random(System.currentTimeMillis());

    public Chromosome(){}

    public Chromosome(int... arguments){
        chromosome = argumentsToChromosome(arguments);
        countGen = chromosome.length();
    }

    private Chromosome(String chromosome, double valueFitness){
        this.chromosome = chromosome;
        countGen = chromosome.length();
        this.valueFitness = valueFitness;
    }

    /*
    * Метод срещивает две хромосомы путем взятия случайного количества генов
    * одного родителя и оставшихся генов другого. В результате образуются две хросомы с генами
    * двух родителей.
     */
    public void cross(Chromosome parentChromosome){
        int countGenSwap =  random.nextInt(countGen);
        Chromosome parentChromosome2 = (Chromosome) this.clone();
        //swap gen
        this.chromosome = this.chromosome.substring(0, countGenSwap)
                + parentChromosome.getChromosome().substring(countGenSwap, parentChromosome.countGen);
        parentChromosome.setChromosome(parentChromosome.getChromosome().substring(0, countGenSwap)
                + parentChromosome2.chromosome.substring(countGenSwap, countGen));
    }

    /*
    *
     */
    public void mutate(){
        /*StringBuilder mutateStringBuilder = new StringBuilder();
        for (int i = 0; i < chromosome.length(); i++) {                                                                                                      /////
            char c = MUTATE_PERCENT >= random.nextDouble() ? chromosome.charAt(i) == '1' ? '0': '1': chromosome.charAt(i);
            mutateStringBuilder.append(c);
       }
        chromosome = mutateStringBuilder.toString();*/
        int[] arguments = getArguments();
        for (int i = 0; i < arguments.length; i++) {
            if (MUTATE_PERCENT >= random.nextDouble()){
                double mutate = random.nextDouble() * 0.4 - 0.4 /2;
                arguments[i] += arguments[i] * mutate;
            }
        }
            chromosome = argumentsToChromosome(arguments);
            countGen = chromosome.length();
    }

    /*
    * Метод возращает массив агруметов из хромосомы
     */
    public int[] getArguments(){
        int countArguments = chromosome.length() / BINARY_SIZE_ARGUMENT;
        int[] arguments = new int[countArguments];
        //String[] argumentString = new String[countArguments];
        for (int i = 0; i < countArguments; i++) {
            String argument = chromosome.substring(i * BINARY_SIZE_ARGUMENT, (i+1) * BINARY_SIZE_ARGUMENT);
            arguments[i] = Integer.parseInt(argument, 2);
        }
        return arguments;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public Object clone(){
        return new Chromosome(chromosome, valueFitness);
    }

    public double getValueFitness() {
        return valueFitness;
    }

    public void setValueFitness(double valueFitness) {
        this.valueFitness = valueFitness;
    }

    /*
     * Метод преобразует аргументы из целочислового вида в хромосому двоичного вида
     */
    private String argumentsToChromosome(int[] arguments){
        StringBuilder result = new StringBuilder();
        for (int argument : arguments) {
            result.append(argumentToBinary(argument));
        }
        return result.toString();
    }

    /*
       *Метод преобразует аргумент из целочислового вида в двоичный вид
     */
    private String argumentToBinary(int argument){
        String binary = Integer.toBinaryString(argument);
        if (binary.length() != BINARY_SIZE_ARGUMENT) {
            int prefixSize = BINARY_SIZE_ARGUMENT - binary.length();
            StringBuilder prefixBuilder = new StringBuilder();
            for (int i = 0; i < prefixSize; i++) {
                prefixBuilder.append("0");
            }
            return prefixBuilder.append(binary).toString();
        }
        return binary;
    }
}
