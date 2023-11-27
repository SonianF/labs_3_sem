package functions.meta;

import functions.Function;

public class Shift implements Function {
    Function function;
    double deltaX;
    double deltaY;

    public Shift(Function func, double deltaX1, double deltaY1) {
        function = func;
        deltaX=deltaX1;
        deltaY=deltaY1;
    }

    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder()+deltaX;
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder()+deltaX;
    }

    @Override
    public double getFunctionValue(double x) {
        return function.getFunctionValue(x)+deltaY;
    }
}
