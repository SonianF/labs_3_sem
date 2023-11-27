package functions.basic;

import functions.Function;
public class Log implements Function{
    double base; //основание логарифма
    public Log(){
        base = Math.E;
    }

    public Log(double b) {
        base = b;
    }

    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.log(x)/Math.log(base);
    }
}
