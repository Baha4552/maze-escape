import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Zones {
    public static int[] kosaraju(Input.Slides s) {
        int n = s.n();
        int[][] out = Graphs.slidesFrom(s), in = Graphs.slidesInto(s);
        boolean[] seen = new boolean[n];
        int[] stack = new int[n], cursor = new int[n], finish = new int[n];
        int finished = 0;
        for (int root = 0; root < n; root++) {
            if (seen[root]) continue;
            int top = 0; stack[0] = root; seen[root] = true;
            while (top >= 0) {
                int u = stack[top];
                if (cursor[u] == out[u].length) { finish[finished++] = u; top--; }
                else {
                    int v = s.to()[out[u][cursor[u]++]];
                    if (!seen[v]) { seen[v] = true; stack[++top] = v; }
                }
            }
        }
        int[] zone = new int[n]; Arrays.fill(zone, -1);
        int count = 0;
        for (int i = n - 1; i >= 0; i--) {
            int root = finish[i];
            if (zone[root] != -1) continue;
            int top = 0; stack[0] = root; zone[root] = count;
            while (top >= 0) {
                int u = stack[top--];
                for (int k : in[u]) {
                    int v = s.from()[k];
                    if (zone[v] == -1) { zone[v] = count; stack[++top] = v; }
                }
            }
            count++;
        }
        return zone;
    }
    public static long[] routes(Input.Slides s, int[] zoneOf, int zoneCount) {
        // Keep only the cheapest parallel slide between each pair of zones.
        java.util.List<Map<Integer, Integer>> dag = new java.util.ArrayList<>();
        for (int z = 0; z < zoneCount; z++) dag.add(new HashMap<>());
        for (int k = 0; k < s.count(); k++) {
            int a = zoneOf[s.from()[k]], b = zoneOf[s.to()[k]];
            if (a != b) dag.get(a).merge(b, s.time()[k], Math::min);
        }
        long[] distance = new long[zoneCount]; Arrays.fill(distance, Long.MAX_VALUE);
        int[] stages = new int[zoneCount]; Arrays.fill(stages, 1);
        distance[zoneOf[0]] = 0;
        long longest = 0;
        for (int a = 0; a < zoneCount; a++) {
            longest = Math.max(longest, stages[a]);
            for (Map.Entry<Integer, Integer> edge : dag.get(a).entrySet()) {
                int b = edge.getKey();
                stages[b] = Math.max(stages[b], stages[a] + 1);
                if (distance[a] != Long.MAX_VALUE)
                    distance[b] = Math.min(distance[b], distance[a] + edge.getValue());
            }
        }
        return new long[]{longest, distance[zoneOf[s.n() - 1]]};
    }
}
