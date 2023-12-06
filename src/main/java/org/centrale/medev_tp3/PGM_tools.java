package org.centrale.medev_tp3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PGM_tools {

    public PGM_tools() {
    }

    public static int[][] readPGM(String filePath) throws IOException {
        int[][] pixelValues = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the PGM header
            String magicNumber = reader.readLine().trim();
            if (!magicNumber.equals("P2")) {
                throw new IOException("Invalid PGM file format. Expected P2.");
            }
            // Skip comments
            String line;
            do {
                line = reader.readLine().trim();
            } while (line.startsWith("#"));
            String[] dimensions = line.split("\\s+");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);
            int maxPixelValue = Integer.parseInt(reader.readLine().trim());
            pixelValues = new int[height][width];

            // Read all pixel values linearly, irrespective of newlines
            List<Integer> allPixels = new ArrayList<>();
            while ((line = reader.readLine()) != null && allPixels.size() < width * height) {
                String[] values = line.trim().split("\\s+");
                for (String value : values) {
                    if (!value.isEmpty()) { // Avoid empty strings that can arise from consecutive spaces
                        allPixels.add(Integer.valueOf(value));
                    }
                }
            }

            // Check if all pixels were read
            if (allPixels.size() != width * height) {
                throw new IOException("The PGM file does not contain the expected number of pixels.");
            }

            // Populate the 2D array with the pixel values
            for (int i = 0, pixelIndex = 0; i < height; i++) {
                for (int j = 0; j < width; j++, pixelIndex++) {
                    pixelValues[i][j] = allPixels.get(pixelIndex);
                }
            }
        } catch (Exception e) {
            System.out.println("Couldn't read the PGM image. Return NULL array.");
            e.printStackTrace();
            pixelValues = null; 
        }
        return pixelValues;
    }
    
        // Function to calculate the histogram of a 2D array of pixel values
    public static int[] histogram(int[][] pixelValues) {
        int width = pixelValues[0].length;
        int height = pixelValues.length;

        int[] histogram = new int[256]; // For grayscale pixel values from 0 to 255

        // Traverse each pixel of the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = pixelValues[y][x];

                // Increment the histogram counter for this pixel value
                histogram[pixelValue]++;
            }
        }

        return histogram;
    }

    public static void writePGM(String filePath, int[][] pixelData, int maxPixelValue) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Header
            writer.write("P2");
            writer.newLine();
            writer.write("# Optional Comment");
            writer.newLine();
            writer.write(pixelData[0].length + " " + pixelData.length);
            writer.newLine();
            writer.write(maxPixelValue + "");
            writer.newLine();

            // Pixel data
            for (int[] row : pixelData) {
                for (int value : row) {
                    writer.write(value + " ");
                }
                writer.newLine();
            }
        }
    }

    public static int[][] applyThreshold(int[][] pixelValues, int threshold) {
        int height = pixelValues.length;
        int width = pixelValues[0].length;

        int[][] thresholdedValues = new int[height][width];

        // Traverse each pixel of the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = pixelValues[y][x];

                // Apply thresholding
                int newPixelValue = (pixelValue < threshold) ? 0 : 255;

                // Set the new pixel value on the thresholded image
                thresholdedValues[y][x] = newPixelValue;
            }
        }
        return thresholdedValues;
    }

    // Fonction utilitaire pour obtenir la valeur maximale dans un tableau
    private static int getMaxVal(int[] array) {
        int maxValue = array[0];
        for (int value : array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
    
    public static int[][] diffImg(int[][] image1, int[][] image2) {
        int height = image1.length;
        int width = image1[0].length;
        int[][] differenceImage = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue1 = image1[y][x];
                int pixelValue2 = image2[y][x];
                differenceImage[y][x] = Math.abs(pixelValue1 - pixelValue2);
            }
        }
        return differenceImage;
    }
    
    public static void showImg(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}


