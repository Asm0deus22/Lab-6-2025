import functions.Functions;
import functions.basic.Log;
import threads.*;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws Exception{
        //nonThread();
        //simpleThreads();
        complicatedThreads();
    }
    public static void complicatedThreads() throws Exception{
        Task t = new Task();
        t.setIterations(10000);
        Semaphore s = new Semaphore(1,true);

        Integrator i = new Integrator(t, s);
        Generator g = new Generator(t, s);

        g.start();
        i.start();

        Thread.sleep(50);
        g.interrupt();
        i.interrupt();
    }
    public static void simpleThreads() {
        Task t = new Task();
        t.setIterations(100);

        SimpleIntegrator si = new SimpleIntegrator(t);
        SimpleGenerator sg = new SimpleGenerator(t);

        new Thread(si).start();
        try {
            Thread.sleep(1000);
        } catch (Exception err) { err.printStackTrace(); }
        new Thread(sg).start();
    }
    public static void nonThread() {
        Task t = new Task();
        t.setIterations(666);
        double value;
        while (t.getCurrentIteration() < t.getIterations()) {
            // 1
            t.setFunction(new Log(Math.random() * 9 + 1));
            // 2
            t.setLeftX(Math.random() * 100);
            // 3
            t.setRightX(100 + Math.random() * 100);
            // 4
            t.setStepIntegration(Math.random());
            // 5
            System.out.println("Source " + t.getLeftX() + " " + t.getRightX() + " " + t.getStepIntegration());
            // 6
            value = Functions.findIntegral(t.getFunction(), t.getLeftX(), t.getRightX(), t.getStepIntegration());
            // 7
            System.out.println("Result " + t.getLeftX() + " " + t.getRightX() + " " + t.getStepIntegration() + " " + value);

            t.incrementIteration();
        }
    }
}
