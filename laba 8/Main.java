import functions.*;
import functions.basic.Cos;
import functions.basic.Log;
import functions.basic.Sin;
import threads.*;


public class Main {
    public static void nonThread() {
        Task task = new Task();
        task.countOfTasks = 100;
        for (int i = 0; i < task.countOfTasks; ++i) {
            double base = Math.random() * 9 + 1;
            task.f = new Log(base);
            task.leftX = Math.random() * 100;
            task.rightX = Math.random() * 100 + 100;
            task.dx = Math.random();
            System.out.println("Source " + task.leftX + ' ' + task.rightX + ' ' + task.dx);
            double integral = Functions.integral(task.f, task.leftX, task.rightX, task.dx);
            System.out.println("Result " + task.leftX + ' ' + task.rightX + ' ' + task.dx + ' ' + integral);
        }
    }

    public static void simpleThreads(){
        Task task=new Task();
        task.countOfTasks=100;
        SimpleGenerator generator=new SimpleGenerator(task);
        Thread generatorThread=new Thread(generator);
        SimpleIntegrator integrator=new SimpleIntegrator(task);
        Thread integratorThread=new Thread(integrator);
        generatorThread.start();
        integratorThread.start();
    }

    public static void complicatedThreads() throws InterruptedException {
        Task task=new Task();
        task.countOfTasks=1000;
        Semaphore semaphore=new Semaphore();
        Generator generator=new Generator(task, semaphore);
        Thread generatorThread=new Thread(generator);
        Integrator integrator=new Integrator(task, semaphore);
        Thread integratorThread=new Thread(integrator);
        generatorThread.setPriority(Thread.MIN_PRIORITY);
        integratorThread.setPriority(Thread.MAX_PRIORITY);
        generatorThread.start();
        integratorThread.start();

        Thread.currentThread().sleep(50);
        System.out.println("...Прошло 50 миллисекунд...");
        generatorThread.interrupt();
        integratorThread.interrupt();
    }

    public static void main(String[] args) throws InterruptedException, IllegalAccessException {
        System.out.println("Проверка работы задания 1:");
     TabulatedFunction function1 = new ArrayTabulatedFunction(2, 5, 4);
     for (FunctionPoint p: function1) {
         System.out.println(p);
     }
        System.out.println("Проверка работы задания 2:");
        Function f = new Cos();
     TabulatedFunction tf;
     tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
     System.out.println((tf.getClass()));

     TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
     tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
     System.out.println(tf.getClass());
     TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
     tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
     System.out.println(tf.getClass());

        System.out.println("----------------- Пример проверки работы рефлексивных методов (задание 3) --------------------------");
        TabulatedFunction f2;
        f2 = TabulatedFunctions.createTabulatedFunction(0, 10, 3, ArrayTabulatedFunction.class);
        System.out.println(f2.getClass());
        System.out.println(f2);

        f2 = TabulatedFunctions.createTabulatedFunction( 0, 10, new double[] {0, 10}, ArrayTabulatedFunction.class);
        System.out.println(f2.getClass());
        System.out.println(f2);

        f2 = TabulatedFunctions.createTabulatedFunction(new FunctionPoint[] {new FunctionPoint(0, 0), new FunctionPoint(10, 10) }, LinkedListTabulatedFunction.class);
        System.out.println(f2.getClass());
        System.out.println(f2);

        f2 = TabulatedFunctions.tabulate( new Sin(), 0, Math.PI, 11, LinkedListTabulatedFunction.class);
        System.out.println(f2.getClass());
        System.out.println(f2);


    }
}