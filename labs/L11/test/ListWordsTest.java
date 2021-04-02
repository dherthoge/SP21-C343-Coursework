import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListWordsTest {

    @Test
    public void possiblePrefix() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("commonwords.txt"));
        ListWords words = new ListWords(list);

        assertFalse(words.possiblePrefix("abuts"));
        assertTrue(words.possiblePrefix("abut"));
        assertTrue(words.possiblePrefix("z"));
        assertFalse(words.possiblePrefix("zz"));
    }

    @Test
    public void trie () {
        ListWords t = new ListWords(Arrays.asList(
                "to", "tea", "ted", "ten", "in", "inn", "A"));

        assertTrue(t.contains("ten"));
        assertTrue(t.contains("in"));
        assertTrue(t.contains("inn"));
        assertFalse(t.contains("tenn"));
        assertFalse(t.contains("te"));
    }

    @Test
    public void dict () throws IOException {
        List<String> list = Files.readAllLines(Paths.get("commonwords.txt"));
        ListWords words = new ListWords(list);

        assertTrue(words.contains("abandon"));
        assertTrue(words.contains("abandoned"));
        assertTrue(words.contains("abandonment"));
        assertFalse(words.contains("abandonmenth"));
        assertFalse(words.contains("abando"));
        assertFalse(words.contains("aband"));
        assertFalse(words.contains("aban"));
        assertFalse(words.contains("aba"));
        assertFalse(words.contains("ab"));
        assertFalse(words.contains("a"));
        assertTrue(words.possiblePrefix("abando"));
        assertFalse(words.possiblePrefix("G"));
        assertTrue(words.possiblePrefix("aband"));
        assertTrue(words.possiblePrefix("aban"));
        assertTrue(words.possiblePrefix("aba"));
        assertTrue(words.possiblePrefix("ab"));
        assertTrue(words.possiblePrefix("a"));
    }
}
