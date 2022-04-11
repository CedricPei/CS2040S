import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TSPGraph implements IApproximateTSP {

    @Override
    public void MST(TSPMap map) {
        int num = map.getCount();
        double[][] matrix = new double[num][num];
        TreeMapPriorityQueue<Double, Integer> pQ = new TreeMapPriorityQueue<>();
        HashSet<Integer> vis = new HashSet<>();
        HashMap<Integer, Integer> parent = new HashMap<>();

        for (int i = 0; i < num; i++) {
            pQ.add(i, Double.MAX_VALUE);
            for (int j = 0; j < num; j++) {
                matrix[i][j] = map.pointDistance(i, j);
            }
        }
        pQ.decreasePriority(0, 0.0);
        vis.add(0);
        parent.put(0, null);

        while (!pQ.isEmpty()) {
            int cur = pQ.extractMin();
            vis.add(cur);

            for (int adj = 0; adj < num; adj++) {
                double dis = matrix[cur][adj];
                if (dis == 0.0)         continue;
                if (!vis.contains(adj)) {
                    pQ.decreasePriority(adj, dis);
                    if (!parent.containsKey(adj) || dis < matrix[adj][parent.get(adj)]) {
                        parent.put(adj, cur);
                    }
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : parent.entrySet()) {
            if (entry.getValue() != null) map.setLink(entry.getKey(), entry.getValue(), false);
        }
        map.redraw();
    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);
        int num = map.getCount();
        int[] parent = new int[num];
        boolean[] vis = new boolean[num];
        HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();

        for (int i = -1 ; i < num; i++) {
            hm.put(i, new ArrayList<>());
        }

        for (int i = 0 ; i < num; i++) {
            vis[i] = false;
            parent[i] = map.getLink(i);
            hm.get(map.getLink(i)).add(i);
            map.eraseLink(i, false);
        }

        int cur = 0;
        int tem = -1;
        while (!(cur == 0 && hm.get(0).isEmpty())) {
            if (hm.get(cur).isEmpty()) {
                if (!vis[cur]) {
                    vis[cur] = true;
                    tem = cur;
                }
                cur = parent[cur];
            } else {
                Integer nex = hm.get(cur).remove(0);
                if (!vis[cur]) {
                    vis[cur] = true;
                    map.setLink(cur, nex, false);
                } else {
                    map.setLink(tem, nex, false);
                }
                cur = nex;
            }
        }
        map.setLink(tem, 0, false);
        map.redraw();
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        int num = map.getCount();
        int[] vis = new int[num];

        for (int i = 0; i < num; i++) {
            vis[i] = -1;
        }

        for (int i = 0; i < num; i++) {
            int parent = map.getLink(i);
            if (parent < 0 || vis[parent] != -1)    return false;
            else                                    vis[parent] = parent;
        }

        int itr = 0;
        int cur = 0;
        int parent = -1;
        while (0 != parent) {
            parent = map.getLink(cur);
            cur = parent;
            itr++;
        }

        return itr == num;
    }

    @Override
    public double tourDistance(TSPMap map) {
        if (!isValidTour(map))   return -1;

        int num = map.getCount();
        double sum = 0.0;
        for (int i = 0; i < num; i++) {
            sum += map.pointDistance(i, map.getLink(i));
        }
        return sum;
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "../hundredpoints.txt");
        TSPGraph graph = new TSPGraph();

        graph.MST(map);
    }
}
