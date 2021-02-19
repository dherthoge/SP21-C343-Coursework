import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PListTest {

    @Test
    void splitAt () {

        PNode p1 = new PNode(0, new PNode(1, new PNode(2, new PEmpty())));

        assertEquals("PEmpty", PNode.splitAtFirstList(0, p1).toString());
        assertEquals("0, 1, 2, PEmpty", PNode.splitAtSecondList(0, p1).toString());
        assertEquals("0, 1, PEmpty", PNode.splitAtFirstList(2, p1).toString());
        assertEquals("2, PEmpty", PNode.splitAtSecondList(2, p1).toString());
        assertEquals("0, 1, 2, PEmpty", PNode.splitAtFirstList(4, p1).toString());
        assertEquals("PEmpty", PNode.splitAtSecondList(4, p1).toString());
    }

    @Test
    void mergeSort () {

        PNode p1 = new PNode(2, new PNode(0, new PNode(1, new PEmpty())));

        System.out.println(p1.mergeSort().toString());
    }

}
