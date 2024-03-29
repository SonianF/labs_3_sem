import functions.*;
import functions.basic.Cos;
import functions.basic.Sin;
import functions.basic.Exp;
import functions.basic.Log;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        Sin sinus = new Sin();
        Cos cosinus = new Cos();
        Function tabuledSinus = TabulatedFunctions.tabulate(sinus, 0, 2*Math.PI, 10) ;
        Function tabuledCosinus = TabulatedFunctions.tabulate(cosinus, 0, 2*Math.PI, 10) ;
        for (double i =0; i<2*Math.PI; i+=0.1) {
            System.out.print("x=");
            System.out.printf("%.1f", i);
            System.out.println(". Значение функции: ");
            System.out.println("sin(x)=" + sinus.getFunctionValue(i) + ", cos(x)= " + cosinus.getFunctionValue(i));
            System.out.println("Значение табулированной функции: ");
            System.out.println("sin(x)=" + tabuledSinus.getFunctionValue(i) + ", cos(x)= " + tabuledCosinus.getFunctionValue(i));
            System.out.println();
        }
        Function func = Functions.sum(Functions.power(tabuledSinus,2), Functions.power(tabuledCosinus, 2));
        System.out.println("Значение функции f(x)=sin(x)^2 + cos(x)^2: ");
        for (double i=0; i<2*Math.PI; i+=0.1) {
            System.out.print("x=");
            System.out.printf("%.1f", i);
            System.out.printf(", f(x)= " + func.getFunctionValue(i));
            System.out.println();
        }

        try {
            TabulatedFunction tabulatedExp = TabulatedFunctions.tabulate(new Exp(), 0, 10, 11);
            //вывод твбулированной экспоненты в файл
            FileWriter out = new FileWriter("out.txt");
            TabulatedFunctions.writeTabulatedFunction(tabulatedExp, out);
            out.close();

            //считывание данных из файла
            FileReader in = new FileReader("out.txt");
            TabulatedFunction inputExp = TabulatedFunctions.readTabulatedFunction(in);
            in.close();

            //вывод значений функции в консоль
            System.out.println("\n" + "Значение табулированной экспоненты f1(x) и функции, считанной из файла f2(x):");
            for (double i = 0; i <= 10; i++) {
                System.out.println("x=" + i + " , f1(x)=" + tabulatedExp.getFunctionValue(i) + " , f2(x)= " + inputExp.getFunctionValue(i));
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            TabulatedFunction tabulatedLog = TabulatedFunctions.tabulate(new Log(Math.E), 0, 10, 11);
            //выводим табулированный логарифм в файл
            FileOutputStream out = new FileOutputStream("out2.bin");
            TabulatedFunctions.outputTabulatedFunction(tabulatedLog, out);
            out.close();

            //считываем данные из файла
            FileInputStream in = new FileInputStream("out2.bin");
            TabulatedFunction inputExp = TabulatedFunctions.inputTabulatedFunction(in);
            in.close();

            //выводим значения функций в консоль
            System.out.println("\n" + "Значения табулированного логарифма f1(x) и функции, считанной из файла f2(x):");
            for (double i = 0; i <= 10; i++) {
                System.out.println("x=" + i + " , f1(x)=" + tabulatedLog.getFunctionValue(i) + ", f2(x)=" + inputExp.getFunctionValue(i));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            //сериализация в файл - процесс преобразования объекта в поток байтов
            TabulatedFunction tabulatedLogExp = TabulatedFunctions.tabulate(Functions.composition(new Log(), new Exp()), 0, 10, 11);
            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream("out3.bin"));
            objOut.writeObject(tabulatedLogExp);
            objOut.close();

            //десериализация
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream("out3.bin"));
            TabulatedFunction deserializedFunction = (TabulatedFunction) objIn.readObject();
            objIn.close();

            //вывод в консоль
            System.out.println("\n" + "Значения табулированного логарифма, взятого от экспоненты f1(x) и функции, десериализованной из файла f2(x):");
            for (double i = 0; i <= 10; i++) {
                System.out.println("x=" + i + " , f1(x)=" + tabulatedLogExp.getFunctionValue(i) + ", f2(x)=" + deserializedFunction.getFunctionValue(i));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Wrong object type");
        }
    }

}
