public class LoadBalancing {
    public static boolean isFeasibleLoad(int[] jobSizes, int queryLoad, int p) {
        int cnt = 1;
        int jobs = jobSizes.length;
        int all = jobSizes[0];
        if (jobSizes[0] > queryLoad) return false;
        for (int i = 1; i < jobs; i++) {
            if (jobSizes[i] > queryLoad) return false;
            all += jobSizes[i];
            if (all < queryLoad) continue;
            if (all > queryLoad) {
                cnt++;
                all = jobSizes[i];
            }
            if (all == queryLoad) {
                if (i != jobs - 1) cnt++;
                all = 0;
            }
        }
        return cnt <= p;
    }
    
    public static int findLoad(int[] jobSizes, int p) {
        int size = jobSizes.length;
        int begin = jobSizes[0];
        for (int i = 1; i < size; i ++) {
            if (begin < jobSizes[i]) begin = jobSizes[i];f
        }
        int end = begin * size;
        while (end != begin) {
            int mid = (end + begin) / 2;
            if (isFeasibleLoad(jobSizes,mid,p)) {
                end = mid;
            }
            else begin = mid + 1;
        }
        return end;
    }
}
