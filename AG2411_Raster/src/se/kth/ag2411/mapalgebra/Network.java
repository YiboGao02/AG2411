package se.kth.ag2411.mapalgebra;

import java.util.HashMap;
import java.io.*;

public class Network {

    public String name;
    public HashMap<String, Node> nodeMap;

    public Network(String name, String inputFileName){

        this.name = name;
        this.nodeMap = new HashMap<>();
    
        String line, arcID, tailName, headName;
        Node tail, head;
        double length;
        Arc forwardArc, backwardArc;

        try{
            File file = new File(inputFileName);
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);

            line = bReader.readLine();
            //read and skip first line

            while((line = bReader.readLine()) != null){
                String[] tokens = line.split(",");
                arcID = tokens[0];
                tailName = tokens[1];
                headName = tokens[2];
                length = Double.parseDouble(tokens[3]);

                tail = nodeMap.computeIfAbsent(tailName, Node::new);
                head = nodeMap.computeIfAbsent(headName, Node::new);
                //computeIfAbsent returns the value of the key in the hashmap if it exists
                //if the key does not exist, this will create a new Node and put into a hashmap

                forwardArc = new Arc(arcID +"a", tail, head, length);
                backwardArc = new Arc(arcID +"b", tail, head, length);

                tail.addArc(forwardArc);
                head.addArc(backwardArc);

            }
            bReader.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("!");
        }
    }

    public void save(String outputFileName){

        try{
            File file = new File(outputFileName);
            FileWriter fWriter = new FileWriter(file);
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            bWriter.write("TLID,NAME,LENGTH\n");

            for(Node node : nodeMap.values()){
                for(Arc arc : node.outArcs){
                    bWriter.write(arc.name.substring(0, arc.name.length()-1)+ "," + arc.name + ","  + arc.length + "\n");
                }
            }
            bWriter.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("!");
        }
    }

    public void printNodes(){

        System.out.println("\tNODE NAME\tLENGTH");
        Node node;
        for(String nodeName: nodeMap.keySet()){ // loop thru nodeMap
            node = nodeMap.get(nodeName);
            System.out.print("\t" + node.name); // \t represents tab space
            System.out.print("\t\t" + node.value);
            System.out.println();
        }
    }

    public void printArcs(){

        System.out.println("TLID,NAME,LENGTH");
        Node node;
        for(String nodeName: nodeMap.keySet()){ // loop thru nodeMap
            node = nodeMap.get(nodeName);
            for(Arc arc: node.outArcs){
                System.out.print(arc.name.substring(0, arc.name.length()-1) + " ");
                System.out.print(arc.name + " ");
                //System.out.print(arc.head.name + " ");
                System.out.print(arc.length + " ");
                System.out.println();
            }
        }
    }
}
