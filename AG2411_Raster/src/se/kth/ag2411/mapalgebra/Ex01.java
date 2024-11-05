package se.kth.ag2411.mapalgebra;
import java.io.File;

public class Ex01 {
    public static void main(String[] args) {

        //args = new String[]{"ForATest","src\\raster3x4.txt", "outputs.txt"};
        File inputFile = new File(args[1]);
        if (!inputFile.exists()) {
            System.out.println("File do not exist");
            return;
        }
        else{
            System.out.println("File exist");
        }

        if(args.length == 3){
        //Instantiate a layer
        Layer layer = new Layer(args[0], args[1]);
        //Printing it on the console
        layer.print();
        //Saving it to the file output.txt
        layer.save(args[2]);
        }
        else {
            System.out.println("Too many or few arguments......");
        }
    } 
}
