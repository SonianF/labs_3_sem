package functions.meta;

import functions.Function;

public class Sum implements Function {
    Function f1;
    Function f2;

    public Sum(Function func1, Function func2) {
        f1=func1;
        f2=func2;
    }

    @Override
    public double getLeftDomainBorder() {
        return Math.max(f1.getLeftDomainBorder(), f2.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return Math.min(f1.getRightDomainBorder(), f2.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return f1.getFunctionValue(x)+f2.getFunctionValue(x);
    }
}
