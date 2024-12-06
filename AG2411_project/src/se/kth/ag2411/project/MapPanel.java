package se.kth.ag2411.project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class MapPanel extends JPanel{

    public BufferedImage image;
    public int scale;

    public MapPanel(BufferedImage image, int scale) {

        super();

        this.image = image;
        this.scale = scale;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth() * scale, image.getHeight() * scale, null);

    }

}
