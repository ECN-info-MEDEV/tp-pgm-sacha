package org.centrale.medev_tp3;

import java.io.IOException;

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

        //PGM_tools.showImg(image1);
        
        int [][] test1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int [][] test2 = {{2, 2, 4}, {4, 15, 6}, {6, 8, 8}};
        int [][] diff = PGM_tools.diffImg(test1,test2);
        PGM_tools.showImg(diff);
    }
}
