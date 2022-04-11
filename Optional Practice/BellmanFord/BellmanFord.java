import java.util.ArrayList;

public class BellmanFord {
    public static int INF = 20000000;
    public static int NEGINF = -20000000;
    public ArrayList<ArrayList<IntPair>> adj;
    public int[] dis;

    public BellmanFord(ArrayList<ArrayList<IntPair>> adjList) {
        this.adj = adjList;
        this.dis = new int[adjList.size()];
    }

    public void computeShortestPaths(int source) {
        dis[source] = 0;
        for (int i = 0; i < dis.length; i++) {
            if (i != source)    dis[i] = BellmanFord.INF;
        }

        for (int j = 1; j <= adj.size() - 1; j++) {
            for (int k = 0; k < adj.size(); k++) {
                for (IntPair pair : adj.get(k)) {
                    if (dis[k] != BellmanFord.INF) {
                        if (dis[pair.first] > pair.second + dis[k] && dis[pair.first] != BellmanFord.NEGINF) {
                            dis[pair.first] = pair.second + dis[k];
                        }
                    }
                }
            }
        }

        for (int k = 0; k < adj.size(); k++) {
            for (IntPair pair : adj.get(k)) {
                if (dis[pair.first] > pair.second + dis[k] && dis[pair.first] != BellmanFord.NEGINF) {
                    for (int j = 1; j <= adj.size(); j++) {
                        for (int n = 0; n < adj.size(); n++) {
                            for (IntPair par : adj.get(n)) {
                                if (dis[n] != BellmanFord.INF && dis[par.first] > par.second + dis[n] && dis[par.first] != BellmanFord.NEGINF) {
                                    dis[par.first] = NEGINF;
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }
    }

    public int getDistance(int node) {
        if (node >= 0 && node < adj.size())   return dis[node];
        else                                  return INF;
    }
}

