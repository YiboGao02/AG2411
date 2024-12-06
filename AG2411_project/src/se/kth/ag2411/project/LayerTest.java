package se.kth.ag2411.project;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class LayerTest {
    public static void main(String[] args) {
        // 从文件中加载测试图层
        String elevationFileName = "data\\elevation.asc";
        String vegetationFileName = "data\\vegetation.asc";
        Layer elevationLayer = new Layer("Elevation", elevationFileName);
        Layer vegetationLayer = new Layer("Vegetation", vegetationFileName);

        double scale = 4.0;

        // 测试 localSum
        Layer localSumLayer = elevationLayer.localSum(vegetationLayer, "LocalSumLayer");
        BufferedImage localSumImage = localSumLayer.toImage();
        displayLayer(localSumImage, "Local Sum Result", localSumLayer, scale);

        // 测试 focalSum
        Layer focalSumLayer = elevationLayer.focalSum(1, true, "FocalSumLayer");
        BufferedImage focalSumImage = focalSumLayer.toImage();
        displayLayer(focalSumImage, "Focal Sum Result", focalSumLayer, scale);

        // 测试 zonalSum
        Layer zonalSumLayer = elevationLayer.zonalSum(vegetationLayer, "ZonalSumLayer");
        BufferedImage zonalSumImage = zonalSumLayer.toImage();
        displayLayer(zonalSumImage, "Zonal Sum Result", zonalSumLayer, scale);
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
