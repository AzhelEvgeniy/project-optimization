package net.azhel.algorithm.genetic;

import net.azhel.algorithm.AlgorithmOptimization;
import net.azhel.algorithm.function.Function;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class GeneticAlgorithm implements AlgorithmOptimization {
    private final int countGeneration;
    private final int countChromosomeInGeneration;
    private final int countArgument;

    //const
        private static final int DEFAULT_COUNT_CHROMOSOME_IN_GENERATION = 2200;
    private static final int DEFAULT_COUNT_GENERATION = 350;
    private static final int HEIGHT_VALUE = 1000;
    private static final int LOW_VALUE = 0;
    private static final double MUTATION_PERCENT = 0.399;

    private Function function;

    private static Random random = new Random(System.currentTimeMillis());

    private Chromosome[] generation;

    private int[][] progressGeneration;

    public static long time = 0;


    private TARGET target;

    private enum TARGET{
        MAX,
        MIN
    }

    public GeneticAlgorithm(Function function, String maxMin) {
        this(function, DEFAULT_COUNT_GENERATION, DEFAULT_COUNT_CHROMOSOME_IN_GENERATION, maxMin);
    }

    public GeneticAlgorithm(Function function, int countGeneration, int countChromosomeInGeneration, String maxMin) {
        this.countGeneration = countGeneration;
        this.countChromosomeInGeneration = countChromosomeInGeneration;
        this.countArgument = function.getCountVariable();
        this.function = function;
        //MAX MIN FUNCTION
        target = maxMin.equals("max") ? TARGET.MAX : TARGET.MIN;
    }

    public int[] calculate() {
        generateFirstGeneration();
        fitnessChromosomes();

        progressGeneration = new int[countGeneration][countArgument];

        int currentGeneration = 0;
        do{

            selection();
            crossing();
            mutate();
            fitnessChromosomes();

            Chromosome bestChromosomeInGeneration = bestChromosomeInGeneration();
            progressGeneration[currentGeneration] = bestChromosomeInGeneration.getArguments();
            System.out.println("Generation[" + currentGeneration + "] "  + Arrays.toString(bestChromosomeInGeneration.getArguments()) +
                    bestChromosomeInGeneration.getValueFitness());

        } while (++currentGeneration < countGeneration);
        return bestChromosomeInGeneration().getArguments();
    }
    //Создание первого покаления
    public void generateFirstGeneration() {
        generation = new Chromosome[countChromosomeInGeneration];
        int[] arguments = new int[countArgument];
        for (int i = 0; i < countChromosomeInGeneration; i++) {
            for (int j = 0; j < countArgument; j++) {
                arguments[j] = random.nextInt(HEIGHT_VALUE - LOW_VALUE) + LOW_VALUE;
            }
            generation[i] = new Chromosome(arguments);
        }
    }
    // Метод ищущий приспособления всех хромосом
    public void fitnessChromosomes(){
        long oldT = System.nanoTime();
        for (Chromosome chromosome : generation) {
            chromosome.setValueFitness(fitnessFunction(chromosome.getArguments()));
        }
        time += System.nanoTime() - oldT;
    }
    // Метод приспослбления
    public double fitnessFunction(int[] arguments){
        double result = function.f(arguments);
        switch (target) {
            case MAX:
                result -= function.limit(arguments);
                break;
            case MIN:
                result += function.limit(arguments);
                break;
        }
        return result;
    }
    //селекция
    public void selection(){
        torneiSelection();
    }
    //скрещивание
    private void crossing(){
        Chromosome[] parentGeneration = new Chromosome[countChromosomeInGeneration];
        int j = 0;
        for (Chromosome chromosome : generation) {
            parentGeneration[j] = (Chromosome) chromosome.clone();
                    j++;
        }
        for (int i = 0; i < countChromosomeInGeneration / 2; i++) {
            int firstIndex = countChromosomeInGeneration / 2 + i;
            int secondIndex = i;

            generation[firstIndex].cross(generation[secondIndex]);
        }
        generation = (Chromosome[]) ArrayUtils.addAll(parentGeneration, generation);
    }
    //Мутация
    private void mutate() {

        for (Chromosome chromosome : generation) {
            if (MUTATION_PERCENT >= random.nextDouble()) {
                chromosome.mutate();
            }
        }
    }
    //Лучший в покалении
    private Chromosome bestChromosomeInGeneration(){
        switch (target){
            case MAX:
                return Arrays.asList(generation).stream().max(new Comparator<Chromosome>() {
                    @Override
                    public int compare(Chromosome o1, Chromosome o2) {
                        return Double.compare(o1.getValueFitness(), o2.getValueFitness());
                    }
                }).get();
            case MIN:
                return Arrays.asList(generation).stream().min(new Comparator<Chromosome>() {
                    @Override
                    public int compare(Chromosome o1, Chromosome o2) {
                        return Double.compare(o1.getValueFitness(), o2.getValueFitness());
                    }
                }).get();
            default: throw new RuntimeException();
        }
    }
    //Турнирная селекция
    private void torneiSelection(){
        Chromosome[] chromosomesSelected = new Chromosome[countChromosomeInGeneration];
        for (int i = 0; i < countChromosomeInGeneration; i++) {
            int firstIndex = random.nextInt(generation.length);
            int secondIndex = random.nextInt(generation.length);
            int thirdIndex = random.nextInt(generation.length);

            double fitnessFirst = generation[firstIndex].getValueFitness();
            double fitnessSecond = generation[secondIndex].getValueFitness();
            double fitnessThird = generation[thirdIndex].getValueFitness();
            switch (target){
                case MAX:
                    chromosomesSelected[i] = fitnessFirst > fitnessSecond && fitnessFirst > fitnessThird ?
                            (Chromosome) generation[firstIndex].clone() : fitnessSecond > fitnessThird ?
                            (Chromosome) generation[secondIndex].clone() : (Chromosome) generation[thirdIndex].clone();
                    break;
                case MIN:
                    chromosomesSelected[i] = fitnessFirst < fitnessSecond && fitnessFirst < fitnessThird ?
                            (Chromosome) generation[firstIndex].clone() : fitnessSecond < fitnessThird ?
                            (Chromosome) generation[secondIndex].clone() : (Chromosome) generation[thirdIndex].clone();
                    break;
                default:
                    throw new RuntimeException();
            }
        }
        generation = Arrays.copyOf(chromosomesSelected, countChromosomeInGeneration);
    }
    // Ход решения генетического алгоритма
    public int[][] getProgressGeneration() {
        return progressGeneration;
    }
}

