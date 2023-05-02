import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger threeLetters = new AtomicInteger(0);
    public static AtomicInteger fourLetters = new AtomicInteger(0);
    public static AtomicInteger fiveLetters = new AtomicInteger(0);
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
    public static boolean checkFirstCriteria(String text){
        char[] textArray = text.toCharArray();
        char[] reverseArray = new char[textArray.length];
        for (int i = 0; i < textArray.length; i++) {
            reverseArray[reverseArray.length-1-i] = textArray[i];
        }
        if (textArray.toString().equals(reverseArray.toString())){
            return true;
        }
        return false;
    }
    public static boolean checkSecondCriteria(String text){
        char[] textArray = text.toCharArray();
        boolean notHasOtherSymbols = true;
        for (int i = 1; i < textArray.length; i++) {
            if(textArray[i] != textArray[0]){
                notHasOtherSymbols = false;
            }
        }
        if (notHasOtherSymbols){
            return true;
        }
        return false;
    }

    public static boolean checkThirdCriteria(String text){
        char[] textArray = text.toCharArray();
        if (textArray[0] == 'a'){
            for (int i = 0; i < textArray.length; i++) {
                if(textArray[i] == 'b'){
                    if(text.substring(i).contains("a")){
                        return false;
                    }
                }
                if(textArray[i] == 'c'){
                    if(text.substring(i).contains("a") || text.substring(i).contains("b")){
                        return false;
                    }
                }
            }
        }
        if (textArray[0] == 'b'){
            for (int i = 0; i < textArray.length; i++) {
                if(text.contains("a")){
                    return false;
                }
                if(textArray[i] == 'c'){
                    if(text.substring(i).contains("b")){
                        return false;
                    }
                }
            }
        }
        if (textArray[0] == 'c'){
            return false; //Всегда false, потому что либо не подходит, либо это другой критерий
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread thread1 = new  Thread(()->{
            for (int i = 0; i < texts.length; i++) {
                if(checkFirstCriteria(texts[i])){
                    if (texts[i].length() == 3){
                        threeLetters.getAndIncrement();
                    }
                    if (texts[i].length() == 4){
                        fourLetters.getAndIncrement();
                    }
                    if (texts[i].length() == 5){
                        fiveLetters.getAndIncrement();
                    }
                }
            }
        });

        Thread thread2 = new Thread(()->{
            for (int i = 0; i < texts.length; i++) {
                if(checkSecondCriteria(texts[i])){
                    if (texts[i].length() == 3){
                        threeLetters.getAndIncrement();
                    }
                    if (texts[i].length() == 4){
                        fourLetters.getAndIncrement();
                    }
                    if (texts[i].length() == 5){
                        fiveLetters.getAndIncrement();
                    }
                }
            }
        });

        Thread thread3 = new Thread(()->{
            for (int i = 0; i < texts.length; i++) {
                if(checkThirdCriteria(texts[i])){
                    if (texts[i].length() == 3){
                        threeLetters.getAndIncrement();
                    }
                    if (texts[i].length() == 4){
                        fourLetters.getAndIncrement();
                    }
                    if (texts[i].length() == 5){
                        fiveLetters.getAndIncrement();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + threeLetters +" штук");
        System.out.println("Красивых слов с длиной 4: " + fourLetters +" штук");
        System.out.println("Красивых слов с длиной 5: " + fiveLetters +" штук");
    }
}
