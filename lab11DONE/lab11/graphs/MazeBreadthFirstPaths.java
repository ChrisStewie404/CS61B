package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    private boolean targetfound=false;
    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze=m;
        s=m.xyTo1D(sourceX,sourceY);
        t=m.xyTo1D(targetX,targetY);
        distTo[s]=0;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> queue = new ArrayDeque<Integer>();
        marked[s] = true;
        queue.add(s);
        while (!queue.isEmpty()) {
            int first = queue.peek();
            marked[first] = true;
            if (first == t) {
                announce();
                break;
            }
            for (int q : maze.adj(first)) {
                if (!marked[q]) {
                    edgeTo[q] = first;
                    announce();
                    distTo[q] = distTo[first] + 1;
                    if (q == t) {
                        return;
                    }
                }
                queue.add(q);
            }
        queue.poll();
        }
    }

    @Override
    public void solve() {
        bfs();
    }
}

