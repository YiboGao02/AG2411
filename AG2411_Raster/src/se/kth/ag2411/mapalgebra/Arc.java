package se.kth.ag2411.mapalgebra;

public class Arc {

    public String name;
    public Node tail;
    public Node head;
    public double length;

    public Arc(String name, Node tail, Node head, double length) {
        this.name = name;
        this.tail = tail;
        this.head = head;
        this.length = length;
        
    }



}
