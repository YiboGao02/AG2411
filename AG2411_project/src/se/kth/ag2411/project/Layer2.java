package se.kth.ag2411.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class Layer2 {
		
		// Attributes (This is complete)
		public String name; // name of this layer
		public int nRows; // number of rows
		public int nCols; // number of columns
		public double[] origin = new double[2]; // x,y-coordinates of lower-left corner
		public double resolution; // cell size
		public double[] values; // data. Alternatively, public double[][] values;
		public double nullValue; // value designated as "No data"
		
		//Constructor
		public Layer2(String layerName, String fileName) {
		// You may want to do some work before reading a file.
			try { // Exception may be thrown while reading (and writing) a file.
				
				// This object represents an input file, elevation.txt, located at ./data/.
				File rFile = new File(fileName);
				
				// This object represents a stream of characters read from the file.
				FileReader fReader = new FileReader(rFile);
				
				// This object represents lines of Strings created from the stream of characters.
				BufferedReader bReader = new BufferedReader(fReader);
				
		        // Read the first six lines as metadata
		        this.nCols = Integer.parseInt(bReader.readLine().split("\\s+")[1]);
		        this.nRows = Integer.parseInt(bReader.readLine().split("\\s+")[1]);
		        this.origin[0] = Double.parseDouble(bReader.readLine().split("\\s+")[1]);
		        this.origin[1] = Double.parseDouble(bReader.readLine().split("\\s+")[1]);
		        this.resolution = Double.parseDouble(bReader.readLine().split("\\s+")[1]);
		        this.nullValue = Double.parseDouble(bReader.readLine().split("\\s+")[1]);
		        this.values = new double[nRows*nCols]; 
		     // Read the matrix values
		        for (int i = 0; i < nRows; i++) {
		            String line = bReader.readLine(); // Read each subsequent line
		            String[] parts = line.trim().split("\\s+"); // Split the line into parts
		            for (int j = 0; j < nCols; j++) {
		                // Parse and store the value in the array
		                this.values[i * nCols + j] = Double.parseDouble(parts[j]);
		            }
		        }
				bReader.close();
			
			} catch (IOException e) {
				System.err.println("Error reading file: " + e.getMessage());
			} catch (NumberFormatException e) {
				System.err.println("Error parsing number: " + e.getMessage());
			}
		}
		
		public Layer(String name, int nRows, int nCols, double[] origin,
				double resolution, double nullValue) {
				// construct a new layer by assigning a value to each of its attributes
		    	this.name = name;
		    	this.nRows = nRows;
		    	this.nCols = nCols;
		    	this.origin = origin;
		    	this.resolution = resolution;
		    	this.nullValue = nullValue;
		    	this.values = new double[nRows * nCols];
			}
		
		// Print (This is complete)
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
		
		// Save (This is complete)
		public void save(String outputFileName) {
		    // This object represents an output file
		   File file = new File(outputFileName);
		    
		    // Use try-with-resources to ensure resources are closed automatically
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
		        //Write metadata to the file
		        bw.write("ncols " + nCols + "\n"); 
		        bw.write("nrows " + nRows + "\n"); 
		        bw.write("xllcorner " + origin[0] + "\n");
		        bw.write("yllcorner " + origin[1] + "\n");
		        bw.write("cellsize " + resolution + "\n");
		        bw.write("NODATA_value " + nullValue + "\n");
		        
		        //Write the values to the file
		        for (int i = 0; i < nRows; i++) {
		            for (int j = 0; j < nCols; j++) {
		                bw.write(values[i * nCols + j] + " ");
		            }
		            bw.newLine(); // Move to the next line after each row
		        }
		    } catch (IOException e) {
		        e.printStackTrace(); // Handle any IO exceptions
		    }
		}
		
		public double getMax() {
			double max = Double.NEGATIVE_INFINITY;
				for (int i = 0; i < nRows; i++) {
					for (int j = 0; j < nCols; j++) {
						if (values[i*nCols+j] > max) {
						max = values[i*nCols+j];
						}
					}
				}
				return max;
			}
		
		public double useGetMaxIndirectly() {
			double max = this.getMax(); // calling getMax()
			return max;
			}
		
		public double getMin() {
		    double min = Double.POSITIVE_INFINITY; // Start with the largest possible value
		    for (int i = 0; i < nRows; i++) {
		        for (int j = 0; j < nCols; j++) {
		            double value = values[i * nCols + j];
		            if (value < min) { // Update min if a smaller value is found
		                min = value;
		            }
		        }
		    }
		    return min; // Return the minimum value found
		}
		
		// Create a BufferedImage of the layer in grayscale
		public BufferedImage toImage () {
	        // Create a 24-bit RGB image with the same dimensions as the layer
	        BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);
	        WritableRaster raster = image.getRaster();
	        
	        // Calculate the maximum value in the layer
	        double maxVal = getMax();
	        double minVal = getMin();
	        
	        // Loop through each cell to set the pixel color
	        int[] color = new int[3]; // Color array for [R, G, B]
	        for (int row = 0; row < nRows; row++) {
	            for (int col = 0; col < nCols; col++) {
	                // Get the value at (row, col)
	                double cellValue = values[row * nCols + col];

	                // If the cell value is a null value, set it to white
	                int grayscale;
	                if (cellValue == nullValue) {
	                    grayscale = 255; // White for null values
	                } else {
	                    // Calculate the grayscale value (darker for higher values)
	                	grayscale = (int) ((1 - ((cellValue - minVal) / (maxVal - minVal))) * 255);

	                }

	                // Set R, G, B to the grayscale value
	                color[0] = color[1] = color[2] = grayscale;

	                // Assign the color to the pixel at (col, row)
	                raster.setPixel(col, row, color);
	            }
	        }
	        return image;
			
		}
		// Visualize a BufferedImage of the layer in color
		public BufferedImage toImage (double[] valuesOfInterest) {
		    // Step 1: Create a 24-bit RGB image with the same dimensions as the layer
		    BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);
		    WritableRaster raster = image.getRaster();

		    // Step 2: Generate random colors for each value in the input array
		    Map<Double, int[]> colorMap = new HashMap<>();
		    Random random = new Random();
		    
		    for (double value : valuesOfInterest) {
		        int[] color = new int[3]; // Color array for [R, G, B]
		        color[0] = random.nextInt(256); // Random Red
		        color[1] = random.nextInt(256); // Random Green
		        color[2] = random.nextInt(256); // Random Blue
		        colorMap.put(value, color);
		    }
		    // Step 3: Assign colors to each cell in the layer based on its value
		    int[] defaultColor = {255, 255, 255}; // Default color (white) for cells not in valuesOfInterest

	        // Calculate the maximum value in the layer
	        double maxVal = getMax();
	        double minVal = getMin();
		    
		    for (int row = 0; row < nRows; row++) {
		        for (int col = 0; col < nCols; col++) {
		            // Get the value at the current cell
		            double cellValue = values[row * nCols + col];

		            // Check if the value is in the valuesOfInterest list
		            int[] color;
		            if (colorMap.containsKey(cellValue)) {
		                color = colorMap.get(cellValue); // Use the assigned color
		            } else {
		            	// If not in valuesOfInterest, assign a grayscale color
		            	// Uncomment if you want grayscale coloring for other values
		            	/*
		                int grayscale;
		                if (cellValue == nullValue) {
		                    grayscale = 255; // White for null values
		                } else {
		                    // Calculate the grayscale value (darker for higher values)
		                	grayscale = (int) ((1 - ((cellValue - minVal) / (maxVal - minVal))) * 255);
		                    
		            }
		                color = new int[] {grayscale, grayscale, grayscale}; // Set R, G, B to grayscale
		                */
		            	color = defaultColor; // Use default color (white)
		        }
		         // Set the pixel color in the image
		         raster.setPixel(col, row, color);
		    }

		}
		// Step 4: Return the colored image
		return image;
	}
///////////////////////// LOCAL /////////////////////////////////////////
		
		//LocalSum
		public Layer localSum(Layer inLayer, String name){
			
		    // Create a new Layer object to store the output results.
		    // It uses the same dimensions, origin, resolution, and null value as the current layer
			Layer outLayer = new Layer(name, nRows, nCols, origin,
			resolution, nullValue);
			
			// Loop through each cell in the layer (using the total number of cells, nRows * nCols)
			for(int i=0; i<(nRows*nCols); i++){
		        // Compute the sum of the corresponding cell values from the current layer
		        // and the input layer, and store the result in the output layer
				outLayer.values[i] = values[i] + inLayer.values[i];
			}
			
			// Return the newly created output layer containing the summed values.
			return outLayer;
		}
		
		
		
		
		
		//LocalDifference
		public Layer localDifference(Layer inLayer, String name){
			
		    // Create a new Layer object to store the output results.
		    // It uses the same dimensions, origin, resolution, and null value as the current layer
			Layer outLayer = new Layer(name, nRows, nCols, origin,
			resolution, nullValue);
			
			// Loop through each cell in the layer (using the total number of cells, nRows * nCols)
			for(int i=0; i<(nRows*nCols); i++){
		        // Compute the difference of the corresponding cell values from the current layer
		        // and the input layer, and store the result in the output layer
				outLayer.values[i] = values[i] - inLayer.values[i];
			}
			
			// Return the newly created output layer containing the difference values.
			return outLayer;
		}
		
		
		
		
		
		//LocalProduct
		public Layer localProduct(Layer inLayer, String name){
			
		    // Create a new Layer object to store the output results.
		    // It uses the same dimensions, origin, resolution, and null value as the current layer
			Layer outLayer = new Layer(name, nRows, nCols, origin,
			resolution, nullValue);
			
			// Loop through each cell in the layer (using the total number of cells, nRows * nCols)
			for(int i=0; i<(nRows*nCols); i++){
		        // Compute the product of the corresponding cell values from the current layer
		        // and the input layer, and store the result in the output layer
				outLayer.values[i] = values[i] * inLayer.values[i];
			}
			
			// Return the newly created output layer containing the product values.
			return outLayer;
		}
		
		
		
		
		
		//LocalMean
		public Layer localMean(Layer inLayer, String name){
			
		    // Create a new Layer object to store the output results.
		    // It uses the same dimensions, origin, resolution, and null value as the current layer
			Layer outLayer = new Layer(name, nRows, nCols, origin,
			resolution, nullValue);
			
			// Loop through each cell in the layer (using the total number of cells, nRows * nCols)
		    for(int i=0; i<(nRows*nCols); i++){
		        // Compute the mean of the corresponding cell values from the current layer
		        // and the input layer, and store the result in the output layer
		        outLayer.values[i] = (values[i] + inLayer.values[i]) / 2;
		    }
		    // Return the newly created output layer containing the mean values.
		    return outLayer;
		}
		
		
		
		
		
		//LocalVariety
		public Layer localVariety(Layer inLayer, String name){
			
		    // Create a new Layer object to store the output results.
		    // It uses the same dimensions, origin, resolution, and null value as the current layer
		    Layer outLayer = new Layer(name, nRows, nCols, origin,
		    resolution, nullValue);
		    
		    // Loop through each cell in the layer (using the total number of cells, nRows * nCols)
		    for(int i=0; i<(nRows*nCols); i++){
		        // Compare the current value with the value in the input layer for the same cell
		        if (values[i] == inLayer.values[i]) {
		            outLayer.values[i] = 1;  // If the values are the same, assign a variety of 1 (same)
		        } else {
		            outLayer.values[i] = 2;  // If the values are different, assign a variety of 2 (different)
		        }
		    }
		    
		    // Return the newly created output layer containing the variety (squared differences).
		    return outLayer;
		    
		}
		
/////////////////////// FOCAL ///////////////////////////
		
	

}
