import functions.FunctionPoint;
import functions.TabulatedFunction;
public class Main {
    public static void main (String[] args) {
        TabulatedFunction function = new TabulatedFunction(-3, 3, new double[]{ 9, 4, 1, 0, 1, 4 ,9});
        System.out.println("Начальный массив из " + function.getPointsCount()+  " элементов:");
        for (int i=0; i<function.getPointsCount(); i++) {
            System.out.println("[" +i+"]: (" + function.getPointX(i) + ", " + function.getPointY(i)+")");
        }
        System.out.println(function.getFunctionValue(2.8));
        function.addPoint(new FunctionPoint(-3.5, -1));
        function.addPoint(new FunctionPoint(0.5, 10));
        function.addPoint(new FunctionPoint(3.5, -1));
        System.out.println("После добавления 3х элементов массив из " + function.getPointsCount()+ " элементов:");
        for (int j=0; j<function.getPointsCount(); j++) {
            System.out.println("[" +j+"]: (" + function.getPointX(j) + ", " + function.getPointY(j)+")");
        }
        function.deletePoint(4);
        function.deletePoint(4);
        System.out.println("После удаления 2х элементов массив из " + function.getPointsCount()+" элементов:");
        for (int i=0; i<function.getPointsCount(); i++) {
            System.out.println("[" +i+"]: (" + function.getPointX(i) + ", " + function.getPointY(i)+")");
        }
}
}
