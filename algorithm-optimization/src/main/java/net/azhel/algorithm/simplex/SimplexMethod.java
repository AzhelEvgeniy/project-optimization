package net.azhel.algorithm.simplex;

import net.azhel.algorithm.AlgorithmOptimization;
import net.azhel.algorithm.function.Function;

import java.util.ArrayList;
import java.util.List;

public class SimplexMethod implements AlgorithmOptimization{
    private SimplexTable simplesTable;
    private final Function function;
    private List<String[][]> processCalculate = new ArrayList<>();
    private TARGET target;

    enum TARGET{
        MAX,
        MIN
    }
    /*
    * Конструктор принимающий математическую модель и цель оптимизации
    * @param f математическая модель
    * @param maxMin цель оптимизации
     */
    public SimplexMethod(Function f, String maxMin){
        function = f;
        if (maxMin.equals("max")) target = TARGET.MAX;
        else target = TARGET.MIN;
    }


    @Override
    public int[] calculate() {
        simplesTable = new SimplexTable(function, target);
        processCalculate.add(simplesTable.getTable());
        while (!simplesTable.isDone()) {
            simplesTable.nextStep();
            processCalculate.add(simplesTable.getTable());
        }
        return simplesTable.getAnswer();
    }
    /*
    * Метод возращающий  ход решения симплекс методом
    */
    public List<String[][]> getProgressCalculate(){
        return processCalculate;
    }


}
