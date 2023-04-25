import java.util.*;

public class Main {
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        String[] texts = new String[25];
        Runnable logic1 = () -> {
            for (int i = 0; i < texts.length; i++) {
                texts[i] = generateText("aab", 30000);
            }
        };
        Runnable logic2 = () -> {
            long startTs = System.currentTimeMillis(); // start time
            for (String text : texts) {
                int maxSize = 0;
                for (int i = 0; i < text.length(); i++) {
                    for (int j = 0; j < text.length(); j++) {
                        if (i >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = i; k < j; k++) {
                            if (text.charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - i) {
                            maxSize = j - i;
                        }
                    }
                }
                System.out.println(text.substring(0, 100) + " -> " + maxSize);
            }
            long endTs = System.currentTimeMillis(); // end time
            System.out.println("Time: " + (endTs - startTs) + "ms");
        };
        List<Thread> threads = new ArrayList<>();
        threads.add(new Thread(logic1));
        threads.add(new Thread(logic2));
        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }
    }
}