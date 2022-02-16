import  java.util.stream.*;

public class Guessing {
    private int begin = 0;
    private int end = 999;
    private int middle = 500;
    private final int[] nm = IntStream.range(1,1001).toArray();

    public int guess() {
        middle = begin + (end - begin) / 2;
        return nm[middle];
    }

    public void update (int answer) {
        if (answer == 1) {
            end = middle;
        }
        else if (answer == -1) {
            begin  = middle + 1;
        }
        else {
            throw new IllegalArgumentException("Illegal Number");
        }
    }
}

