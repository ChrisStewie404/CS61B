package lab11.graphs;

import java.util.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean cycleFound=false;
    private Stack<Integer> stack;
    private Maze maze;
    private int cnt;
    public MazeCycles(Maze m) {
        super(m);
        maze=m;
        stack=new Stack<Integer>();
    }

    @Override
    public void solve() {
        int s=7;
        marked[s]=true;
        stack.add(s);
        distTo[s]=cnt;
        for(int p:maze.adj(s)){
            dfsc(s,p);
            if(cycleFound) return;
        }

    }
    // Helper methods go here

    public void dfsc(int p,int q){
        marked[q]=true;
        distTo[q]=++cnt;
        announce();
        stack.add(q);
        for(int t:maze.adj(q)){
            if(t==p) continue;
            else if(!marked[t]){
                dfsc(q,t);
                if(cycleFound) return;
            }
            else if(marked[t]){
                cycleFound=true;
                edgeTo[t]=q;
                announce();
                while(!stack.isEmpty()&&stack.peek()!=t){
                    int w=stack.pop();
                    edgeTo[w]=stack.peek();
                    announce();
                }
                return;
            }
        }
        if(!cycleFound){
            stack.pop();
        }
    }
    public void dfscycle(int p,int q){
        marked[q]=true;
        announce();
        for(int t:maze.adj(q)){
            if(t==p) continue;
            else if(!marked[t]){
                dfscycle(q,t);
                if(cycleFound){
                    edgeTo[t]=q;
                    announce();
                    return;
                }
            }
            else{
                cycleFound=true;
                edgeTo[t]=q;
                announce();
                return;
            }
        }
    }
    public void dfs(int p,int q){
        edgeTo[q]=p;
        marked[q]=true;
        announce();
        for(int t:maze.adj(q)){
            if(p==t) continue;
            else if(!marked[t]){
                dfs(q,t);
            }
            else{
                edgeTo[t]=q;
                cycleFound=true;
                return;
            }
        }
    }
}

