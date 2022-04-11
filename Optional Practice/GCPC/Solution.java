import java.util.TreeSet;

public class Solution {
    public static class Team implements Comparable<Team>{
        int id;
        int score;
        int penalty;

        public Team(int id, int score, int penalty) {
            this.id = id;
            this.score = score;
            this.penalty = penalty;
        }

        @Override
        public int compareTo(Team t) {
            if (score != t.score) {
                return t.score - score;
            } else {
                if (penalty != t.penalty)   return penalty - t.penalty;
                else                        return id - t.id;
            }
        }
    }

    TreeSet<Team> set = new TreeSet<>();
    Team[] teams;

    public Solution(int numTeams) {
        teams = new Team[numTeams + 1];
        for (int i = 1; i <= numTeams; i++) {
            teams[i] = new Team(i, 0, 0);
        }
    }

    public int update(int team, long newPenalty){
        set.remove(teams[team]);
        teams[team].score++;
        teams[team].penalty += newPenalty;
        if (team == 1) {
            while (!set.isEmpty()) {
                Team t = set.last();
                if (teams[1].compareTo(t) < 0)    set.remove(t);
                else                              break;
            }
        } else {
            if (teams[1].compareTo(teams[team]) > 0)    set.add(teams[team]);
        }
        return set.size() + 1;
    }
}
