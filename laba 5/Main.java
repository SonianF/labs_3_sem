import functions.*;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Проверка метода toString() для обеих фукций:");
        double[] array = {1, 2, 3, 4, 5, 6, 7,8} ;
        ArrayTabulatedFunction func1 = new ArrayTabulatedFunction(1, 8, array);
        LinkedListTabulatedFunction func2 = new LinkedListTabulatedFunction(1, 8, array);
        System.out.println(func1.toString());
        System.out.println(func2.toString());
        System.out.println("\n");

        System.out.println("Проверка метода equals() для одинаковых функций:");
        System.out.println("Первая функция:" + func1.toString());
        System.out.println("Вторая функция:" + func2.toString());
        System.out.println("Равны ли функции? " + func1.equals(func2));
        System.out.println("\n");

        System.out.println("Проверка метода equals() для разных функций:");
        double[] array2 = {0, 2, 3, 4, 5, 6, 7,8} ;
        LinkedListTabulatedFunction func3 = new LinkedListTabulatedFunction(1, 8, array2);
        System.out.println("Первая функция:" + func1.toString());
        System.out.println("Вторая функция:" + func3.toString());
        System.out.println("Равны ли функции? " + func1.equals(func3));
        System.out.println("\n");

        System.out.println("Проверка метода equals() для разных функций одинаковых объектов:");
        ArrayTabulatedFunction func4 = new ArrayTabulatedFunction(1, 8, array2);
        System.out.println("Первая функция:" + func1.toString());
        System.out.println("Вторая функция:" + func4.toString());
        System.out.println("Равны ли функции? " + func1.equals(func4));
        System.out.println("\n");

        System.out.println("Проверка метода hashCode() для разных функций использованных объектов:");
        System.out.println("Первая функция:" + func1.toString() + ". Её хеш: " + func1.hashCode());
        System.out.println("Вторая функция:" + func2.toString() + ". Её хеш: " + func2.hashCode());
        System.out.println("Третья функция:" + func3.toString() + ". Её хеш: " + func3.hashCode());
        System.out.println("Четвертая функция:" + func4.toString() + ". Её хеш: " + func4.hashCode());


    }
}
