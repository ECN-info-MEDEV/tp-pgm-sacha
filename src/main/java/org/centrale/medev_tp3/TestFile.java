package org.centrale.medev_tp3;

import java.io.IOException;
import javax.swing.SwingUtilities;

public class TestFile {
    public static void main(String[] args) throws IOException {

        String dir = "C:/Users/sacha/Desktop/MEDEV/TP3/ImagesTestPGM";
        String input1 = dir + "/coins.pgm";
        String output1 = dir + "/coins_output.pgm";
        String outputHistogram1 = dir + "coins_output_histogram.pgm";

        int[][] img1 = PGM_tools.readPGM(input1);

        System.out.println("\n-------- READ IMAGE ----------\n");

        // Display image properties
        System.out.println("Image width: " + img1[0].length);
        System.out.println("Image height: " + img1.length);

        System.out.println("\n-------- VISUALIZATION ----------\n");

        PGM_visual disp1 = new PGM_visual();
        disp1.setTitle("Coins");
        disp1.showImg(img1);
        
        System.out.println("\n-------- THRESHOLD ----------\n");
        
        PGM_visual disp2 = new PGM_visual();
        int[][] tre1 = PGM_tools.applyThreshold(img1, 100);
        disp2.setTitle("Thresholded image");
        disp2.showImg(tre1);
        
        System.out.println("\n-------- DIFFERENCE ----------\n");
        
        PGM_visual disp3 = new PGM_visual();
        int[][] diff1 = PGM_tools.diffImg(img1, tre1);
        disp3.setTitle("Difference");
        disp3.showImg(diff1);
        
        System.out.println("\n-------- HISTOGRAM ----------\n");
        
        PGM_visual disp4 = new PGM_visual();
        int[] hist1 = PGM_tools.histogram(img1);
        int[][] histImg1 = PGM_tools.histogramToImage(hist1, img1[0].length, img1.length);
        disp4.setTitle("Histogramme");
        disp4.showImg(histImg1);
        
    }
}
