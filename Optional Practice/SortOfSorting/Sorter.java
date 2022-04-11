class Sorter {
    public static void sortStrings(String[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int trace = i;
            while (trace >= 1 && isGreaterThan(arr[trace - 1], arr[trace])) {
                swap(arr, trace - 1, trace);
                trace--;
            }
        }
    }

    public static void swap(String[] arr, int a, int b) {
        String temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static boolean isGreaterThan(String str1, String str2) {
        char ch11 = str1.charAt(0);
        char ch12 = str2.charAt(0);
        if (Character.compare(ch11, ch12) == 0) return Character.compare(str1.charAt(1), str2.charAt(1)) > 0;
        else                                    return Character.compare(ch11, ch12) > 0;
    }
}
