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
        return new long[]{0, 0};
    }
}
