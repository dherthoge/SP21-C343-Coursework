import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class SortTest {

    @Test
    void sort () {
        // TODO more correctness tests
        List<Integer> ns = Arrays.asList(70,100,203,784,412,135,101);
        List<Integer> ns1 = Arrays.asList(9,8,7,6,5,4,3,2,1);
        List<Integer> ns2 = Arrays.asList();
        List<Integer> ns3 = Arrays.asList(1,2,3,4,5,6,7,8,9);

        System.out.printf("Original list ns = %s%n", ns);
        System.out.printf("insertionSort ns = %s%n%n", Sort.insertionSort(ns));

        System.out.printf("Original list ns1 = %s%n", ns1);
        System.out.printf("insertionSort ns1 = %s%n%n", Sort.insertionSort(ns1));

        System.out.printf("Original list ns2 = %s%n", ns2);
        System.out.printf("insertionSort ns2 = %s%n%n", Sort.insertionSort(ns2));

        System.out.printf("Original list ns3 = %s%n", ns3);
        System.out.printf("insertionSort ns3 = %s%n%n", Sort.insertionSort(ns3));
    }

    @Test
    void timeSort () {
        // TODO efficiency tests
        // In particular, include a test that demonstrates why insertion sort's worst-case
        // performance is O(n^2)

        Random random = new Random();
        List<Integer> ns = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            ns.add(random.nextInt());
        }

        Instant start = Instant.now();
        Sort.insertionSort(ns);
        Instant end = Instant.now();
        Duration time = Duration.between(start, end);
        System.out.println("Random order of size 10,000 = " + time.toMillis() + "\n\n");



        List<Integer> ns1 = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            ns1.add(i);
        }

        Instant start1 = Instant.now();
        Sort.insertionSort(ns1);
        Instant end1 = Instant.now();
        Duration time1 = Duration.between(start1, end1);
        System.out.println("Already sorted order of size 10,000 = " + time1.toMillis() + "\n\n");



        List<Integer> ns2 = new ArrayList<>();
        for (int i = 9999; i >= 0; i--) {
            ns2.add(i);
        }

        Instant start2 = Instant.now();
        Sort.insertionSort(ns2);
        Instant end2 = Instant.now();
        Duration time2 = Duration.between(start2, end2);
        System.out.println("Reversed sorted order of size 10,000 = " + time2.toMillis() + "\n\n");
    }

}
