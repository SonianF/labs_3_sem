import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
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

    public static void main(String[] args) throws InterruptedException{
        Exp exp = new Exp();
        System.out.println("Интеграл экспоненты:");
        System.out.println(Functions.integral(exp, 0, 1, 0.000000000000001));
        System.out.println("NonThread: ");
        nonThread();
        System.out.println("\n");
        System.out.println("simpleThreads:");
        simpleThreads();
        System.out.println("\n");
        System.out.println("complicatedThreads:");
        complicatedThreads();
    }
}