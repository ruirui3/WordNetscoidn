import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Rui Zhao attests that this code is their original work and was written in compliance with the class Academic Integrity and Collaboration Policy found in the syllabus. 
 */

public class WordNet {

    private Map<String, List<Integer>> nounToIDs;
    private ArrayList<String> synsetList;
    private Digraph digraph;
    private SAP sap;
    

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        In in = new In(synsets);

        nounToIDs = new HashMap<>();
        synsetList = new ArrayList<String>();

        while (in.hasNextLine()) {

            String[] synset = in.readLine().split(",");
            int id = Integer.parseInt(synset[0]);
            String[] numOfNouns = synset[1].split(" ");

            synsetList.add(synset[1]);

            for (String noun : numOfNouns) {
                if (!nounToIDs.containsKey(noun)) {
                    nounToIDs.put(noun, new ArrayList<>()); //marks entire noun structure into one hyp
                }
                nounToIDs.get(noun).add(id);
            }

        }

        digraph = new Digraph(synsetList.size());

        In bingBingBongBong = new In(hypernyms);
        while (bingBingBongBong.hasNextLine()) {

            String[] bingBongList = bingBingBongBong.readLine().split(",");
            int id = Integer.parseInt(bingBongList[0]);
            
            for (int i = 1; i < bingBongList.length; i++) {
                
                digraph.addEdge(id, Integer.parseInt(bingBongList[i]));

            }

        }

        DirectedCycle dc = new DirectedCycle(digraph);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }
        int roots = 0;
        for (int v = 0; v < digraph.V(); v++) {
            if (digraph.outdegree(v) == 0) {
                roots++;
            }
        }
        if (roots > 1) {
            throw new IllegalArgumentException();
        }

        sap = new SAP(digraph);
    
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        
        return nounToIDs.keySet();

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nounToIDs.containsKey(word);

    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        return sap.length(nounToIDs.get(nounA), nounToIDs.get(nounB));

    }

    private int nounToHyp(String noun) {
        if (!nounToIDs.containsKey(noun)) {
            throw new IllegalArgumentException();
        }
        return nounToIDs.get(noun).get(0);
    }

    private String hypToNoun(int hyp) {
        if (hyp<0 || hyp >= synsetList.size()) {
            throw new IllegalArgumentException();
        }
        return synsetList.get(hyp);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {

        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB) ) {
            throw new IllegalArgumentException();
        }

        return hypToNoun(sap.ancestor(nounToHyp(nounA), nounToHyp(nounB)));
        
    }

    // do unit testing of this class
    public static void main(String[] args) {
        
      }
}