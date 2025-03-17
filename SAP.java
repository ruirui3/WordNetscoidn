import java.util.HashSet;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Rui Zhao attests that this code is their original work and was written in compliance with the class Academic Integrity and Collaboration Policy found in the syllabus. 
 */

public class SAP {

    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("Diagraph cannot be null!");
        }
        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        if (v < 0 || v >= digraph.V() || w < 0 || w >= digraph.V()) {
            throw new IllegalArgumentException();
        }

        if (v == w) {
            return 0;
        }

        HashSet<Integer> hashV = new HashSet<Integer>();
        hashV.add(v);
        HashSet<Integer> hashW = new HashSet<Integer>();
        hashW.add(w);

        return length(hashV, hashW);

    }

    // a common ancestor of v and w that participates in a shortest ancestral path;
    // -1 if no such path
    public int ancestor(int v, int w) {

        if (v < 0 || v >= digraph.V() || w < 0 || w >= digraph.V()) {
            throw new IllegalArgumentException();
        }

        HashSet<Integer> hashV = new HashSet<Integer>();
        hashV.add(v);
        HashSet<Integer> hashW = new HashSet<Integer>();
        hashW.add(w);


        return ancestor(hashV, hashW);

    }

    private boolean checkError(Iterable<Integer> list) {
        for (Integer i : list) {
            if (i == null || i<0 || i >= digraph.V()) {
                return false;
            }
        }
        return true;
    }

    private void illegalArgumentThrowing(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null || !checkError(v) || !checkError(w)) {
            throw new IllegalArgumentException();
        }
    }
    

    // length of shortest ancestral path between any vertex in v and any vertex in
    // w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        illegalArgumentThrowing(v, w);
        if (!v.iterator().hasNext() || !w.iterator().hasNext()) {
            return -1;
        }
        
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

        illegalArgumentThrowing(v, w);
        if (!v.iterator().hasNext() || !w.iterator().hasNext()) {
            return -1;
        }
        BreadthFirstDirectedPaths breadv = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths breadw = new BreadthFirstDirectedPaths(digraph, w);
        int minLen = Integer.MAX_VALUE;
        int ancestor = -1;

        for (int i = 0; i < digraph.V(); i++) {

            if (breadv.hasPathTo(i) && breadw.hasPathTo(i)) { // see if there is a path of ancestor i connected and
                                                              // loops for number of V

                int tempMin = breadv.distTo(i) + breadw.distTo(i);

                if (tempMin < minLen || (tempMin == minLen && i < ancestor)) {
                    minLen = tempMin;
                    ancestor = i;
                } else if (tempMin == minLen) {
                    ancestor = i;
                }

            }

        }

        return ancestor;

    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("wordnet\\digraph2.txt");
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
