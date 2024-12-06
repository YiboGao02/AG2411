package se.kth.ag2411.project;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class LayerOperationsTest {
    public static void main(String[] args) {
        // Load test layers from files
        String elevationFileName = "data\\development.asc";
        String vegetationFileName = "data\\vegetation.asc";
        Layer elevationLayer = new Layer("Elevation", elevationFileName);
        Layer vegetationLayer = new Layer("Vegetation", vegetationFileName);

        double scale = 4.0;

        // Test localSum
        Layer localSumLayer = elevationLayer.localSum(elevationLayer, "LocalSumLayer");
        BufferedImage localSumImage = localSumLayer.toImage();
        displayLayer(localSumImage, "Local Sum Result", localSumLayer, scale);

        // Test localDifference
        Layer localDifferenceLayer = elevationLayer.localDifference(vegetationLayer, "LocalDifferenceLayer");
        BufferedImage localDifferenceImage = localDifferenceLayer.toImage();
        displayLayer(localDifferenceImage, "Local Difference Result", localDifferenceLayer, scale);

        // Test localProduct
        Layer localProductLayer = elevationLayer.localProduct(vegetationLayer, "LocalProductLayer");
        BufferedImage localProductImage = localProductLayer.toImage();
        displayLayer(localProductImage, "Local Product Result", localProductLayer, scale);

        // Test localMean
        Layer localMeanLayer = elevationLayer.localMean(vegetationLayer, "LocalMeanLayer");
        BufferedImage localMeanImage = localMeanLayer.toImage();
        displayLayer(localMeanImage, "Local Mean Result", localMeanLayer, scale);

        // Test localVariety
        Layer localVarietyLayer = elevationLayer.localVariety(vegetationLayer, "LocalVarietyLayer");
        BufferedImage localVarietyImage = localVarietyLayer.toImage();
        displayLayer(localVarietyImage, "Local Variety Result", localVarietyLayer, scale);
    }

    private static void displayLayer(BufferedImage image, String title, Layer layer, double scale) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) (layer.nCols * scale), (int) (layer.nRows * scale));
        MapPanel mapPanel = new MapPanel(image, (int) scale);
        frame.add(mapPanel);
        frame.setVisible(true);
    }
}

