package se.kth.ag2411.project;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LayerTest {
    public static void main(String[] args) {
        // 从文件中加载测试图层
        Layer elevationLayer = new Layer("Elevation", "AG2411_project\\data\\elevation.asc");
        Layer vegetationLayer = new Layer("Vegetation", "AG2411_project\\data\\vegetation.asc");

        // 测试 localSum
        Layer localSumLayer = elevationLayer.localSum(vegetationLayer, "LocalSumLayer");
        System.out.println("Local Sum Result:");
        displayLayer(localSumLayer, "Local Sum Result");

        // 测试 focalSum
        Layer focalSumLayer = elevationLayer.focalSum(1, true, "FocalSumLayer");
        System.out.println("Focal Sum Result:");
        displayLayer(focalSumLayer, "Focal Sum Result");

        // 测试 zonalSum
        Layer zonalSumLayer = elevationLayer.zonalSum(vegetationLayer, "ZonalSumLayer");
        System.out.println("Zonal Sum Result:");
        displayLayer(zonalSumLayer, "Zonal Sum Result");
    }

    private static void printLayerValues(Layer layer) {
        for (int i = 0; i < layer.nRows; i++) {
            for (int j = 0; j < layer.nCols; j++) {
                System.out.print(layer.values[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void displayLayer(Layer layer, String title) {
        BufferedImage image = layer.toImage();
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(image.getWidth(), image.getHeight());
        frame.add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);
    }
}
