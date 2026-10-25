import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Maze {
    public static int[] breakWalls(Input.Walls w) {
        DisjointSet d = new DisjointSet(w.cells());
        int[] result = new int[Math.max(0, w.cells() - 1)];
        int count = 0;
        for (int k = 0; k < w.count(); k++)
            if (d.union(w.a()[k], w.b()[k])) result[count++] = k;
        return Arrays.copyOf(result, count);
    }
    public static int[] cheapestMaze(Input.Walls w) {
        // Pack cost and edge index into a primitive long to avoid boxed sorting.
        long[] order = new long[w.count()];
        for (int k = 0; k < order.length; k++)
            order[k] = ((long) w.cost()[k] << 32) | (k & 0xffffffffL);
        Arrays.sort(order);
        DisjointSet d = new DisjointSet(w.cells());
        int[] result = new int[Math.max(0, w.cells() - 1)];
        int count = 0;
        for (long entry : order) {
            int k = (int) entry;
            if (d.union(w.a()[k], w.b()[k])) result[count++] = k;
            if (count == result.length) break;
        }
        return Arrays.copyOf(result, count);
    }
    public static long primCost(Input.Walls w) {
        int n = w.cells();
        if (n == 0) return 0;
        int[] degree = new int[n];
        for (int k = 0; k < w.count(); k++) { degree[w.a()[k]]++; degree[w.b()[k]]++; }
        int[][] edges = new int[n][];
        for (int u = 0; u < n; u++) edges[u] = new int[degree[u]];
        Arrays.fill(degree, 0);
        for (int k = 0; k < w.count(); k++) {
            edges[w.a()[k]][degree[w.a()[k]]++] = k;
            edges[w.b()[k]][degree[w.b()[k]]++] = k;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(k -> w.cost()[k]));
        boolean[] used = new boolean[n];
        int u = 0, reached = 0;
        long total = 0;
        while (true) {
            used[u] = true; reached++;
            for (int k : edges[u]) {
                int v = w.a()[k] == u ? w.b()[k] : w.a()[k];
                if (!used[v]) queue.add(k);
            }
            if (reached == n) return total;
            int next = -1;
            while (!queue.isEmpty()) {
                int k = queue.remove();
                if (used[w.a()[k]] && used[w.b()[k]]) continue;
                next = used[w.a()[k]] ? w.b()[k] : w.a()[k];
                total += w.cost()[k]; break;
            }
            if (next < 0) throw new IllegalArgumentException("Disconnected wall graph");
            u = next;
        }
    }
}
