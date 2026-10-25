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
    public static int[] cheapestMaze(Input.Walls w) { return new int[0]; }
    public static long primCost(Input.Walls w) { return 0; }
}
