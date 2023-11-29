package functions.meta;

import functions.Function;

public class Scale implements Function{
    Function function;
    double kX;
    double kY;

    public Scale(Function func, double kX1, double kY1) {
        function = func;
        kX=kX1;
        kY=kY1;
    }

    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder()*kX;
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder()*kX;
    }

    @Override
    public double getFunctionValue(double x) {
        return function.getFunctionValue(x)*kY;
    }
}
