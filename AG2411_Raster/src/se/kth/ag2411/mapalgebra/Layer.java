package se.kth.ag2411.mapalgebra;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

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

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                System.out.print(values[i*nCols+j]+" ");
            }
            System.out.println();
        }
    }

    //
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
            for (int i = 0; i < nRows; i++) {
                for (int j = 0; j < nCols; j++) {
                    fWriter.write(values[i*nCols + j]+" ");
                }
                fWriter.write("\n");
            }
            System.out.println("Saved");
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //
    public BufferedImage toImage() {

            BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);
            WritableRaster raster = image.getRaster();

            //find the min and max value in values list
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;
            for(double value : values){
                if(value != nullValue){ //ignore the null value
                    if(value < min){
                        min = value;
                    }
                    if(value > max){
                        max = value;
                    }
                }
            }

            for(int i = 0; i < nRows; i++){
                for(int j = 0; j < nCols; j++){
                    
                    int temp_finder = i * nCols + j;
                    double temp_value = values[temp_finder];
                    int gray = 0;

                    if(temp_value != nullValue){
                        //convert the value to gray scale
                        //the largest is black and the smallest is white
                        
                        //gray = (int) (255 * (max - temp_value) / (max - min));
                        
                        //when max == min give 128 to that pixel
                        if(max == min){
                            gray = 128;
                        } else{
                            gray = (int) (255 * (max - temp_value) / (max - min));
                        }
                    }

                    int[] gray_color = {gray, gray, gray};
                    raster.setPixel(j, i, gray_color);
                    //set the pixel at (j,i) to its converted gray scale value
                }
            }
            return image;
    }

    public BufferedImage toImage(double[] value_of_interest){
       
        BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();

        Map<Double, int[]> colorMap = new HashMap<Double, int[]>();
        
        Random random = new Random();

        //assign a random color to each value of intrerest
        for(double value : value_of_interest){
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            colorMap.put(value, new int[]{r, g, b});
        }

        for(int i = 0; i < nRows; i++){
            for(int j = 0; j < nCols; j++){
                int temp_finder = i * nCols + j;
                double temp_value = values[temp_finder];
                int[] color = null;
                
                if(temp_value == nullValue){
                    //if the value is null, set the pixel to white
                    color = new int[]{255, 255, 255};
                } else if (colorMap.containsKey(temp_value)) {
                    //if the value is in the colorMap, set the pixel to the color
                    color = colorMap.get(temp_value);
                } else {
                    //if the value is not in the colorMap, set the pixel to white
                    color = new int[]{255, 255, 255};
                }

                raster.setPixel(j, i, color);
            }
        }
        return image;
    }

    //New Layer method for creating an empty layer to store the output of a map algebra operation
    public Layer(String name, int nRows, int nCols, double[] origin, double resolution, double nullValue) {
        
        this.name = name; 
        this.nRows = nRows; 
        this.nCols = nCols; 
        this.origin = origin; 
        this.resolution = resolution; 
        this.nullValue = nullValue; 
    
        // 初始化 values 数组以存储每个单元格的值
        this.values = new double[nRows * nCols];
    }
    
    public Layer localSum(Layer inLayer, String outLayerName) {
        //
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);

        
        for (int i = 0; i < (nRows * nCols); i++) {
            outLayer.values[i] = values[i] + inLayer.values[i];
        }

        // 返回包含求和值的输出 Layer 对象
        return outLayer;
    }

    public Layer focalVariety(int radius, boolean isSquare, String outLayerName) {
        //
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);

        
        for (int i = 0; i < nRows * nCols; i++) {
            
            int[] neighbors = neighborhood(i, radius, isSquare);

            // 创建一个集合来存储唯一值
            Set<Double> uniqueValues = new HashSet<>();
            
            for (int index : neighbors) {
                double value = values[index];
                if (value != nullValue) { // 忽略无数据值
                    uniqueValues.add(value);
                }
            }

           
            outLayer.values[i] = uniqueValues.size();
        }

        return outLayer;
    }

    //
    private int[] neighborhood(int index, int radius, boolean isSquare) {
        
        List<Integer> neighborIndices = new ArrayList<>();

       
        int row = index / nCols;
        int col = index % nCols;

        
        for (int r = -radius; r <= radius; r++) {
            for (int c = -radius; c <= radius; c++) {
                int neighborRow = row + r;
                int neighborCol = col + c;

                // 检查是否超出边界
                if (neighborRow >= 0 && neighborRow < nRows && neighborCol >= 0 && neighborCol < nCols) {
                    // 如果是正方形邻域，直接添加；如果是圆形邻域，检查是否在半径范围内
                    if (isSquare || (Math.sqrt(r * r + c * c) <= radius)) {
                        // 将二维索引转换回一维索引并添加到列表中
                        neighborIndices.add(neighborRow * nCols + neighborCol);
                    }
                }
            }
        }

       
        return neighborIndices.stream().mapToInt(i -> i).toArray();
    }

    public Layer zonalMinimum(Layer zoneLayer, String outLayerName) {
        //
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);

        
        Map<Double, Double> zoneMinMap = new HashMap<>();

      
        for (int i = 0; i < nRows * nCols; i++) {
            double zone = zoneLayer.values[i];
            double value = values[i];

            if (value != nullValue) { 
                
                if (!zoneMinMap.containsKey(zone) || value < zoneMinMap.get(zone)) {
                    zoneMinMap.put(zone, value);
                }
            }
        }

        
        for (int i = 0; i < nRows * nCols; i++) {
            double zone = zoneLayer.values[i];
            if (zoneMinMap.containsKey(zone)) {
                outLayer.values[i] = zoneMinMap.get(zone);
            } else {
                outLayer.values[i] = nullValue; 
            }
        }

        return outLayer;
    }

}
