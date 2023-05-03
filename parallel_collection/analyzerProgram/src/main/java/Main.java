import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;


public class Main {
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            Queue queue = new ArrayBlockingQueue(100);
        });
        Thread thread2 = new Thread(() -> {
            Queue queue = new ArrayBlockingQueue(100);
        });
        Thread thread3 = new Thread(() -> {
            Queue queue = new ArrayBlockingQueue(100);
        });
        Thread thread4 = new Thread(() -> {
            Queue queue = new ArrayBlockingQueue(100);
        });
    }
}
