package lab11.graphs;

import java.util.Comparator;
import java.util.PriorityQueue;
import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<Node> pq;
    private class Node implements Comparable<Node>{
       private int v;
        private int dist;
        private int hdist;
        Node prev;
        private Node(int v,Node prev){
            this.v=v;
            this.dist=prev.dist+1;
            this.hdist=h(v);
        }
        @Override
        public int compareTo(Node o) {
            return this.dist+this.hdist-o.dist-o.hdist;
        }
    }

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        pq=new MinPQ<>();
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int x=maze.toX(v);
        int y=maze.toY(v);
        int tx=maze.toX(t);
        int ty=maze.toY(t);
        return Math.abs(x-tx)+Math.abs(y-ty);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        Node start=new Node(s,null);
        marked[s]=true;
        announce();
        pq.insert(start);
        while(!pq.isEmpty()){
            Node fringe=pq.delMin();
            if(fringe.v==t){
                while(fringe.prev!=null){
                    edgeTo[fringe.v]=fringe.prev.v;
                    announce();
                    fringe=fringe.prev;
                }
                return;
            }
            int v=fringe.v;
            for(int p:maze.adj(v)){
                if(marked[p]) continue;
                marked[p]=true;
                announce();
                Node neighbor=new Node(p,fringe);
                pq.insert(neighbor);
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

