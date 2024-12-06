package se.kth.ag2411.project;

import java.awt.image.BufferedImage; 
import java.util.Random;
import javax.swing.*;
//import se.kth.ag2411.project.Layer;
//import se.kth.ag2411.mapalgebra.MapPanel;

public class Game2{
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        System.out.println("Welcome to the Map Algebra Game!");
        System.out.println("You will load two layers (Zone Layer and Value Layer) and guess the operation performed.");
        System.out.println("Ready to play? (yes/no)");

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String response = scanner.nextLine();
        if (!response.equalsIgnoreCase("yes")) {
            System.out.println("Goodbye!");
            return;
        }

        System.out.println("Please enter the path to the Zone Layer:");
        String zoneLayerPath = scanner.nextLine();
        //System.out.println("Please enter a name for the Zone Layer:");
       // String zoneLayerName = scanner.nextLine();
        //Layer zoneLayer = new Layer(zoneLayerName, zoneLayerPath);
        Layer zoneLayer = new Layer("ZoneLayer", zoneLayerPath);

        System.out.println("Please enter the path to the Value Layer:");
        String valueLayerPath = scanner.nextLine();
       // System.out.println("Please enter a name for the Value Layer:");
       // String valueLayerName = scanner.nextLine();
       //Layer valueLayer = new Layer(valueLayerName, valueLayerPath);
        Layer valueLayer = new Layer("valueLayer", valueLayerPath);


     // Extend the operations array to include focal and zonal operations
        String[] operations = {
            "localSum-zone", "localDifference-zone", "localProduct-zone", "localMean-zone", "localVariety-zone",
            "localSum-value", "localDifference-value", "localProduct-value", "localMean-value", "localVariety-value",
            "focalSum", "focalMean", "focalVariety",
            "zonalSum", "zonalMean", "zonalVariety", "zonalMinimum", "zonalMaximum", "zonalMajority"
        };
        Random random = new Random();
        String chosenOperation = operations[random.nextInt(operations.length)];

        Layer resultLayer = null;

  
        switch (chosenOperation) {
            // Local operations - zone
            case "localSum-zone":
                resultLayer = zoneLayer.localSum(zoneLayer, "Result - Local Sum-zone");
                break;
            case "localDifference-zone":
                resultLayer = zoneLayer.localDifference(zoneLayer, "Result - Local Difference-zone");
                break;
            case "localProduct-zone":
                resultLayer = zoneLayer.localProduct(zoneLayer, "Result - Local Product-zone");
                break;
            case "localMean-zone":
                resultLayer = zoneLayer.localMean(zoneLayer, "Result - Local Mean-zone");
                break;
            case "localVariety-zone":
                resultLayer = zoneLayer.localVariety(zoneLayer, "Result - Local Variety-zone");
                break;

            // Local operations - value
            case "localSum-value":
                resultLayer = valueLayer.localSum(valueLayer, "Result - Local Sum-value");
                break;
            case "localDifference-value":
                resultLayer = valueLayer.localDifference(valueLayer, "Result - Local Difference-value");
                break;
            case "localProduct-value":
                resultLayer = valueLayer.localProduct(valueLayer, "Result - Local Product-value");
                break;
            case "localMean-value":
                resultLayer = valueLayer.localMean(valueLayer, "Result - Local Mean-value");
                break;
            case "localVariety-value":
                resultLayer = valueLayer.localVariety(valueLayer, "Result - Local Variety-value");
                break;

            // Focal operations
            case "focalSum":
                resultLayer = zoneLayer.focalSum(1, true, "Result - Focal Sum");
                break;
            case "focalMean":
                resultLayer = zoneLayer.focalMean(1, true, "Result - Focal Mean");
                break;
            case "focalVariety":
                resultLayer = zoneLayer.focalVariety(1, true, "Result - Focal Variety");
                break;

            // Zonal operations
            case "zonalSum":
                resultLayer = valueLayer.zonalSum(zoneLayer, "Result - Zonal Sum");
                break;
            case "zonalMean":
                resultLayer = valueLayer.zonalMean(zoneLayer, "Result - Zonal Mean");
                break;
            case "zonalVariety":
                resultLayer = valueLayer.zonalVariety(zoneLayer, "Result - Zonal Variety");
                break;
            case "zonalMinimum":
                resultLayer = valueLayer.zonalMinimum(zoneLayer, "Result - Zonal Minimum");
                break;
            case "zonalMaximum":
                resultLayer = valueLayer.zonalMaximum(zoneLayer, "Result - Zonal Maximum");
                break;
            case "zonalMajority":
                resultLayer = valueLayer.zonalMajority(zoneLayer, "Result - Zonal Majority");
                break;

            default:
                throw new IllegalArgumentException("Unknown operation: " + chosenOperation);
        }


        if (resultLayer != null) {
            BufferedImage resultImage = resultLayer.toImage();
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Map Algebra Result");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                MapPanel panel = new MapPanel(resultImage, 2);
                frame.add(panel);
                frame.setVisible(true);
            });
        }

        String[] alternatives = new String[3];
        alternatives[0] = chosenOperation;
        for (int i = 1; i < 3; i++) {
            String randomOp;
            do {
                randomOp = operations[random.nextInt(operations.length)];
            } while (randomOp.equals(alternatives[0]) || (i == 2 && randomOp.equals(alternatives[1])));
            alternatives[i] = randomOp;
        }

        for (int i = 0; i < alternatives.length; i++) {
            int swapIndex = random.nextInt(alternatives.length);
            String temp = alternatives[i];
            alternatives[i] = alternatives[swapIndex];
            alternatives[swapIndex] = temp;
        }

        System.out.println("What operation was performed?");
        for (int i = 0; i < alternatives.length; i++) {
            System.out.println((i + 1) + ": " + alternatives[i]);
        }

        int choice = scanner.nextInt();
        if (choice > 0 && choice <= alternatives.length && alternatives[choice - 1].equals(chosenOperation)) {
            System.out.println("Correct! The operation was " + chosenOperation);
        } else {
            System.out.println("Wrong! The correct operation was " + chosenOperation);
        }

        scanner.close();
    }
}

///Users/melkerferdfelt/Library/CloudStorage/OneDrive-KTH/KTH_PÅ_DATOR/ämnen/AG2411-Algorith/project/Project/src/Project/
