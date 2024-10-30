import java.util.concurrent.locks.Lock;

public class Philosopher extends Thread{

    private final int id;
    private final Lock leftFork;
    private final Lock rightFork;
    private int counter;

    public Philosopher(int id, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        counter = 1;
    }

    @Override
    public void run() {
        processDinner();
    }

    private void processDinner(){
        while(counter <= 3) {
                think();
                eat();
        }
    }

    private void eat() {
        // Берем левую вилку и потом правую вилку
        try {
            leftFork.lock();
            if (rightFork.tryLock()) {
                try {
                    System.out.println("Философ " + id + " покушал " + counter + " раз");
                    counter++;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    rightFork.unlock(); // Разблокировка правой вилки
                }
            }
        } finally {
            leftFork.unlock(); // Разблокировка левой вилки
        }
    }

    private void think() {
        System.out.println("Философ " + id + " думает.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
