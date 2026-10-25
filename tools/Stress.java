public class Stress {
 public static void main(String[] args) {
  int n=1000000;
  int[] a=new int[n-1], b=new int[n-1], cost=new int[n-1];
  for(int i=0;i<n-1;i++){a[i]=i;b[i]=i+1;cost[i]=1;}
  Input.Walls w=new Input.Walls(1,n,a,b,cost);
  int[] broken=Maze.breakWalls(w);
  Escape.Result r=Escape.escape(w,broken);
  if(r.path().length!=n || r.visited()!=n || r.deadEnds()!=2) throw new AssertionError("DFS");
  Input.Slides s=new Input.Slides(n,a,b,cost);
  int[] z=Zones.kosaraju(s);
  for(int i=0;i<n;i++) if(z[i]!=i) throw new AssertionError("SCC");
  long[] route=Zones.routes(s,z,n);
  if(route[0]!=n || route[1]!=n-1) throw new AssertionError("routes");
  Input.Walls one=new Input.Walls(1,1,new int[0],new int[0],new int[0]);
  if(Escape.escape(one,new int[0]).visited()!=1 || Maze.primCost(one)!=0) throw new AssertionError("single");
  System.out.println("Million-cell DFS, SCC and DAG route; single-cell checks passed.");
 }
}
