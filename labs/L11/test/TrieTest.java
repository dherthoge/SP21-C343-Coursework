import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    public void insert() {
        Trie trie = new Trie();
        trie.insert("aaron");
        trie.insert("aarons");
        trie.insert("aareqon");
        trie.insert("beta");
        trie.insert("betas");
        trie.insert("betag");
        trie.insert("beta");
        trie.insert("gamma");
        trie.insert("gaasdmma");
        trie.insert("fs");
        trie.insert("betvcasdfa");

        assertTrue(trie.contains("aaron"));
        assertTrue(trie.contains("aarons"));
        assertFalse(trie.contains("aaronsg"));
        assertTrue(trie.contains("aareqon"));
        assertTrue(trie.contains("beta"));
        assertTrue(trie.contains("betas"));
        assertTrue(trie.contains("betag"));
        assertTrue(trie.contains("beta"));
        assertTrue(trie.contains("gamma"));
        assertTrue(trie.contains("gaasdmma"));
        assertTrue(trie.contains("fs"));
        assertTrue(trie.contains("betvcasdfa"));
    }

    @Test
    public void contains() throws IOException {
        Trie trie = new Trie();
        Scanner scanner = new Scanner(Paths.get("commonwords.txt"));
        while (scanner.hasNext()) {
            trie.insert(scanner.next());
        }

        assertFalse(trie.contains("aarons"));
        assertTrue(trie.contains("aaron"));
        assertFalse(trie.contains("aaro"));
        assertTrue(trie.contains("absent"));
    }

    @Test
    public void possiblePrefix() throws IOException {
        Trie trie = new Trie();
        Scanner scanner = new Scanner(Paths.get("commonwords.txt"));
        while (scanner.hasNext()) {
            trie.insert(scanner.next());
        }

        assertFalse(trie.possiblePrefix("aarons"));
        assertTrue(trie.possiblePrefix("aaron"));
        assertTrue(trie.possiblePrefix("aaro"));
        assertTrue(trie.possiblePrefix("absent"));
    }

    @Test
    public void trie () {
        Trie t = new Trie();
        t.insert("on");
        t.insert("one");
        t.insert("of");
        t.insert("off");
        t.insert("oft");
        t.insert("or");

        System.out.println(t.getChildren());
    }

    @Test
    public void dict () throws IOException {
        Trie trie = new Trie();
        Scanner scanner = new Scanner(Paths.get("commonwords.txt"));
        while (scanner.hasNext()) {
            trie.insert(scanner.next());
        }

        assertTrue(trie.contains("abandon"));
        assertTrue(trie.contains("abandoned"));
        assertTrue(trie.contains("abandonment"));
        assertFalse(trie.contains("abandonmenth"));
        assertFalse(trie.contains("abando"));
        assertFalse(trie.contains("aband"));
        assertFalse(trie.contains("aban"));
        assertFalse(trie.contains("aba"));
        assertFalse(trie.contains("ab"));
        assertFalse(trie.contains("a"));
        assertTrue(trie.possiblePrefix("abando"));
        assertTrue(trie.possiblePrefix("aband"));
        assertTrue(trie.possiblePrefix("aban"));
        assertTrue(trie.possiblePrefix("aba"));
        assertTrue(trie.possiblePrefix("ab"));
        assertTrue(trie.possiblePrefix("a"));
    }
}
