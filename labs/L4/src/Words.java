import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

class Word {

    private String w;

    Word (String w) { this.w = w; }

    /**
     * this functionally reduces our hash structure (in our case, HashSet) to a list (due to hashing at the same spot)
     * after your tests are created, modify this hash function to something of your choice
     * see how much you can improve your timing :)
     * (YOU MAY NOT USE BUILT-IN hashCode)
     */
    // TODO after testing
    public int hashCode () {

        // 9th: ~ 35 ms
        int sum = 0;
        for(int i = 0; i < w.length(); i++) {
            sum += sum * 7 + w.charAt(i);
        }
        return sum;

        // 8th: ~ 40 ms
//        int sum = 0;
//        for(int i = 0; i < w.length(); i++) {
//            sum += sum * 3 + w.charAt(i);
//        }
//        return sum;

        // 7th: ~ 32 ms
//        int sum = 0;
//        for(int i = 0; i < w.length(); i++) {
//            sum += sum * 5 + w.charAt(i);
//        }
//        return sum;

        // 6th: ~ 15966 ms
//        int sum = 0;
//        for(int i = 0; i < w.length(); i++) {
//            sum *= w.charAt(i);
//        }
//        return sum;

        // 5th: ~ 121 ms
//        int sum = 0;
//        for(int i = 0; i < w.length(); i++) {
//            sum += (int) w.charAt(i) * 7;
//        }
//        return sum;

        // 4th: ~ 563 ms
//        int sum = 0;
//        for(int i = 0; i < w.length(); i++) {
//            sum += (int) w.charAt(i) % 7;
//        }
//        return sum;

        // 4th: ~ 110 ms
//        int sum = 0;
//        for(int i = 0; i < w.length(); i++) {
//            sum += (int) w.charAt(i);
//        }
//        return sum;

        // 3rd: ~ 2405 ms
//        return w.length() % 7;

        // 2nd: ~ 1810 ms
//        return w.length() * 2;

        // 1st: ~ 1799 ms
//        return w.length();

        // Given: ~ 15904 ms
//        return 7;
    }
}

class Words {

    static String filename = "commonwords.txt";
    // from http://www.mieliestronk.com/corncob_lowercase.txt
    static List<String> slist;
    static List<Word> wlist;

    static {
        try {
            // note that objects in our slist will have the default hashCode; whereas wlist is a list of objects
            // from our Word class that has our own hashCode method implemented
            slist = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
            wlist = slist.stream().map(Word::new).collect(Collectors.toList());

        } catch (IOException e) {
            throw new Error();
        }
    }

    public static void stringsToHashSet() {

        HashSet<String> stringHashSet = new HashSet<>();
        stringHashSet.addAll(slist);
    }

    public static void wordsToHashSet() {

        HashSet<Word> wordHashSet = new HashSet<>();
        wordHashSet.addAll(wlist);
    }
}
