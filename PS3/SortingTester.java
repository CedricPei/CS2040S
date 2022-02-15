public class SortingTester {

    public static boolean checkSort(ISort sorter, int size) {
        boolean result = true;
        //Generate KeyValuePair array of random key.
        KeyValuePair[] array = new KeyValuePair[size];
        int val = 0;
        for (int i = 0; i < size; i++) {
            int key = (int)(Math.random() * 30000);
            array[i] = new KeyValuePair(key, val);
            val++;
        }

        //Create a stopwatch
        StopWatch watch = new StopWatch();
        StopWatch watch1 = new StopWatch();
        StopWatch watch2 = new StopWatch();

        //Sort the array.
        watch.start();
        sorter.sort(array);
        watch.stop();

        //Best case.
        watch1.start();
        sorter.sort(array);
        watch1.stop();

        //Get reverseArray.
        KeyValuePair[] reverseArray = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            reverseArray[i] = array[size -1 - i];
        }

        //Worst case.
        watch2.start();
        sorter.sort(reverseArray);
        watch2.stop();

        //Check the sorting result.
        for (int i = 0; i < size - 1; i++) {
            if (array[i].getKey() > array[i + 1].getKey()) {
                result = false;
                break;
            }
        }

        System.out.println("Random Time: " + watch.getTime());
        System.out.println("Best-Case Time: " + watch1.getTime());
        System.out.println("Worst-Case Time: " + watch2.getTime());
        System.out.println("Correctness of this algorithm is: " + result);
        return result;
    }

    public static boolean isStable(ISort sorter, int size) {
        boolean result = true;
        //Generate KeyValuePair array of random key.
        KeyValuePair[] array = new KeyValuePair[size];
        int val = 0;
        for (int i = 0; i < size; i++) {
            int key = (int)(Math.random() * 30000);
            array[i] = new KeyValuePair(key, val);
            val++;
        }

        //Sort the array.
        sorter.sort(array);

        //Check the sorting result.
        for (int i = 0; i < size - 1; i++) {
            if (array[i].getKey() == array[i + 1].getKey()) {
                if (array[i].getValue() > array[i + 1].getValue()) {
                    result = false;
                }
            }
        }

        System.out.println("Stability of this algorithm is: " + result);
        return result;
    }

    public static void main(String[] args) {
        int size = 1000;

        System.out.println("Sorter A");
        ISort sorterA = new SorterA();
        SortingTester.checkSort(sorterA, size);
        SortingTester.isStable(sorterA, size);
/*
        System.out.println("Sorter B");
        ISort sorterB = new SorterB();
        SortingTester.checkSort(sorterB, size);
        SortingTester.isStable(sorterB, size);
*/
        System.out.println("Sorter C");
        ISort sorterC = new SorterC();
        SortingTester.checkSort(sorterC, size);
        SortingTester.isStable(sorterC, size);

        /*
        System.out.println("Sorter D");
        ISort sorterD = new SorterD();
        SortingTester.checkSort(sorterD, size);
        SortingTester.isStable(sorterD, size);
         */

        /*
        System.out.println("Sorter E");
        ISort sorterE = new SorterE();
        SortingTester.checkSort(sorterE, size);
        SortingTester.isStable(sorterE, size);
         */

        System.out.println("Sorter F");
        ISort sorterF = new SorterF();
        SortingTester.checkSort(sorterF, size);
        SortingTester.isStable(sorterF, size);
    }
}
