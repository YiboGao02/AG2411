package se.kth.ag2411.project;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Map Algebra Operations!");
        System.out.println("Choose an operation type: local, focal, or zonal");
        String operationType = scanner.nextLine().toLowerCase();

        switch (operationType) {
            case "local":
                performLocalOperation(scanner);
                break;
            case "focal":
                performFocalOperation(scanner);
                break;
            case "zonal":
                performZonalOperation(scanner);
                break;
            default:
                System.out.println("Invalid operation type. Please choose 'local', 'focal', or 'zonal'.");
        }

        scanner.close();
    }

    private static void performLocalOperation(Scanner scanner) {
        System.out.println("Enter the file path for the layer:");
        String filePath = scanner.nextLine();

        Layer layer = new Layer("Local Layer", filePath);
        System.out.println("Choose a local operation: localSum, localDifference, localProduct, localMean, localVariety");
        String localOperation = scanner.nextLine();

        System.out.println("Enter the file path for the second layer (if required):");
        String secondFilePath = scanner.nextLine();
        Layer secondLayer = new Layer("Second Layer", secondFilePath);

        Layer resultLayer = null;
        switch (localOperation) {
            case "localSum":
                resultLayer = layer.localSum(secondLayer, "Local Sum Result");
                break;
            case "localDifference":
                resultLayer = layer.localDifference(secondLayer, "Local Difference Result");
                break;
            case "localProduct":
                resultLayer = layer.localProduct(secondLayer, "Local Product Result");
                break;
            case "localMean":
                resultLayer = layer.localMean(secondLayer, "Local Mean Result");
                break;
            case "localVariety":
                resultLayer = layer.localVariety(secondLayer, "Local Variety Result");
                break;
            default:
                System.out.println("Invalid local operation selected.");
                return;
        }

        saveResultLayer(resultLayer);
    }

    private static void performFocalOperation(Scanner scanner) {
        System.out.println("Enter the file path for the layer:");
        String filePath = scanner.nextLine();

        Layer layer = new Layer("Focal Layer", filePath);
        System.out.println("Choose a focal operation: focalSum, focalMean, focalVariety");
        String focalOperation = scanner.nextLine();

        System.out.println("Enter the radius of the neighborhood:");
        int radius = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over
        System.out.println("Enter neighborhood shape (circle or rectangle):");
        String shape = scanner.nextLine();

        // Placeholder for focal operations
        Layer resultLayer = null;
        // Implement focalSum, focalMean, and focalVariety methods in the Layer class
        switch (focalOperation) {
            case "focalSum":
                // resultLayer = layer.focalSum(radius, shape);
                break;
            case "focalMean":
                // resultLayer = layer.focalMean(radius, shape);
                break;
            case "focalVariety":
                // resultLayer = layer.focalVariety(radius, shape);
                break;
            default:
                System.out.println("Invalid focal operation selected.");
                return;
        }

        saveResultLayer(resultLayer);
    }

    private static void performZonalOperation(Scanner scanner) {
        System.out.println("Enter the file path for the value layer:");
        String valueLayerPath = scanner.nextLine();
        Layer valueLayer = new Layer("Value Layer", valueLayerPath);

        System.out.println("Enter the file path for the zone layer:");
        String zoneLayerPath = scanner.nextLine();
        Layer zoneLayer = new Layer("Zone Layer", zoneLayerPath);

        System.out.println("Choose a zonal operation: zonalSum, zonalMean, zonalVariety, zonalMinimum, zonalMaximum, zonalMajority");
        String zonalOperation = scanner.nextLine();

        // Placeholder for zonal operations
        Layer resultLayer = null;
        // Implement zonal operations in the Layer class
        switch (zonalOperation) {
            case "zonalSum":
                // resultLayer = valueLayer.zonalSum(zoneLayer);
                break;
            case "zonalMean":
                // resultLayer = valueLayer.zonalMean(zoneLayer);
                break;
            case "zonalVariety":
                // resultLayer = valueLayer.zonalVariety(zoneLayer);
                break;
            case "zonalMinimum":
                // resultLayer = valueLayer.zonalMinimum(zoneLayer);
                break;
            case "zonalMaximum":
                // resultLayer = valueLayer.zonalMaximum(zoneLayer);
                break;
            case "zonalMajority":
                // resultLayer = valueLayer.zonalMajority(zoneLayer);
                break;
            default:
                System.out.println("Invalid zonal operation selected.");
                return;
        }

        saveResultLayer(resultLayer);
    }
    


    private static void saveResultLayer(Layer resultLayer) {
        if (resultLayer != null) {
            System.out.println("Enter the file path to save the result layer:");
            Scanner scanner = new Scanner(System.in);
            String outputPath = scanner.nextLine();
            resultLayer.save(outputPath);
            System.out.println("Result layer saved to: " + outputPath);
        } else {
            System.out.println("No result layer generated.");
        }
    }
}

