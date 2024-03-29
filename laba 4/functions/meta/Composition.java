package functions.meta;

import functions.Function;

public class Composition implements Function {
    Function f1;
    Function f2;

    public Composition (Function func1, Function func2) {
        f1=func1;
        f2=func2;
    }

    @Override
    public double getLeftDomainBorder() {
        return f1.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return f1.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return f1.getFunctionValue(f2.getFunctionValue(x));
    }
}
