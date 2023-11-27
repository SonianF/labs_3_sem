package functions;
import java.io.*;

public class TabulatedFunctions {
    private TabulatedFunctions() {
    }

    /*метод, получающий функцию и возвращающий её табулированный аналог на заданном отрезке с заданным количеством точек.*/
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (leftX<function.getLeftDomainBorder()||rightX> function.getRightDomainBorder())
            throw new IllegalArgumentException("The boundaries of the segment must lie inside the domain of the function");
        double step = (rightX-leftX)/(pointsCount-1); //вычисляем длину шага между точками
        FunctionPoint[] values = new FunctionPoint[pointsCount];
        for (int i=0; i<pointsCount; i++){
            values[i]=new FunctionPoint(leftX+i*step, function.getFunctionValue(leftX+i*step));
        }
        return new ArrayTabulatedFunction(values);
    }

    /*Метод вывода табулированной функции в байтовый поток должен в указанный поток вывести значения,
    по которым потом можно будет восстановить табулированную функцию, а именно количество точек в ней и значения координат точек.*/
    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) {
    try {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeInt(function.getPointsCount());
        for (int i = 0; i < function.getPointsCount(); ++i) {
            dos.writeDouble(function.getPointX(i));
            dos.writeDouble(function.getPointY(i));
        }
    }
    catch(IOException e){
        System.out.println(e.getMessage());
    }
    }

    /*Метод ввода табулированной функции из байтового потока
    должен считывать из указанного потока данные о табулированной функции, создавать и настраивать её объект и возвращать его из метода.*/
    public static TabulatedFunction inputTabulatedFunction(InputStream in) {
        try {
            DataInputStream dis = new DataInputStream(in);
            int count = dis.readInt();
            FunctionPoint[] values = new FunctionPoint[count];
            double x, y;
            for (int i = 0; i < count; ++i) {
                x = dis.readDouble();
                y = dis.readDouble();
                values[i] = new FunctionPoint(x, y);
            }
            return new ArrayTabulatedFunction(values);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayTabulatedFunction(0, 0, 0);
    }


    /*Метод записи табулированной функции в символьный поток должен в указанный поток вывести значения, по которым потом можно будет восстановить табулированную функцию,
    а именно количество точек в ней и значения координат точек. Проще всего считать, что значения записываются в строку и разделяются пробелами.*/
    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) {
        PrintWriter pw = new PrintWriter(out);
        pw.print(function.getPointsCount() + " ");
        for (int i=0; i< function.getPointsCount();++i) {
            pw.print(function.getPointX(i) + " ");
            pw.print(function.getPointY(i) + " ");
        }
    }

    /*Метод чтения табулированной функции из символьного потока должен считывать из указанного потока данные о табулированной функции,
    создавать и настраивать её объект и возвращать его из метода.*/

    public static TabulatedFunction readTabulatedFunction(Reader in) {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.parseNumbers();
        try{
            tokenizer.nextToken();
            int numOfPoints = (int) tokenizer.nval; //посчитали количество точек
            FunctionPoint[] values = new FunctionPoint[numOfPoints];
            double x, y;
            for (int i=0; i<numOfPoints; ++i){
                tokenizer.nextToken(); //берем из потока число
                x= tokenizer.nval; //записываем координату х
                tokenizer.nextToken(); //берем из потока следущее число
                y = tokenizer.nval; //записываем координату у
                values[i] = new FunctionPoint(x, y); //добавляем точку в массив
            }
            return new ArrayTabulatedFunction(values);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        return new ArrayTabulatedFunction(0, 0, 0);
    }
}
