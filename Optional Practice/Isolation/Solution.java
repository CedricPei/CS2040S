import java.util.HashMap;

public class Solution {
    public static int solve(int[] arr) {
        int max = -1;
        int start = 0;
        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (hm.get(arr[i]) == null) {
                hm.put(arr[i], i);
            } else {
                max = Math.max(max, i - start);
                start = start > hm.get(arr[i]) ? start : hm.get(arr[i]) + 1;
                hm.replace(arr[i], i);
            }
        }
        max = Math.max(max, arr.length - start);
        return max;
    }
}