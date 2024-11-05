package se.kth.ag2411.mapalgebra;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Layer {

    // Attributes (This is complete)
    public String name; // name of this layer
    public int nRows; // number of rows
    public int nCols; // number of columns
    public double[] origin = new double[2]; // x,y-coordinates of lower-left corner
    public double resolution; // cell size
    public double[] values; // data. Alternatively, public double[][] values;
    public double nullValue; // value designated as "No data"
    //Constructor (This is not complete)

    public int actual_Row = 0;
    public int actual_Col = 0;
    //here we store the actual row and column in the inconsistent data

    public Layer(String layerName, String fileName) {
        
        this.name = layerName;

        try {

            File rFile = new File(fileName);
            FileReader fReader = new FileReader(rFile);
            BufferedReader bReader = new BufferedReader(fReader);
            
            //Read the nCol for the first line
            //Integer
            nCols = Integer.parseInt(bReader.readLine().split("\\s+")[1]);
            //Read the nRow for the second line
            nRows = Integer.parseInt(bReader.readLine().split("\\s+")[1]); 
            origin[0] = Double.parseDouble(bReader.readLine().split("\\s+")[1]); 
            origin[1] = Double.parseDouble(bReader.readLine().split("\\s+")[1]); 
            resolution = Double.parseDouble(bReader.readLine().split("\\s+")[1]); 
            nullValue = Double.parseDouble(bReader.readLine().split("\\s+")[1]); 
            
            //ininialize the values array
            this.values = new double[nRows * nCols];
            //read the remaining lines
            String dataRow = bReader.readLine();
            int count = 0;

            while(dataRow != null){

                String[] subStrings  = dataRow.split(" ");
                
                actual_Col = subStrings.length; //count the actual column in inconsistent data
                //System.out.println(actual_Col);

                for (String value : subStrings) {
                    this.values[count] = Double.parseDouble(value);
                    count = count + 1;
                }
                //print the data on console
                //System.out.println("this is the "+ count +" line" + dataRow);
                dataRow = bReader.readLine();
                
                actual_Row++; //count the actual row in inconsistent data
            }
            //System.out.println(actual_Row);

            bReader.close();
        
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    // (This is complete)
    public void print(){
        //Print this layer to console
        System.out.println("ncols "+nCols);
        System.out.println("nrows "+nRows);
        System.out.println("xllcorner "+origin[0]);
        System.out.println("yllcorner "+origin[1]);
        System.out.println("cellsize "+resolution);
        System.out.println("NODATA_value " + nullValue);

        for (int i = 0; i < actual_Row; i++) {
            for (int j = 0; j < actual_Col; j++) {
                System.out.print(values[i*actual_Col+j]+" ");
            }
            System.out.println();
        }
    }

    // Save (This is not complete)
    public void save(String outputFileName) {
        File file = new File(outputFileName);
        //ASCII data store in file
        try{
            FileWriter fWriter = new FileWriter(file);

            //Write the header
            fWriter.write("ncols         "+nCols+"\n");
            fWriter.write("nrows         "+nRows+"\n");
            fWriter.write("xllcorner     "+origin[0]+"\n");
            fWriter.write("yllcorner     "+origin[1]+"\n");
            fWriter.write("cellsize      "+resolution+"\n");
            fWriter.write("NODATA_value  "+nullValue+"\n");

            //Write the data
            for (int i = 0; i < actual_Row; i++) {
                for (int j = 0; j < actual_Col; j++) {
                    fWriter.write(values[i*actual_Col + j]+" ");
                }
                fWriter.write("\n");
            }
            System.out.println("Saved");
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
