/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
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

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
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
