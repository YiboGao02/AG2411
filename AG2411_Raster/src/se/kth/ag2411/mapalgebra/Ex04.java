package se.kth.ag2411.mapalgebra;

public class Ex04 {
    public static void main(String args[]){
        
        args = new String[]{
            "data\\smallnetwork.txt",
            "output.txt",
        };
       
        String inputFile = args[0];
        String outputFile = args[1];

        Network network = new Network("network", inputFile);
        network.save(outputFile);
        network.printNodes();
        network.printArcs();
    }
}
