import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static final Map<Integer, Integer> sizeToFreq = new TreeMap<>();

    public static void main(String[] args) throws InterruptedException {

        Runnable logic = () -> {
            String result = generateRoute("RLRFR", 100);
            Pattern pattern = Pattern.compile("R");
            Matcher matcher = pattern.matcher(result);
            int n = 0;
            while (matcher.find()) {
                n++;
            }
            synchronized (sizeToFreq) {
                if (sizeToFreq.containsKey(n)) {
                    int amount = sizeToFreq.get(n);
                    sizeToFreq.remove(n);
                    sizeToFreq.put(n, amount + 1);
                } else {
                    sizeToFreq.put(n, 1);
                }
                sizeToFreq.notify();
            }
        };
        new Thread(() -> {
            int max = 0;
            int maxKey = 0;
            while (!Thread.interrupted()) {
                synchronized (sizeToFreq) {
                    try {
                        sizeToFreq.wait();
                        for (int key : sizeToFreq.keySet()) {
                            if (sizeToFreq.get(key) > max) {
                                max = sizeToFreq.get(key);
                                maxKey = key;
                            }
                        }
                        System.out.println("Текущее самое частое количество повторений " + maxKey + " (встретилось " + sizeToFreq.get(maxKey) + " раз)");

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
        for (int i = 0; i <= 1000; i++) {
            Thread thread = new Thread(logic);
            thread.start();
            thread.join();
            thread.interrupt();
        }
    }
}
