package net.azhel.algorithm.simplex;

import net.azhel.algorithm.AlgorithmOptimization;
import net.azhel.algorithm.function.Function;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SimplexMethod_Test {
    SimplexMethod optimization;
    Function function;
    @Before
    public void init(){
        String targetFunction = "82.5*x1 + 70 * x2 + 78 * x3";
        List<String> limits = Arrays.asList(
                "2.5 * x1 + 1.8 * x2 + 2 * x3 <= 2250",
                "2 * x1 + 1.6 * x2 + 2.2 * x3 <= 2250",
                "x1 >= 200",
                "x2 >= 200",
                "x3 >= 200"
        );
       /* String targetFunction = "2 * x1 + 3 * x2";
        List<String> limits = Arrays.asList(
                "3 * x1 + 3 * x2 <= 15",
                "2 * x1 + 6 * x2 >= 18",
                "4 * x1 + 0 * x2 >= 16",
                "1 * x1 + 2 * x2 <= 8"

        );*/
        /*String targetFunction = "21 * x1 + 18 * x2 + 16 * x3 + 17.5 * x4";
        List<String> limits = Arrays.asList(
                "(8 * x1 + 7 * x2 + 5 * x3 + 9 * x4) <= 22",
                "(8 * x1 + 9 * x2 + 7 * x3 + 8 * x4) <= 25",
                "(10 * x1 + 9* x2 + 9 * x3 + 7 * x4) <= 38",
                "(10 * x1 + 11 * x2 + 11 * x3 + 6 * x4) <= 30"
        );*/
        function = new Function(targetFunction, limits);
        optimization = new SimplexMethod(function, "min");
    }
    @Test
    public void calculate_test(){
        System.out.println(Arrays.toString(optimization.calculate()));
        System.out.println(function.f(optimization.calculate()));
    }
}
