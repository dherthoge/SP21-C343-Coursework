import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

class WordsTest {

    // write your own tests timing how long it takes to build a hashset with a Listof String and then a Listof Word
    // refer to Lab 3 tests if you do not remember how to time tests using methods of the Instant & Duration class
    @Test
    void timeTests () {
        // TODO


        Duration d1 = timeStrings();
        System.out.printf("Running Strings->HashSet; time in ms = %d%n",
                d1.toMillis());


        Duration d2 = timeWords();
        System.out.printf("Running Words->HashSet; time in ms = %d%n",
                d2.toMillis());
    }

    Duration timeStrings() {

        Instant start = Instant.now();
        Words.stringsToHashSet();
        Instant end = Instant.now();


        return Duration.between(start, end);
    }

    Duration timeWords() {

        Instant start = Instant.now();
        Words.wordsToHashSet();
        Instant end = Instant.now();
        return Duration.between(start, end);
    }

}
