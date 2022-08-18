
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WaitingMessage implements Runnable {


    private static int counter = 0;
    private final CountDownLatch latch;
    private static int count = 0;
    public WaitingMessage(CountDownLatch latch){
        this.latch=latch;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                
                System.out.println("I am waiting for response from server!" +getcount() + "time");   
              latch.await(5 ,TimeUnit.SECONDS);
                counter++;
                count++;
                
               
            } catch (InterruptedException ex) {
                ex.getStackTrace();
            }
        }
    }

    public int getCounter() {
        return counter;
    }

    public void resetCounter() {
        counter = 0;
    }

    private void resetcount() {
        count=0;
    }

    private int getcount() {
        return count;
    }
}