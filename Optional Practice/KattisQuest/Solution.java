import java.util.TreeSet;

public class Solution {
    public static class Quest implements Comparable<Quest> {
        public long energy;
        public long reward;
    
        public Quest(long energy, long reward) {
            this.energy = energy;
            this.reward = reward;
        }
    
        @Override
        public int compareTo(Quest q) {
            if (energy != q.energy)    return (int) (energy - q.energy);
            else                       return (int) (reward - q.reward);
        }
    }

    TreeSet<Quest> q = new TreeSet<Quest>();

    void add(long energy, long value) {
        q.add(new Quest(energy, value));
    }

    long query(long remainingEnergy) {
        long ans = 0;
        while (!q.isEmpty() && remainingEnergy > 0) {
            Quest s = q.floor(new Quest(remainingEnergy, Long.MAX_VALUE));
            if (s != null) {
                ans += s.reward;
                remainingEnergy -= s.energy;
                q.remove(s);
            } else {
                break;
            }
        }
        return ans;
    }
}
