package net.azhel.algorithm.function;

import net.objecthunter.exp4j.operator.Operator;

public class OperatorComparison {
    public static Operator moreAndEquals = new Operator("<=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
        @Override
        public double apply(double... doubles) {
            if (doubles[0] <= doubles[1])
                return 1d;
            else
                return 0d;
        }
    };
    public static Operator lessAndEquals = new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

        @Override
        public double apply(double[] values) {
            if (values[0] >= values[1]) {
                return 1d;
            } else {
                return 0d;
            }
        }
    };
    public static Operator equals = new Operator("=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {

        @Override
        public double apply(double[] values) {
            if (values[0] == values[1]) {
                return 1d;
            } else {
                return 0d;
            }
        }
    };
}
