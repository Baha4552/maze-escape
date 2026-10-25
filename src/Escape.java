import java.util.Arrays;

public class Escape {
    public record Result(int[] path, int visited, int deadEnds) {}
    public static Result escape(Input.Walls w, int[] broken) {
        int[][] g = Graphs.open(w, broken);
        int n = w.cells(), dead = 0;
        for (int[] neighbors : g) if (neighbors.length == 1) dead++;
        if (n == 0) return new Result(new int[0], 0, dead);
        int[] stack = new int[n], direction = new int[n];
        boolean[] seen = new boolean[n];
        int top = 0, visited = 1;
        seen[0] = true;
        while (top >= 0) {
            int u = stack[top];
            if (u == n - 1) return new Result(Arrays.copyOf(stack, top + 1), visited, dead);
            if (direction[top] == 4) { top--; continue; }
            int v = switch (direction[top]++) {
                case 0 -> u - w.cols();
                case 1 -> u % w.cols() + 1 < w.cols() ? u + 1 : -1;
                case 2 -> u + w.cols();
                default -> u % w.cols() > 0 ? u - 1 : -1;
            };
            if (v < 0 || v >= n || seen[v]) continue;
            boolean open = false;
            for (int neighbor : g[u]) if (neighbor == v) { open = true; break; }
            if (open) { seen[v] = true; visited++; stack[++top] = v; direction[top] = 0; }
        }
        return new Result(new int[0], visited, dead);
    }
}
