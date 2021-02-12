import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public int hashCode () { return 7; }
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
}
