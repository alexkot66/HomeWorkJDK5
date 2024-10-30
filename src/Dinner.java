import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dinner extends Thread {

    private int countPhilosophers;
    private Lock[] forks;
    private Philosopher[] philosophers;

    public Dinner(int countPhilosophers) {
        this.countPhilosophers = countPhilosophers;
        createForks();
        createPhilosophers();
    }

    private void createForks(){
        forks = new Lock[countPhilosophers];
        for (int i = 0; i < countPhilosophers; i++) {
            forks[i] = new ReentrantLock();
        }
    }

    private void createPhilosophers(){
        philosophers = new Philosopher[countPhilosophers];
        for (int i = 0; i < countPhilosophers; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % countPhilosophers]);
        }
    }

    @Override
    public void run() {
        startOfDinner();
        endOfDinner();

    }

    private void startOfDinner(){
        for (Philosopher philosopher : philosophers){
            philosopher.start();
        }
    }

    private void endOfDinner(){
        for (Philosopher philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
