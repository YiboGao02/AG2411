package se.kth.ag2411.mapalgebra;

import java.util.LinkedList;

public class Node {

    public String name;
    public double value;
    public LinkedList<Arc> outArcs;

    public Node(String name) {
        this.name = name;
        this.value = Double.POSITIVE_INFINITY; // "infinity" as default
        this.outArcs = new LinkedList<>();     // Initialize an empty LinkedList
    }

    public void addArc(Arc arc){
        outArcs.add(arc);
    }

}
