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
    public  static  final  Map<Integer, Integer> sizeToFreq = new TreeMap<>();
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
            }
        };
        for (int i = 0; i <= 1000; i++) {
            Thread thread = new Thread(logic);
            thread.start();
            thread.join();
        }
        int max = 0;
        int maxKey = 0;
        for (int key: sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > max){
                max = sizeToFreq.get(key);
                maxKey = key;
            }
        }
        System.out.println("Самое частое количество повторений "+maxKey+" (встретилось "+ sizeToFreq.get(maxKey)+ " раз)");
        System.out.println("Другие размеры:");
        for (int key: sizeToFreq.keySet()) {
            if (key != maxKey){
                System.out.println("- "+key+" ("+sizeToFreq.get(key)+" раз)");
            }
        }
    }

}
