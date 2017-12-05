package net.azhel.algorithm.simplex;

import net.azhel.algorithm.function.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.azhel.algorithm.simplex.SimplexMethod.TARGET.MAX;

public class SimplexTable {
    private double[][] table;
    private int row, column;
    private String[] rowHeader, columnHeader;
    private int countVariable;
    private int countAdditionalVariable;
    Function function;

    private enum Operator{
        MORE_AND_EQUALS,
        LESS_AND_EQUALS,
        EQUALS
    }
    SimplexMethod.TARGET target;

    SimplexTable(Function f, SimplexMethod.TARGET target){
        function = f;
        this.target = target;
        getStandardForm(f);
    }
    /*
    * Метод переводящий симплекс таблицу на следующую итерацию.
     */
    public void nextStep(){
        //найти индекс столбца минимальный отрицательный элемент в целевой функции
        int indexColumn = minElemTargetFunction();
        //высчитать деление свободного члена на элемент столбца минимального отрицательного элемента
        //найти минимальное деление и тем самым выделить разрешающий элемент
        int indexRow = findResolvingElementIndex(indexColumn);
        //transform
        //проверить найдено ли решение
        transform(indexRow, indexColumn);
    }

    /*
    * Метод проверяющий на заканчивание выполнение симлекс метода
     */
    public boolean isDone(){
        for (int i = 0; i < column; i++) {
            switch (target){
                case MAX:
                    if (table[row-1][i] < 0.0d) return false;
                    break;
                case MIN:
                    if (round(table[row-1][i], 2) > 0.0d) return false;
            }
        }
        return true;
    }

    /*
    * Метод возращающий результат оптимизации
     */
    public int[] getAnswer(){
        List variables = new ArrayList(function.getVariables());
        int[] result = new int[countVariable];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < countVariable; j++) {
                if (rowHeader[i].equals(variables.get(j))){
                    result[j] = (int) table[i][0];
                }
            }
        }
        return result;
    }

    /*
    * Метод возращает базисное решения исходя из математической модели
     */
    private void getStandardForm(Function f){

        countVariable = f.getCountVariable();
        //target function
        double[] targetFunctionConst = getExpressionConst(f.getTargetFunctionString());
               //limits
        List<String> limitList = f.getStrLimits();
        getCountAdditionalVariable(limitList); //count AdditionalVariable
        //count variable + answer + additional variable
        column = f.getCountVariable() + countAdditionalVariable + 1;
        double[][] limits = new double[limitList.size()][column];
        int indexAdditionalVariable = 0;
        for (int i = 0; i < limitList.size(); i++) {
            if (limitList.get(i).contains("<=") || limitList.get(i).contains(">=")) {
                limits[i] = parserLimit(limitList.get(i), indexAdditionalVariable++);
            }
            else limits[i] = parserLimit(limitList.get(i), -1);
        }
        row = limitList.size() + 1;
        table = new double[row][column];
        for (int i = 0; i < row - 1; i++) {
            table[i] = limits[i];
        }
        for (int i = 0; i < targetFunctionConst.length; i++) {
            table[row-1][i+1] = targetFunctionConst[i];
        }
        initHeader();
            admissibleBasis();
        switch (target){
            case MIN:
                for (int i = 0; i < targetFunctionConst.length; i++) {
                    targetFunctionConst[i] *= -1;
                }
        }
        //printTable();
    }

    private int findResolvingElementIndex(int indexColumn) {
        //высчитать деление свободного члена на элемент столбца минимального отрицательного элемента
        //найти минимальное деление и тем самым выделить разрешающий элемент
       double[] del = solveDel(indexColumn);
        int indexMinElem = 0;
        for (int i = 0; i < del.length; i++) {
            if(del[i] > 0.0d) {indexMinElem = i; break;}
        }
        for (int i = indexMinElem + 1; i < del.length; i++) {
            if(del[i] > 0.0d)
            if (del[i] < del[indexMinElem]){
                indexMinElem = i;
            }
        }
       return indexMinElem;
    }

    private double[] solveDel(int indexColumn) {
        double[] result = new double[row];
        for (int i = 0; i < row; i++) {
            result[i] = table[i][0] / table[i][indexColumn];
        }
        return result;
    }

    private int minElemTargetFunction() {
        int indexMinElem = 0;
        for (int i = 1; i < column; i++) {
            if (table[row -1][i] < table[row -1][indexMinElem]){
                indexMinElem = i;
            }
        }
        return indexMinElem;
    }
    /*
    * Метод создающий шапку Симплекс таблицы
     */
    private void initHeader(){
        rowHeader = new String[row];
        rowHeader[row-1] = "f(x)";
        for (int i = 0; i < row - 1; i++) {
            rowHeader[i] = "x" + (i + countVariable + 1);
        }
        columnHeader = new String[column];
        columnHeader[0] = "B";
        int i = 1;
        for (String variable : function.getVariables()){
            columnHeader[i] = variable;
            i++;
        }
        for (; i < column; i++) {
            columnHeader[i] = "x" + i;
        }
    }

    private double[] parserLimit(String limit, int index) {
        double[] row = new double[column];
        //answer
        row[0] = getConstWithoutArgument(limit);
        //const
        int i = 1;
        for (double constArg : getExpressionConst(limit)) {
            row[i++] = constArg;
        }
        //additionalVariable
        if (index != -1) {
            if (limit.contains("<=")) row[i + index] = 1;
            else {
                row[i + index] = -1;
                for (int j = 0; j < row.length; j++) {
                    row[j] = row[j] == 0 ? 0 : row[j] * -1;
                }
            }
        }
            return row;
    }

    private double[] getExpressionConst(String function){ //TODO исправить
        List<String> variables = new ArrayList(this.function.getVariables());
        double[] result = new double[variables.size()];
        /* const with argument*/
        Matcher m = Pattern.compile("[0-9\\.]*[ ]?[\\*]?[ ]?[A-Za-z]{1}[a-zA-Z0-9]*").matcher(function);
        while (m.find()) {
            String constWithVariable = m.group();
            for (int i = 0; i < variables.size(); i++) {
                if(constWithVariable.contains(variables.get(i))) {
                    /* const without argument*/
                    Matcher numberMather = Pattern.compile("[0-9\\.]*").matcher(constWithVariable);
                    if (numberMather.find()) {
                        String con = numberMather.group();
                        if (con.length() == 0) result[i] = 1;
                        else
                        result[i] += Double.valueOf(con);
                    }
                }
            }
        }
        return result;
    }

    private double getConstWithoutArgument(String function){
        double result = 0;
        result = Double.valueOf(function.substring(function.indexOf('=') + 1, function.length()).replace(" ", ""));
        return result;
    }

    private void getCountAdditionalVariable(List<String> limits){
        for (String limit : limits) {
            Matcher m = Pattern.compile("<=|>=").matcher(limit);
            while (m.find()) {
                countAdditionalVariable++;
            }
        }
    }
    /*
    * Метод жардонавски преобразований
     */
    private void transform(int indexRow, int indexColumn){
        double[][] transformTable = copyOf(table);
        rowHeader[indexRow] = columnHeader[indexColumn];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (i != indexRow)
                    transformTable[i][j] = table[i][j] - (table[indexRow][j] * table[i][indexColumn]) / table[indexRow][indexColumn];
                else
                    transformTable[i][j] = table[i][j] / table[indexRow][indexColumn];
            }
        }
        table = copyOf(transformTable);
    }

    private double[][] copyOf(double[][] original){
        double[][] copies = new double[row][column];
        for (int i = 0; i < row; i++) {
            copies[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copies;
    }
    /*
    * Метод преобразующий симплекс таблицу, если в решении есть
    * отрицательные элементы
     */
    private void admissibleBasis() {
        for (int i = 0; i < row; i++) {
            if (table[i][0] < 0)
                for (int j = 1; j < column; j++) {
                    if (table[i][j] < 0) {
                        transform(i, j);
                        break;
                    }
                }
        }
        //target function
        table[row - 1][0] = 0;
        for (int j = 1; j < column; j++) {
            table[row - 1][j] =  table[row - 1][j] == 0 ? 0 :  table[row - 1][j] * -1;
        }
    }

    public String[][] getTable() {
        int row = this.row + 1;
        int column = this.column + 1;
        String[][] result = new String[row][column];
        //header column
        result[0][0] = "";
        for (int i = 1; i < column; i++) {
            result[0][i] = columnHeader[i-1];
        }
        //header row
        for (int i = 1; i < row; i++) {
            result[i][0] = rowHeader[i-1];
        }
        //table
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < column; j++) {
                result[i][j] = String.valueOf(round(table[i-1][j-1],2));
            }
        }
        return result;
    }

    private double round(double value, int n){
        return Math.round(value * Math.pow(10, n)) / Math.pow(10, n);
    }
}
