import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Main {
    public static BlockingQueue<String> qa = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> qb = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> qc = new ArrayBlockingQueue<>(100);
    public static Thread generator;
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }


    public static int textAnalyzer(BlockingQueue<String> queue, char letter){
        int max = 0;
        int counter = 0;
        String str;
        try {
            while (generator.isAlive()){
                str = queue.take();
                for (char ch:str.toCharArray()) {
                    if (ch == letter){
                        counter++;
                    }
                }
                if (counter > max){
                    max = counter;
                }
                counter = 0;
            }
        }catch (InterruptedException e){
            System.out.println(Thread.currentThread().getName()+" is interrupted");
        }
        return max;
    }
    public static Thread newThread(BlockingQueue<String> queue, char letter){
        return new Thread(()->{
            int max = textAnalyzer(queue, letter);
            System.out.println("Максимальное количество символов "+letter+" равно " + max);
        });
    }

    public static void main(String[] args) throws InterruptedException {
         generator = new Thread(() ->{
            for (int i = 0; i < 10_000; i++) {
                String str = generateText("abc", 100_000);
                qa.offer(str);
                qb.offer(str);
                qc.offer(str);
            }
        });
        generator.start();
        Thread a = newThread(qa, 'a');
        Thread b = newThread(qb, 'b');
        Thread c = newThread(qc, 'c');
        a.start();
        b.start();
        c.start();
        a.join();
        b.join();
        c.join();

    }
}
