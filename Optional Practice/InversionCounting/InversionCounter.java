import java.util.Arrays;

class InversionCounter {
    public static long mergeSort(int[] arr, int[] spaceArr, int begin, int end) {
        if (begin >= end)   return 0;

        long cnt = 0;
        int mid = begin + (end - begin) / 2;
        cnt += mergeSort(arr, spaceArr, begin, mid);
        cnt += mergeSort(arr, spaceArr,mid + 1, end);
        cnt += helper(arr, spaceArr, begin, mid, mid+1, end);
        return cnt;
    }

    public static long countSwaps(int[] arr) {
        return mergeSort(arr, new int[arr.length], 0,  arr.length - 1);
    }

    public static long helper(int[] arr, int[] spaceArr, int left1, int right1, int left2, int right2) {
        int start = left1;
        int cur = start;
        long cnt = 0;

        while (left1 <= right1 && left2 <= right2) {
            if (arr[left1] <= arr[left2]) {
                spaceArr[cur] = arr[left1];
                left1++;
            } else {
                spaceArr[cur] = arr[left2];
                cnt += (right1 - (left1 - 1));
                left2++;
            }
            cur++;
        }

        while (left2 <= right2) {
            spaceArr[cur] = arr[left2];
            left2++;
            cur++;
        }

        while (left1 <= right1) {
            spaceArr[cur] = arr[left1];
            left1++;
            cur++;
        }

        for (int i = start; i <= right2; i++) {
            arr[i] = spaceArr[i];
        }
        return cnt;
    }

    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        return helper(arr, new int[arr.length], left1, right1, left2, right2);
    }
}
