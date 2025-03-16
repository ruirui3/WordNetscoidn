import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private List<String> numOfNoun;
    private List<Integer> numOfID;
    private Digraph digraph;
    private SAP sap;
    

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        
        In in = new In(synsets);

        numOfNoun = new ArrayList<String>();
        numOfID = new ArrayList<Integer>();

        while (in.hasNextLine()) {

            String[] synset = in.readLine().split(",");
            
            String[] numOfNouns = synset[1].split(" ");


            for (int i = 0; i<numOfNouns.length; i++) {
                numOfNoun.add(numOfNouns[i]);
            }

        }

        digraph = new Digraph(numOfNoun.size());

        In bingBingBongBong = new In(hypernyms);
        while (bingBingBongBong.hasNextLine()) {

            String[] bingBongList = bingBingBongBong.readLine().split(",");
            numOfID.add(Integer.parseInt(bingBongList[0]));
            
            for (int i = 1; i < bingBongList.length; i++) {
                
                digraph.addEdge(Integer.parseInt(bingBongList[0]), Integer.parseInt(bingBongList[i]));

            }

        }

        sap = new SAP(digraph);
    
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        
        return numOfNoun;

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {

        return numOfNoun.contains(word);

    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {

        return sap.length(nounToHyp(nounA), nounToHyp(nounB));

    }

    private int nounToHyp(String noun) {
        return numOfID.get(numOfNoun.indexOf(noun));
    }

    private String hypToNoun(int hyp) {
        return numOfNoun.get(numOfID.indexOf(hyp));
    }




    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return hypToNoun(sap.ancestor(nounToHyp(nounA), nounToHyp(nounB)));
        
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}