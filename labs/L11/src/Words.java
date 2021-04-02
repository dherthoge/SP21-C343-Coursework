/**
 * The common interface for the two implementations of the
 * dictionary of words.
 */

public interface Words {
    boolean contains (String w);
    boolean possiblePrefix (String w);
}
