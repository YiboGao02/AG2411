package se.kth.ag2411.mapalgebra;

import javax.swing.JFrame;
import java.awt.image.BufferedImage;

public class Ex02 {
    public static void main(String[] args) {

        args = new String[]{"ForATest","data\\vegetation.asc", "5", "1"};

        // get the input parameters
        String layerName = args[0];
        String fileName = args[1];
        int scale = Integer.parseInt(args[2]);

        if(args.length == 3){
            Layer layer = new Layer(layerName, fileName);
            BufferedImage grayImage = layer.toImage(); 

            //gray image
            JFrame grayFrame = new JFrame("Gray Image - " + layerName);
            grayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            grayFrame.setSize((int) (layer.actual_Col * scale), (int) (layer.actual_Row * scale)); // 根据缩放比例设置窗口大小
            MapPanel grayPanel = new MapPanel(grayImage, (int) scale);
            grayFrame.add(grayPanel);
            grayFrame.setVisible(true);
        } else{

            // check the number of input parameters
            if (args.length < 4) {
                System.out.println("<layer_name> <file_name> <scale> <values_of_interest>");
                return;
            }

            // get the interested values
            double[] valuesOfInterest = new double[args.length - 3];
            for (int i = 3; i < args.length; i++) {
                valuesOfInterest[i - 3] = Double.parseDouble(args[i]);
            }

            //
            Layer layer = new Layer(layerName, fileName);
            BufferedImage grayImage = layer.toImage(); 
            BufferedImage colorImage = layer.toImage(valuesOfInterest); 

            //gray image
            JFrame grayFrame = new JFrame("Gray Image - " + layerName);
            grayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            grayFrame.setSize((int) (layer.actual_Col * scale), (int) (layer.actual_Row * scale)); // 根据缩放比例设置窗口大小
            MapPanel grayPanel = new MapPanel(grayImage, (int) scale);
            grayFrame.add(grayPanel);
            grayFrame.setVisible(true);

            //rgb image
            JFrame colorFrame = new JFrame("Color Image - " + layerName);
            colorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            colorFrame.setSize((int) (layer.actual_Col * scale), (int) (layer.actual_Row * scale)); // 根据缩放比例设置窗口大小
            MapPanel colorPanel = new MapPanel(colorImage, (int) scale);
            colorFrame.add(colorPanel);
            colorFrame.setVisible(true);
        }


        /*
        // get the interested values
        double[] valuesOfInterest = new double[args.length - 3];
        for (int i = 3; i < args.length; i++) {
            valuesOfInterest[i - 3] = Double.parseDouble(args[i]);
        }

        //
        Layer layer = new Layer(layerName, fileName);
        BufferedImage grayImage = layer.toImage(); 
        BufferedImage colorImage = layer.toImage(valuesOfInterest); 

        //gray image
        JFrame grayFrame = new JFrame("Gray Image - " + layerName);
        grayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        grayFrame.setSize((int) (layer.actual_Col * scale), (int) (layer.actual_Row * scale)); // 根据缩放比例设置窗口大小
        MapPanel grayPanel = new MapPanel(grayImage, (int) scale);
        grayFrame.add(grayPanel);
        grayFrame.setVisible(true);

        //rgb image
        JFrame colorFrame = new JFrame("Color Image - " + layerName);
        colorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        colorFrame.setSize((int) (layer.actual_Col * scale), (int) (layer.actual_Row * scale)); // 根据缩放比例设置窗口大小
        MapPanel colorPanel = new MapPanel(colorImage, (int) scale);
        colorFrame.add(colorPanel);
        colorFrame.setVisible(true);

        */
    }
}
