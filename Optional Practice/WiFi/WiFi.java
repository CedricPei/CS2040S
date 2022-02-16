import java.util.Arrays;

public class WiFi {
    public static boolean coverable(int[]  houses,int numOfAccessPoints,double distance) {
        int houseNum = houses.length;
        Arrays.sort(houses);
        
        int need = 1;
        double mostFar = houses[0] + distance;
        for (int i = 1; i < houseNum; i ++) {
            if (houses[i] - mostFar <= distance) continue;
            need ++;
            mostFar = houses[i] + distance;
        }
        return need <= numOfAccessPoints;

        /*
        if (numOfAccessPoints >= houseNum) {
            return true;
        }
        else {
            double cover = 2 * distance;
            Arrays.sort(houses);

            if (numOfAccessPoints * cover >= houses[houseNum - 1] - houses[0]) {
                return true;
            } else {
                int need = 1;
                for (int i = 0; i < houseNum - 1;i ++) {
                    for(int p = i + 1; p < houseNum; p ++) {
                        if (houses[p] - houses[i] < cover) {
                            continue;
                        }
                        if (houses[p] - houses[i] >  cover) {
                            need ++;
                            i = p - 1;
                            break;
                        }
                        if (houses[p] - houses[i] == cover) {
                            if (p != houseNum - 1) {
                                need ++;
                                i = p;
                            }
                            break;
                        }
                    }
                }
                if (need <= numOfAccessPoints){
                    return true;
                }
                else {
                    return false;
                }
            }
        }
         */
    }

    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        int houseNum = houses.length;
        Arrays.sort(houses);

        
        double begin = 0;
        double min = houses[houseNum - 1];
        double end = houses[houseNum - 1];
        while (end - begin > 0.5) {
            double mid = (end + begin) / 2;
            if (coverable(houses, numOfAccessPoints, mid)) {
                end = mid;
                min = mid;
            }
            else begin = mid;
        }
        System.out.println(min);
        return min;
        
        /*
        if (numOfAccessPoints >= houseNum) {
            return 0;
        } else {
            double minDistance = houses[houseNum - 1] - houses[0];
            for (int i = 0; i < houseNum - 1; i++) {
                for (int p = i + 1; p < houseNum; p++) {
                    double distance = ((double) houses[p] - (double) houses[i]) / 2;
                    if ( (WiFi.coverable(houses, numOfAccessPoints, distance) == true) && distance < minDistance) {
                        minDistance = distance;
                        break;
                    }
                }
            }
            return minDistance;
        }
        */
    }
}
