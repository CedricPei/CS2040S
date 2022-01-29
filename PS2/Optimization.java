/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {
    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) {
        int size = dataArray.length;

        if (size == 1) return dataArray[0];
        else {
            if ((dataArray[0] > dataArray[1] ) && (dataArray[size - 1] > dataArray[size - 2]) )
                return Math.max(dataArray[0],dataArray[size - 1]);
            else return findPeakUtil(dataArray, 0, size - 1, size);
        }
    }

    static int findPeakUtil(int[] arr, int low, int high, int n) {
		int mid = low + (high - low) / 2;
		if ((mid == 0 || arr[mid - 1] <= arr[mid]) && (mid == n - 1 || arr[mid + 1] <= arr[mid]))
			return arr[mid];
		else if (mid > 0 && arr[mid - 1] > arr[mid]) return findPeakUtil(arr, low, (mid - 1), n);
		else return findPeakUtil(arr, (mid + 1), high, n);
	}
}
