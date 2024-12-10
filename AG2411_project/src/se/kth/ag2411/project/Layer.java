package se.kth.ag2411.project;

import java.util.Set;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Arrays;
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
    public double[][] values; // data. Alternatively, public double[][] values;
    public double nullValue; // value designated as "No data"
    //Constructor (This is not complete)

    public double max = Double.NEGATIVE_INFINITY; 
	public double min = Double.POSITIVE_INFINITY; 

    public int actual_Row = 0;
    public int actual_Col = 0;
    //here we store the actual row and column in the inconsistent data

	public Layer(String layerName, String fileName) {
	
		File rFile = new File (fileName);
		if(rFile.exists()) {
			this.name = layerName;
		
				try { 				
					
					File inputfile = new File(fileName);
					FileReader fReader = new FileReader(inputfile);
					BufferedReader bReader = new BufferedReader(fReader);
					
					String temporary;
						
					temporary = bReader.readLine().substring(14).trim();
					nCols = Integer.parseInt(temporary);
					temporary = bReader.readLine().substring(14).trim();
					nRows = Integer.parseInt(temporary);
					temporary = bReader.readLine().substring(14).trim();
					origin[0] = Double.parseDouble(temporary);
					temporary = bReader.readLine().substring(14).trim();
					origin[1] = Double.parseDouble(temporary);
					temporary = bReader.readLine().substring(14).trim();
					resolution = Double.parseDouble(temporary);
					temporary = bReader.readLine().substring(14).trim();
					nullValue = Double.parseDouble(temporary);
						
					this.values = new double[nRows][nCols];
								
					// Read each of the remaining lines, which represents a row of raster values
					for (int i = 0; i < nRows; i++) {
						
						String arow=bReader.readLine();					
						String[] arow_value=arow.split("\\s+");
						
						for (int j = 0; j < nCols; j++) {
							this.values[i][j]=Double.parseDouble(arow_value[j].trim());
							if (values[i][j] > max) {
								max = values[i][j];
							}
							if (values[i][j] < min) {
								min = values[i][j];
							}
							
						}
						
					}
					
					bReader.close();
			 						
					} catch (Exception e) {
                        e.printStackTrace();
						// UI.checker=false; 
						// JOptionPane.showMessageDialog(null,"Raster ASCII file is not formatted correctly.");
					}
		}			
		else {
			JOptionPane.showMessageDialog(null,"File does not exist.");
		}
	}

	public Layer(String outLayerName, int nRows2, int nCols2, double[] ori, double res, double nullV) {
		this.name = outLayerName;
		this.nRows = nRows2;
		this.nCols = nCols2;
		this.origin[0] = ori[0];
		this.origin[1] = ori[1];
		this.resolution = res;
		this.nullValue = nullV;
		this.values = new double[nRows][nCols];
	}

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
    
    public int[] neighbourhood(int row, int col, int radius, boolean isSquare) {
        List<Integer> neighbors = new ArrayList<>();
    
        if (isSquare) {
            for (int i = row - radius; i <= row + radius; i++) {
                for (int j = col - radius; j <= col + radius; j++) {
                    if (i >= 0 && i < nRows && j >= 0 && j < nCols) {
                        neighbors.add(i * nCols + j);
                    }
                }
            }
        } else {
            for (int i = 0; i < nRows; i++) {
                for (int j = 0; j < nCols; j++) {
                    if (Math.pow(i - row, 2) + Math.pow(j - col, 2) <= Math.pow(radius, 2)) {
                        neighbors.add(i * nCols + j);
                    }
                }
            }
        }
    
        return neighbors.stream().mapToInt(i -> i).toArray();
    }
    

    public BufferedImage toImage() {

        BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
    
        // Find the min and max value in values list
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                double value = values[i][j];
                if (value != nullValue) { // Ignore the null value
                    if (value < min) {
                        min = value;
                    }
                    if (value > max) {
                        max = value;
                    }
                }
            }
        }
    
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                double temp_value = values[i][j];
                int gray = 0;
    
                if (temp_value != nullValue) {
                    // Convert the value to gray scale
                    // The largest is black and the smallest is white
    
                    // When max == min give 128 to that pixel
                    if (max == min) {
                        gray = 128;
                    } else {
                        gray = (int) (255 * (max - temp_value) / (max - min));
                    }
                }
    
                int[] gray_color = {gray, gray, gray};
                raster.setPixel(j, i, gray_color);
                // Set the pixel at (j, i) to its converted gray scale value
            }
        }
        return image;
    }

    public BufferedImage toImage(double[] value_of_interest) {

        BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
    
        Map<Double, int[]> colorMap = new HashMap<Double, int[]>();
    
        Random random = new Random();
    
        // Assign a random color to each value of interest
        for (double value : value_of_interest) {
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            colorMap.put(value, new int[]{r, g, b});
        }
    
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                double temp_value = values[i][j];
                int[] color = null;
    
                if (temp_value == nullValue) {
                    // If the value is null, set the pixel to white
                    color = new int[]{255, 255, 255};
                } else if (colorMap.containsKey(temp_value)) {
                    // If the value is in the colorMap, set the pixel to the color
                    color = colorMap.get(temp_value);
                } else {
                    // If the value is not in the colorMap, set the pixel to white
                    color = new int[]{255, 255, 255};
                }
    
                raster.setPixel(j, i, color);
            }
        }
        return image;
    }
    
/////local operations////////////////////////
    public Layer localSum(Layer inLayer, String name) {

        // Create a new Layer object to store the output results.
        // It uses the same dimensions, origin, resolution, and null value as the current layer
        Layer outLayer = new Layer(name, nRows, nCols, origin, resolution, nullValue);
    
        // Loop through each cell in the layer
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                outLayer.values[i][j] = values[i][j] + inLayer.values[i][j];
            }
        }
    
        // Return the newly created output layer containing the summed values.
        return outLayer;
    }
    
    public Layer localDifference(Layer inLayer, String name) {
    
        // Create a new Layer object to store the output results.
        // It uses the same dimensions, origin, resolution, and null value as the current layer
        Layer outLayer = new Layer(name, nRows, nCols, origin, resolution, nullValue);
    
        // Loop through each cell in the layer
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                outLayer.values[i][j] = values[i][j] - inLayer.values[i][j];
            }
        }
    
        // Return the newly created output layer containing the difference values.
        return outLayer;
    }
    
    public Layer localProduct(Layer inLayer, String name) {
    
        // Create a new Layer object to store the output results.
        // It uses the same dimensions, origin, resolution, and null value as the current layer
        Layer outLayer = new Layer(name, nRows, nCols, origin, resolution, nullValue);
    
        // Loop through each cell in the layer
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                outLayer.values[i][j] = values[i][j] * inLayer.values[i][j];
            }
        }
    
        // Return the newly created output layer containing the product values.
        return outLayer;
    }
    
    public Layer localMean(Layer inLayer, String name) {
    
        // Create a new Layer object to store the output results.
        // It uses the same dimensions, origin, resolution, and null value as the current layer
        Layer outLayer = new Layer(name, nRows, nCols, origin, resolution, nullValue);
    
        // Loop through each cell in the layer
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                outLayer.values[i][j] = (values[i][j] + inLayer.values[i][j]) / 2;
            }
        }
    
        // Return the newly created output layer containing the mean values.
        return outLayer;
    }
    
    public Layer localVariety(Layer inLayer, String name) {
    
        // Create a new Layer object to store the output results.
        // It uses the same dimensions, origin, resolution, and null value as the current layer
        Layer outLayer = new Layer(name, nRows, nCols, origin, resolution, nullValue);
    
        // Loop through each cell in the layer
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                // Compare the current value with the value in the input layer for the same cell
                if (values[i][j] == inLayer.values[i][j]) {
                    outLayer.values[i][j] = 1;  // If the values are the same, assign a variety of 1 (same)
                } else {
                    outLayer.values[i][j] = 2;  // If the values are different, assign a variety of 2 (different)
                }
            }
        }
    
        // Return the newly created output layer containing the variety (squared differences).
        return outLayer;
    }
    
////Focal operations////////////////////////
    public Layer focalSum(int radius, boolean isSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int[] neighborIndices = neighbourhood(i, j, radius, isSquare);
                double[] neighborValues = new double[neighborIndices.length];
                for (int k = 0; k < neighborIndices.length; k++) {
                    int row = neighborIndices[k] / nCols;
                    int col = neighborIndices[k] % nCols;
                    neighborValues[k] = values[row][col];
                }
                Arrays.sort(neighborValues);
                int sum = 0;
                for (double value : neighborValues) {
                    sum += value;
                }
                outLayer.values[i][j] = sum;
            }
        }
        return outLayer;
    }

    public Layer focalMean(int radius, boolean isSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.values = this.values;
        outLayer = outLayer.focalSum(radius, isSquare, outLayerName);
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                outLayer.values[i][j] = Math.round(outLayer.values[i][j] / 9);
            }
        }
        return outLayer;
    }

    public Layer focalVariety(int radius, boolean isSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int[] neighborIndices = neighbourhood(i, j, radius, isSquare);
                double[] neighborValues = new double[neighborIndices.length];
                for (int k = 0; k < neighborIndices.length; k++) {
                    int row = neighborIndices[k] / nCols;
                    int col = neighborIndices[k] % nCols;
                    neighborValues[k] = values[row][col];
                }
                Arrays.sort(neighborValues);
                int variety = 1;
                for (int k = 0; k < neighborValues.length - 1; k++) {
                    if (neighborValues[k + 1] != neighborValues[k]) {
                        variety++;
                    }
                }
                outLayer.values[i][j] = variety;
            }
        }
        return outLayer;
    }

    public double focalMaximum(int radius, boolean isSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.focalSum(radius, isSquare, outLayerName);
        double max = values[0][0];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (max < values[i][j]) {
                    max = values[i][j];
                }
            }
        }
        return max;
    }

    public double focalMinimum(int radius, boolean isSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        outLayer.focalSum(radius, isSquare, outLayerName);
        double min = values[0][0];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (min > values[i][j]) {
                    min = values[i][j];
                }
            }
        }
        return min;
    }

    public Layer focalSlope(String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        double slope;
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (i >= 1 && i < nRows - 1 && j >= 1 && j < nCols - 1) {
                    double xSlope = ((values[i - 1][j + 1] + 2 * values[i][j + 1] + values[i + 1][j + 1]) - (values[i - 1][j - 1] + 2 * values[i][j - 1] + values[i + 1][j - 1])) / (8 * resolution);
                    double ySlope = ((values[i + 1][j - 1] + 2 * values[i + 1][j] + values[i + 1][j + 1]) - (values[i - 1][j - 1] + 2 * values[i - 1][j] + values[i - 1][j + 1])) / (8 * resolution);
                    slope = Math.atan(Math.sqrt(Math.pow(xSlope, 2) + Math.pow(ySlope, 2))) * 180 / Math.PI;
                } else {
                    slope = nullValue;
                }
                outLayer.values[i][j] = Math.round(slope);
            }
        }
        return outLayer;
    }

////zonal operations////////////////////////
    public Layer zonalSum(Layer zoneLayer, String outLayerName) {
        if (this.nRows != zoneLayer.nRows || this.nCols != zoneLayer.nCols) {
            throw new IllegalArgumentException("Input layers must have the same dimensions.");
        }

        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        Map<Double, Double> zoneSums = new HashMap<>();

        // Calculate the sum for each zone
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                double zone = zoneLayer.values[i][j];
                if (zone != zoneLayer.nullValue) {
                    zoneSums.put(zone, zoneSums.getOrDefault(zone, 0.0) + values[i][j]);
                }
            }
        }

        // Assign the sum value to each cell in the output layer based on its zone
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                double zone = zoneLayer.values[i][j];
                if (zone != zoneLayer.nullValue) {
                    outLayer.values[i][j] = zoneSums.getOrDefault(zone, nullValue);
                } else {
                    outLayer.values[i][j] = nullValue;
                }
            }
        }

        return outLayer;
    }

    public Layer zonalMean(Layer zoneLayer, String outLayerName) {
        Layer zoneOutPutLayer = new Layer(name, nRows, nCols, origin,
                resolution, nullValue);
        HashMap<Double, Double> uniqueZone = new HashMap<>(); // Save all zones.
        double zoneCount = 0;
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (uniqueZone.containsValue(zoneLayer.values[i][j]) == false) { // Does the value exist in zonemap?
                    zoneCount = zoneCount + 1;
                    uniqueZone.put(zoneCount, zoneLayer.values[i][j]); // If not, add it.
                }
            }
        }
        
        HashMap<Double, Double> meanZone = new HashMap<>(); 
        for (double z = 1; z <= zoneCount; z++) {
            ArrayList<Double> zoneValues = new ArrayList<Double>();
            double mean = 0;
            for (int i = 0; i < nRows; i++) {
                for (int j = 0; j < nCols; j++) {
                    if (zoneLayer.values[i][j] == uniqueZone.get(z)) { 
                        zoneValues.add(values[i][j]);
                    }
                    }
                }
            double total = 0;
            for (int k = 0; k<=zoneValues.size()-1; k++) {
                    total = total + zoneValues.get(k);
                    mean = total/zoneValues.size();
            }
            meanZone.put(z, mean); 
        }
        
        for (double z = 1; z <= zoneCount; z++) {
            for (int i = 0; i < nRows; i++) {
                for (int j = 0; j < nCols; j++) {
                    if (zoneLayer.values[i][j] == uniqueZone.get(z)) { 
                        zoneOutPutLayer.values[i][j] = meanZone.get(z);
                    }
                }
            }
        }
        return zoneOutPutLayer;
    }

    public Layer zonalVariety(Layer zoneLayer, String outLayerName) {
        if (this.nRows != zoneLayer.nRows || this.nCols != zoneLayer.nCols) {
            throw new IllegalArgumentException("Input layers must have the same dimensions.");
        }
    
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        Map<Double, Set<Double>> zoneValues = new HashMap<>();
    
        // Collect unique values for each zone
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                double zone = zoneLayer.values[i][j];
                if (zone != zoneLayer.nullValue) {
                    zoneValues.putIfAbsent(zone, new HashSet<>());
                    zoneValues.get(zone).add(values[i][j]);
                }
            }
        }
    
        // Assign the variety count to each cell in the output layer based on its zone
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                double zone = zoneLayer.values[i][j];
                if (zone != zoneLayer.nullValue) {
                    outLayer.values[i][j] = zoneValues.getOrDefault(zone, new HashSet<>()).size();
                } else {
                    outLayer.values[i][j] = nullValue;
                }
            }
        }
    
        return outLayer;
    }

	public Layer zonalMinimum(Layer zoneLayer, String outLayerName) {
		Layer zoneOutPutLayer = new Layer(name, nRows, nCols, origin,
				resolution, nullValue);
		HashMap<Double, Double> uniqueZone = new HashMap<>(); 
		double zoneCount = 0;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (uniqueZone.containsValue(zoneLayer.values[i][j]) == false) {
					zoneCount = zoneCount + 1;
					uniqueZone.put(zoneCount, zoneLayer.values[i][j]); 
				}

			}

		}
		
		HashMap<Double, Double> minZone = new HashMap<>(); 
		for (double z = 1; z <= zoneCount; z++) {
			double minimum = Double.POSITIVE_INFINITY;
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					if (zoneLayer.values[i][j] == uniqueZone.get(z)) { 
						if (values[i][j] < minimum) {
							minimum = values[i][j];
						}
					}
				}
			}
			minZone.put(z, minimum); 
		}
		
		for (double z = 1; z <= zoneCount; z++) {
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					if (zoneLayer.values[i][j] == uniqueZone.get(z)) {  
						zoneOutPutLayer.values[i][j] = minZone.get(z);
					}

				}
			}
		}

		return zoneOutPutLayer;
	}
	
	public Layer zonalMaximum(Layer zoneLayer, String outLayerName) {
		Layer zoneOutPutLayer = new Layer(name, nRows, nCols, origin,
				resolution, nullValue);
		HashMap<Double, Double> uniqueZone = new HashMap<>(); 
		double zoneCount = 0;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (uniqueZone.containsValue(zoneLayer.values[i][j]) == false) {
					zoneCount = zoneCount + 1;
					uniqueZone.put(zoneCount, zoneLayer.values[i][j]); 
				}

			}

		}
		
		HashMap<Double, Double> minZone = new HashMap<>(); 
		for (double z = 1; z <= zoneCount; z++) {
			double maximum = Double.NEGATIVE_INFINITY;
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					if (zoneLayer.values[i][j] == uniqueZone.get(z)) { 
						if (values[i][j] > maximum) {
							maximum = values[i][j];
						}
					}
				}
			}
			minZone.put(z, maximum); 
		}
		
		for (double z = 1; z <= zoneCount; z++) {
			for (int i = 0; i < nRows; i++) {
				for (int j = 0; j < nCols; j++) {
					if (zoneLayer.values[i][j] == uniqueZone.get(z)) {  
						zoneOutPutLayer.values[i][j] = minZone.get(z);
					}

				}
			}
		}

		return zoneOutPutLayer;
	}

    public Layer zonalMajority(Layer zoneLayer, String outLayerName) {
        if (this.nRows != zoneLayer.nRows || this.nCols != zoneLayer.nCols) {
            throw new IllegalArgumentException("Input layers must have the same dimensions.");
        }
    
        Layer zoneOutputLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        Map<Double, Set<Double>> uniqueZones = new HashMap<>();
    
        // Collect all unique zones from the zoneLayer
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                double zone = zoneLayer.values[i][j];
                if (zone != zoneLayer.nullValue) {
                    uniqueZones.putIfAbsent(zone, new HashSet<>());
                    uniqueZones.get(zone).add(values[i][j]);
                }
            }
        }
    
        // Calculate the majority value for each zone
        Map<Double, Double> majorityValues = new HashMap<>();
        for (Map.Entry<Double, Set<Double>> entry : uniqueZones.entrySet()) {
            double zone = entry.getKey();
            List<Double> zoneValues = new ArrayList<>(entry.getValue());
            Map<Double, Integer> valueCounts = new HashMap<>();
    
            // Count the occurrences of each value in the zone
            for (double value : zoneValues) {
                valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
            }
    
            // Determine the majority value
            double majorityValue = zoneValues.get(0);
            int majorityCount = 0;
            for (Map.Entry<Double, Integer> countEntry : valueCounts.entrySet()) {
                double value = countEntry.getKey();
                int count = countEntry.getValue();
                if (count > majorityCount || (count == majorityCount && value < majorityValue)) {
                    majorityValue = value;
                    majorityCount = count;
                }
            }
            majorityValues.put(zone, majorityValue);
        }
    
        // Assign the majority value to each cell in the output layer
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                double zone = zoneLayer.values[i][j];
                if (zone != zoneLayer.nullValue) {
                    zoneOutputLayer.values[i][j] = majorityValues.getOrDefault(zone, nullValue);
                } else {
                    zoneOutputLayer.values[i][j] = nullValue;
                }
            }
        }
    
        return zoneOutputLayer;
    }

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

}
