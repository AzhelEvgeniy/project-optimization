package net.azhel.algorithm.genetic;

import net.azhel.algorithm.function.Function;
import net.objecthunter.exp4j.function.Functions;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.linear.LinearConstraint;
import org.apache.commons.math.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math.optimization.linear.Relationship;
import org.apache.commons.math.optimization.linear.SimplexSolver;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class GeneticAlgorithm_Test {
    Function function;
    GeneticAlgorithm geneticAlgorithm;

    @Before
    public void init(){
       /*String targetFunction = "82.5*x1 + 70 * x2 + 78 * x3";
        List<String> limits = Arrays.asList(
                "2.5 * x1 + 1.8 * x2 + 2 * x3 <= 2250",
                "2 * x1 + 1.6 * x2 + 2.2 * x3 <= 2250",
                "x1 - 200 >= 0",
                "x2 - 200 >= 0",
                "x3 - 200 >= 0"
        );*/
      /*String targetFunction = "21 * x1 + 18 * x2 + 16 * x3 + 17.5 * x4";
        List<String> limits = Arrays.asList(
                "(8 * x1 + 7 * x2 + 5 * x3 + 9 * x4) <= 22",
                "(8 * x1 + 9 * x2 + 7 * x3 + 8 * x4) <= 25",
                "(10 * x1 + 9* x2 + 9 * x3 + 7 * x4) <= 38",
                "(10 * x1 + 11 * x2 + 11 * x3 + 6 * x4) <= 30",
                "x1 >= 0",
                "x2 >= 0",
                "x3 >= 0",
                "x4 >= 0"
        );*/
        /*String targetFunction = "5.5 * x";
        List<String> limits = Arrays.asList(
               "6 * x <= 66",
                "x >= 0"
        );*/

       /*String targetFunction = "60*x1 + 40*x2";
        List<String> limits = Arrays.asList(
                "10*x1 + 8*x2 <= 8000",
                "x1<=600",
                "x2<=750",
                "x1 >= 0",
                "x2 >= 0"
        );*/
        String targetFunction = "2 * x1 + 3 * x2";
        List<String> limits = Arrays.asList(
                "3 * x1 + 3 * x2 <= 15",
                "2 * x1 + 6 * x2 <= 18",
                "4 * x1 + 0 * x2 <= 16",
                "1 * x1 + 2 * x2 <= 8"

        );
        function = new Function(targetFunction, limits);

        geneticAlgorithm = new GeneticAlgorithm(function, "max");
    }

    @Test
    public void calculate_test() {
        //System.out.println(Arrays.toString(geneticAlgorithm.calculate()));
        //System.out.println((double)GeneticAlgorithm.time / 1000000000.0);
        //System.out.println(function.limit(new int[] {2, 0, 1, 0}));
    }
}
