import org.junit.Test;

import static org.junit.Assert.*;

public class InversionCounterTest {

    public static long bruteForce(int[] arr) {
        long cnt = 0;
        for(int i = 0; i < arr.length; ++i) {
            for(int j = i + 1; j < arr.length; ++j) {
                if(arr[i] > arr[j]) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public static void printArray(int[] arr) {
        for(int e: arr) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

    @Test
    public void countSwapsTest1() {
        int[] arr = {3, 1, 2};
        assertEquals(2L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest2() {
        int[] arr = {2, 3, 4, 1};
        assertEquals(3L, InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest3() {
        int[] arr = {2, 5, 3, 7, 4, 1, 9};
        long res1 = bruteForce(arr);
        long res2 = InversionCounter.countSwaps(arr);
        printArray(arr);
        assertEquals(res1, res2);
    }

    @Test
    public void countSwapsTest4() {
        int[] arr = {2, 10, 5, 3, 6, 89, 4, 32, 100, 31, 52, 98, 12, 7};
        long res1 = bruteForce(arr);
        long res2 = InversionCounter.countSwaps(arr);
        printArray(arr);
        assertEquals(res1, res2);
    }

    @Test
    public void countSwapsTest5() {
        int[] arr = {2, 89, 10, 4, 11, 121, 56, 34, 5, 3, 6, 89, 77, 4, 32, 100, 65, 31, 43, 52, 98, 12, 7};
        long res1 = bruteForce(arr);
        long res2 = InversionCounter.countSwaps(arr);
        printArray(arr);
        assertEquals(res1, res2);
    }

    @Test
    public void countSwapsTest6() {
        int[] arr = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        assertEquals(bruteForce(arr), InversionCounter.countSwaps(arr));
    }

    @Test
    public void countSwapsTest7() {
        int[] arr = {2, 89, 10, 4, 11, 121, 56, 34, 5, 3, 6, 89, 77, 4, 32, 100, 65, 31, 43, 52, 98, 12, 7, 134, 1,
                     312, 102, 23, 41, 88, 99, 256, 17, 178, 78, 57, 9, 15};
        long res1 = bruteForce(arr);
        long res2 = InversionCounter.countSwaps(arr);
        printArray(arr);
        assertEquals(res1, res2);
    }

    @Test
    public void mergeAndCountTest1() {
        int[] arr = {3, 1, 2};
        assertEquals(2L, InversionCounter.mergeAndCount(arr, 0, 0, 1, 2));
    }

    @Test
    public void mergeAndCountTest2() {
        int[] arr = {2, 3, 4, 1};
        assertEquals(3L, InversionCounter.mergeAndCount(arr, 0, 2, 3, 3));
    }

}