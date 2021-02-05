import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    void stack () throws EmptyStackE {
        Stack<Integer> s = new EmptyS<>();
        Stack<Integer> s1 = s.push(1).push(2).push(3).push(4);
        Stack<Integer> s2 = s1.push(5).push(6);
        Stack<Integer> s3 = s1.push(7);
        assertEquals(4,s1.getTop());
        Stack<Integer> s4 = s1.pop();

        System.out.printf("s1 = %s%n", s1);
        System.out.printf("s4 = %s%n", s4);
        System.out.printf("s2 = %s%n", s2);
        System.out.printf("s3 = %s%n", s3);
        System.out.printf("s1 = %s%n", s1);

        assertEquals(1, s2.getLast());

        assertEquals(100, s4.addLast(100).getLast());
    }

}