package se.kth.ag2411.mapalgebra;

import javax.swing.*;
import java.awt.image.BufferedImage;
//import java.awt.Dimension;

public class Ex03 {

    public static void main(String[] args) {

    /* 
        args = new String[]{
            "localSum",
            "data\\elevation.asc",
            "data\\vegetation.asc",
            "output.asc"
        };
    */
    /*
        args = new String[]{
            "focalVariety",
            "data\\vegetation.asc",
            "output.asc",
            "5",
            "true"
        };
    */
    args = new String[]{
        "zonalMinimum",
        "data\\elevation.asc",
        "data\\vegetation.asc",
        "output.asc"
    };


        // 检查命令行参数数量
        if (args.length < 3) {
            System.out.println("Usage: Ex03 <operation> <inputFile1> [<inputFile2>] <outputFile> [additional parameters]");
            return;
        }

        // 获取命令行参数
        String operation = args[0];
        String inputFile1 = args[1];
        String outputFile = args[args.length - 1];
        Layer inLayer1 = new Layer("InputLayer1", inputFile1);

        Layer outLayer = null;
        
        if (operation.equals("localSum")) {
            if (args.length < 4) {
                System.out.println("Error: localSum operation requires two input files.");
                return;
            }
            String inputFile2 = args[2];
            Layer inLayer2 = new Layer("InputLayer2", inputFile2);
            outLayer = inLayer1.localSum(inLayer2, "LocalSumOutput");
            visualize(outLayer);

        } else if (operation.equals("focalVariety")) {
            if (args.length < 5) {
                System.out.println("Error: focalVariety operation requires radius and shape parameters.");
                return;
            }
            int radius = Integer.parseInt(args[3]);
            boolean isSquare = Boolean.parseBoolean(args[4]);
            outLayer = inLayer1.focalVariety(radius, isSquare, "FocalVarietyOutput");
            visualize(outLayer);

        } else if (operation.equals("zonalMinimum")) {
            if (args.length < 4) {
                System.out.println("Error: zonalMinimum operation requires a zone layer input file.");
                return;
            }
            String zoneFile = args[2];
            Layer zoneLayer = new Layer("ZoneLayer", zoneFile);
            outLayer = inLayer1.zonalMinimum(zoneLayer, "ZonalMinimumOutput");
            visualize(outLayer);

        } else {
            System.out.println("Error: " + operation + " is not a supported operation.");
            return;
        }

        // 保存输出图层
        if (outLayer != null) {
            outLayer.save(outputFile);
            System.out.println("Operation " + operation + " completed successfully. Output saved to " + outputFile);
        }
    }

    // 可视化方法
    private static void visualize(Layer layer) {
        BufferedImage image = layer.toImage();
        JFrame frame = new JFrame("Map Algebra Result: " + layer.name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) (layer.nCols * 5), (int) (layer.nRows * 5)); // 根据缩放比例设置窗口大小
        //frame.setSize(new Dimension(image.getWidth(), image.getHeight()));
        MapPanel panel = new MapPanel(image, 5);
        frame.add(panel);
        frame.setVisible(true);
    }
}
