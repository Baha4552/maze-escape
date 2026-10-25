/** Union by size with iterative path compression. */
public class DisjointSet {
    private final int[] parent, size;
    private int components;
    public DisjointSet(int n) {
        parent = new int[n]; size = new int[n]; components = n;
        for (int i = 0; i < n; i++) { parent[i] = i; size[i] = 1; }
    }
    public int find(int x) {
        int root = x;
        while (parent[root] != root) root = parent[root];
        while (parent[x] != x) {
            int next = parent[x]; parent[x] = root; x = next;
        }
        return root;
    }
    public boolean union(int a, int b) {
        int x = find(a), y = find(b);
        if (x == y) return false;
        if (size[x] < size[y]) { int t = x; x = y; y = t; }
        parent[y] = x; size[x] += size[y]; components--; return true;
    }
    public boolean connected(int a, int b) { return find(a) == find(b); }
    public int count() { return components; }
}
