package org.centrale.medev_tp3;

import java.io.IOException;
import javax.swing.SwingUtilities;

public class TestFile {
    public static void main(String[] args) throws IOException {

        String dir = "C:/Users/sacha/Desktop/MEDEV/TP3/ImagesTestPGM";
        String input1 = dir + "/coins.pgm";
        String output1 = dir + "/coins_output.pgm";
        String outputHistogram1 = dir + "coins_output_histogram.pgm";

        int[][] image1 = PGM_tools.readPGM(input1);

        System.out.println("\n-------- READ IMAGE ----------\n");

        // Display image properties
        System.out.println("Image width: " + image1[0].length);
        System.out.println("Image height: " + image1.length);
        System.out.println("");

        // Display the first 10x10 pixel chunk of the image
        for (int i = 0; i < Math.min(10, image1.length); i++) {
            for (int j = 0; j < Math.min(10, image1[i].length); j++) {
                System.out.print(image1[i][j] + " ");
            }
            System.out.println();
        }
        
        System.out.println("\n-------- DIFFERENCE BETWEEN 2 IMAGES ----------\n");

        int [][] test1 = {{1,0,0,0,-1}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}};
        int [][] test2 = {{0,0,0,0,0}, {0,0,0,0,0}, {0,-5,9,-5,0}, {0,0,0,0,0}, {1,0,0,0,-1}};        int [][] diff = PGM_tools.diffImg(test1,test2);
        PGM_tools.showImg(diff);
        
        System.out.println("\n-------- VISUALIZATION ----------\n");

        // Run the GUI codes on Event-Dispatching thread for thread safety
        PGM_visual display1 = new PGM_visual(image1);
        display1.showImg();
    }
}
