import java.util.HashSet;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        HashSet<Integer> hashV = new HashSet<Integer>();
        hashV.add(v);
        HashSet<Integer> hashW = new HashSet<Integer>();
        hashW.add(w);

        return length(hashV, hashW);

    }

    // a common ancestor of v and w that participates in a shortest ancestral path;
    // -1 if no such path
    public int ancestor(int v, int w) {

        if (length(v, w) >= 1) {
            return v;
        }

        return -1;

    }

    // length of shortest ancestral path between any vertex in v and any vertex in
    // w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        BreadthFirstDirectedPaths breadv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths breadw = new BreadthFirstDirectedPaths(digraph, w);
        int maxLen = Integer.MAX_VALUE;

        for (int i = 0; i < digraph.V(); i++) {

            if (breadv.hasPathTo(i) && breadw.hasPathTo(i)) { // see if there is a path of ancestor i connected and
                                                              // loops for number of V

                int tempMax = breadv.distTo(i) + breadw.distTo(i);

                maxLen = Math.min(tempMax, maxLen);
            }

        }

        if (maxLen != Integer.MAX_VALUE) {
            return maxLen;
        }
        return -1;

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such
    // path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {

        BreadthFirstDirectedPaths breadv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths breadw = new BreadthFirstDirectedPaths(digraph, w);
        int minLen = Integer.MAX_VALUE;
        int ancestor = -1;

        for (int i = 0; i < digraph.V(); i++) {

            if (breadv.hasPathTo(i) && breadw.hasPathTo(i)) { // see if there is a path of ancestor i connected and
                                                              // loops for number of V

                int tempMin = breadv.distTo(i) + breadw.distTo(i);

                if (tempMin < minLen) {
                    minLen = tempMin;
                    ancestor = i;
                }

            }

        }

        return ancestor;

    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("wordnet\\digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
