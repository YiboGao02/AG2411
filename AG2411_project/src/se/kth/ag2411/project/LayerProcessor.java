package se.kth.ag2411.project;

import java.io.File;

public class LayerProcessor {

    /**
     * Processes the selected operation and associated file paths.
     *
     * @param operation The selected operation (e.g., "Local Sum").
     * @param filePaths Array of file paths for input layers.
     * @param outputFilePath Path to save the resulting layer.
     * @param radius Radius for focal operations (if applicable).
     * @param isSquare Shape for focal operations (true for square, false for circle).
     */
    public void processLayer(String operation, String[] filePaths, String outputFilePath, Integer radius, Boolean isSquare) {
        try {
            if (filePaths.length == 0 || filePaths[0] == null || filePaths[0].isEmpty()) {
                throw new IllegalArgumentException("Input file paths cannot be empty.");
            }
    
            // Load layers
            Layer layer1 = new Layer("Layer1", filePaths[0]);
            Layer layer2 = null;
    
            if (filePaths.length > 1 && filePaths[1] != null && !filePaths[1].isEmpty()) {
                layer2 = new Layer("Layer2", filePaths[1]);
            }
    
            // Process based on operation
            Layer resultLayer = null;
    
            switch (operation) {
                // Local Operations
                case "Local Sum":
                case "Local Difference":
                case "Local Product":
                case "Local Mean":
                case "Local Variety":
                    if (layer2 == null) {
                        throw new IllegalArgumentException(operation + " requires two layers.");
                    }
                    resultLayer = switch (operation) {
                        case "Local Sum" -> layer1.localSum(layer2, "LocalSumResult");
                        case "Local Difference" -> layer1.localDifference(layer2, "LocalDifferenceResult");
                        case "Local Product" -> layer1.localProduct(layer2, "LocalProductResult");
                        case "Local Mean" -> layer1.localMean(layer2, "LocalMeanResult");
                        case "Local Variety" -> layer1.localVariety(layer2, "LocalVarietyResult");
                        default -> throw new IllegalArgumentException("Unsupported local operation.");
                    };
                    break;
    
                // Focal Operations
                case "Focal Sum":
                case "Focal Mean":
                case "Focal Variety":
                case "Focal Maximum":
                case "Focal Minimum":
                    if (radius == null || isSquare == null) {
                        throw new IllegalArgumentException("Focal operations require radius and shape.");
                    }
                    resultLayer = switch (operation) {
                        case "Focal Sum" -> layer1.focalSum(radius, isSquare, "FocalSumResult");
                        case "Focal Mean" -> layer1.focalMean(radius, isSquare, "FocalMeanResult");
                        case "Focal Variety" -> layer1.focalVariety(radius, isSquare, "FocalVarietyResult");
                        case "Focal Maximum" -> layer1.focalMaximum(radius, isSquare, "FocalMaximumResult");
                        case "Focal Minimum" -> layer1.focalMinimum(radius, isSquare, "FocalMinimumResult");
                        default -> throw new IllegalArgumentException("Unsupported focal operation.");
                    };
                    break;
    
                // Zonal Operations
                case "Zonal Sum":
                case "Zonal Mean":
                case "Zonal Variety":
                case "Zonal Maximum":
                case "Zonal Minimum":
                case "Zonal Majority":
                    if (layer2 == null) {
                        throw new IllegalArgumentException(operation + " requires two layers.");
                    }
                    resultLayer = switch (operation) {
                        case "Zonal Sum" -> layer1.zonalSum(layer2, "ZonalSumResult");
                        case "Zonal Mean" -> layer1.zonalMean(layer2, "ZonalMeanResult");
                        case "Zonal Variety" -> layer1.zonalVariety(layer2, "ZonalVarietyResult");
                        case "Zonal Maximum" -> layer1.zonalMaximum(layer2, "ZonalMaximumResult");
                        case "Zonal Minimum" -> layer1.zonalMinimum(layer2, "ZonalMinimumResult");
                        case "Zonal Majority" -> layer1.zonalMajority(layer2, "ZonalMajorityResult");
                        default -> throw new IllegalArgumentException("Unsupported zonal operation.");
                    };
                    break;
    
                default:
                    throw new UnsupportedOperationException("Operation not supported: " + operation);
            }
    
            // Save the result
            if (resultLayer != null) {
                resultLayer.save(outputFilePath);
                System.out.println("Result saved to: " + outputFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
