package org.centrale.medev_tp3;

/**
 *
 * @author sacha
 */
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;

public class PGM_visual extends JFrame {

    public PGM_visual() {
        this.setTitle("");
    }
    
    private BufferedImage matrixToBI(int[][] matrix) {
        int width = matrix[0].length;
        int height = matrix.length;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = bufferedImage.getRaster();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = matrix[y][x];
                raster.setSample(x, y, 0, pixelValue); 
            }
        }
        return bufferedImage;
    }
    
    public void showImg(int[][] img){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage image = matrixToBI(img);
        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);
        add(label);
        pack(); 
        setVisible(true);
    }
    
    public void showPGM(String filePath) throws IOException{
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BufferedImage image = matrixToBI(PGM_tools.readPGM(filePath));
        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);
        add(label);
        pack(); 
        setVisible(true);
    }
}
