package net.azhel.algorithm.function;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionTest {
    Function function;
    @Before
    public void init(){
        String targetFunctional = "82.5*x1 + 70 * x2 + 78 * x3";
        List<String> limits = Arrays.asList(
          "2.5 * x1 + 1.8 * x2 + 2 * x3 <= 2250",
          "2 * x1 + 1.6 * x2 + 2.2 * x3 <= 2250",
          "x1 >= 200",
          "x2 >= 200",
          "x3 >= 200"
        );
        function = new Function(targetFunctional, limits);
    }

    @Test
    public void f_test(){
        /*int[] arg = new int[] {200, 200, 695};
        double result = function.f(arg);
        if(Double.compare(result, 84710.0d) != 0){
            throw new AssertionError("Method f(int[]) return wrong value " + result);
        }*/
    }

    @Test
    public void limit_test(){
       /* //wrong argument
        int[] arg = new int[] {0, 200, 200};
        if(function.limit(arg))
            throw new AssertionError("Method checkLimit with wrong argument" + Arrays.toString(arg)
                    + "returned the  no correct answer");
        arg = new int[] {200, 0, 200};
        if(function.limit(arg))
            throw new AssertionError("Method checkLimit with wrong argument" + Arrays.toString(arg)
                    + "returned the  no correct answer");
        arg = new int[] {200, 200, 0};
        if(function.limit(arg))
            throw new AssertionError("Method checkLimit with wrong argument" + Arrays.toString(arg)
                    + "returned the  no correct answer");
        arg = new int[] {1000, 200, 1000};
        if(function.limit(arg))
            throw new AssertionError("Method checkLimit with wrong argument" + Arrays.toString(arg)
                    + "returned the  no correct answer");
        //correct argument
        arg = new int[] {200, 200, 695};
        if(!function.limit(arg))
            throw new AssertionError("Method checkLimit with wrong argument" + Arrays.toString(arg)
                    + "returned the  no correct answer");*/
    }

}
