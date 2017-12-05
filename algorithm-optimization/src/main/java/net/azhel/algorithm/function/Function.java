package net.azhel.algorithm.function;

import net.azhel.algorithm.genetic.GeneticAlgorithm;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Function {

    private Expression targetFunction;
    private String targetFunctionString;
    private List<String> strLimits = new ArrayList<>();
    private List<Expression> limits = new ArrayList<>();
    private List<Expression> limitRight = new ArrayList<>();
    private Set<String> variables;

    private static String[] defaultFunctionName = new String[]{
            "sin", "cos", "tan", "cot",
            "log", "log2", "log10", "log1p",
            "abs", "acos", "asin", "atan",
            "cbrt", "floor", "sinh", "sqrt",
            "tanh", "cosh", "ceil", "pow",
            "exp", "expm1", "signum"
    };

    public Function(){

    }

    public Function(String targetFunction, List<String> limits) {
        setTargetFunction(targetFunction);
        setLimits(limits);
    }

    public double f(int[] arguments){
        targetFunction = fillVariableArgument(targetFunction, arguments);
        return targetFunction.evaluate();
    }

    public double limit(int[] arguments){
        double result = 0;
        for (int i = 0; i < limits.size(); i++) {
            limits.set(i, fillVariableArgument(limits.get(i), arguments));
            limitRight.set(i, fillVariableArgument(limitRight.get(i), arguments));
            if (limits.get(i).evaluate() == 0) {
                result += Math.pow(limitRight.get(i).evaluate(), 2) + 10_000_000;
            }
        }
        return result;
    }

    public int getCountVariable(){
        return variables.size();
    }

    private void setTargetFunction(String targetFunction) {
        targetFunctionString = targetFunction;
        this.variables = getVariableInFunction(targetFunction);
        this.targetFunction = new ExpressionBuilder(targetFunction).variables(variables).build();
    }

    private void setLimits(List<String> limits) {
        if (targetFunction == null) throw new RuntimeException(); //TODO selected exception
        for (String limit : limits) {
            strLimits.add(limit);
            Expression expression = new ExpressionBuilder(limit).variables(variables).operator(OperatorComparison.moreAndEquals,
                    OperatorComparison.equals, OperatorComparison.lessAndEquals).build();
            Expression expression1 = new ExpressionBuilder(limit.substring(0, limit.indexOf("=") - 1)).variables(variables).build();
            limitRight.add(expression1);
            this.limits.add(expression);
        }
    }

    public Set<String> getVariableInFunction(String function){
        Set<String> variables = new LinkedHashSet<>();
        Pattern regexp = Pattern.compile("[A-Za-z]{1}[a-zA-Z0-9]*");
        Matcher m = regexp.matcher(function);
        while (m.find()) {
            String variable = m.group();
            if (variable.length() < 3) variables.add(variable);
            else{
                if(Arrays.asList(defaultFunctionName).parallelStream().filter(s -> s.equals(variable)).toArray().length == 0){
                    variables.add(variable);
                }
            }
        }
        return variables;
    }

    private Expression fillVariableArgument(Expression expression, int[] arguments){
        int i = 0;
        for (String variable : variables) {
            expression.setVariable(variable, arguments[i++]);
        }
        return expression;
    }

    public String getTargetFunctionString() {
        return targetFunctionString;
    }

    public List<String> getStrLimits() {
        return strLimits;
    }

    public Set<String> getVariables() {
        return variables;
    }
}
