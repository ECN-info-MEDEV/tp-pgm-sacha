package org.centrale.medev_tp3;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bebom
 */
public class TestFile {

    public static void main(String[] args) throws IOException {
        String directory = "C:/Users/sacha/Desktop/MEDEV/TP3/ImagesTestPGM";
        String inputFilePath = directory + "/coins.pgm";
        String outputFilePath = directory + "/coins_output.pgm";
        String outputHistogram = directory + "coins_output_histogram.pgm";
        BufferedImage image1 = readPGMImage(inputFilePath);
        //Verificacion de la image1
        
        System.out.println("--------READ IMAGE----------");
        
        //System.out.println(image1);
        System.out.println("Image Width: " + image1.getWidth());
        System.out.println("Image Height: " + image1.getHeight());
        System.out.println("Image color type: " + image1.getType());

        // Écriture de l'image PGM traitée dans un nouveau fichier
        //String filePath, int[][] pixelData, int width, int height, int maxPixelValue
        //writePGMImage(image1, outputFilePath);

    }
    // Fonction pour lire une image PGM à partir d'un fichier

    public static BufferedImage readPGMImage(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        // Read the PGM header
        String magicNumber = reader.readLine().trim();
        if (!magicNumber.equals("P2")) {
            throw new IOException("Invalid PGM file format. Expected P2.");
        }

        // Skip comments if any
        String line;
        do {
            line = reader.readLine().trim();
        } while (line.startsWith("#"));

        // Read image dimensions and max pixel value
        String[] dimensions = line.split("\\s+");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);

        int maxPixelValue = Integer.parseInt(reader.readLine().trim());

        // Read pixel values
        List<Integer> pixelValues = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] values = line.split("\\s+");
            for (String value : values) {
                pixelValues.add(Integer.parseInt(value));
            }
        }

        // Create BufferedImage and set pixel values
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int pixelIndex = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = pixelValues.get(pixelIndex++);
                int rgb = (pixelValue * 255) / maxPixelValue;  // Scale to 0-255
                image.setRGB(x, y, rgb << 16 | rgb << 8 | rgb);
            }
        }

        reader.close();
        return image;
    }

    // Fonction pour écrire une image PGM dans un fichier
    public static void writePGMImage(String filePath, int[][] pixelData, int width, int height, int maxPixelValue) throws  IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        // Encabezado PGM
        writer.write("P2");
        writer.newLine();
        writer.write("# Comentario opcional");
        writer.newLine();
        writer.write(width + " " + height);
        writer.newLine();
        writer.write(maxPixelValue + "");
        writer.newLine();

        // Datos de píxeles
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                writer.write(pixelData[y][x] + " ");
            }
            writer.newLine();
        }

        writer.close();
    
    }

    public static BufferedImage applyThreshold(BufferedImage image, int threshold) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage thresholdedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Parcours de chaque pixel de l'image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = image.getRGB(x, y) & 0xFF; // Obtenez la valeur du niveau de gris

                // Appliquez le seuillage
                int newPixelValue = (pixelValue < threshold) ? 0 : 255;

                // Définissez la nouvelle valeur de pixel sur l'image seuillée
                thresholdedImage.setRGB(x, y, newPixelValue << 16 | newPixelValue << 8 | newPixelValue);
            }
        }
        return thresholdedImage;
    }

    // Fonction pour agrandir une image avec interpolation bilinéaire
    public static BufferedImage enlargeImage(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage enlargedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        // Facteurs de conversion
        double xRatio = (double) image.getWidth() / newWidth;
        double yRatio = (double) image.getHeight() / newHeight;

        // Parcours de chaque pixel de l'image agrandie
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                // Position originale dans l'image d'origine
                double originalX = x * xRatio;
                double originalY = y * yRatio;

                // Coordonnées des quatre coins entourant le point original
                int x1 = (int) originalX;
                int y1 = (int) originalY;
                int x2 = x1 + 1;
                int y2 = y1 + 1;

                // Interpolation bilinéaire
                double value11 = getPixelValue(image, x1, y1);
                double value12 = getPixelValue(image, x1, y2);
                double value21 = getPixelValue(image, x2, y1);
                double value22 = getPixelValue(image, x2, y2);

                double interpolatedValue = bilinearInterpolation(originalX - x1, originalY - y1, value11, value12, value21, value22);

                // Affecte la valeur interpolée au pixel dans l'image agrandie
                enlargedImage.setRGB(x, y, (int) interpolatedValue);
            }
        }

        return enlargedImage;
    }

    // Fonction pour réduire une image avec sous-échantillonnage
    public static BufferedImage reduceImage(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage reducedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        // Facteurs de conversion
        double xRatio = (double) image.getWidth() / newWidth;
        double yRatio = (double) image.getHeight() / newHeight;

        // Parcours de chaque pixel de l'image réduite
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                // Position originale dans l'image d'origine
                int originalX = (int) (x * xRatio);
                int originalY = (int) (y * yRatio);

                // Obtient la valeur du pixel original et l'affecte au pixel dans l'image réduite
                reducedImage.setRGB(x, y, image.getRGB(originalX, originalY));
            }
        }

        return reducedImage;
    }

    // Fonction pour obtenir la valeur d'un pixel dans une image (gère les bords)
    private static int getPixelValue(BufferedImage image, int x, int y) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        // Gère les bords de l'image
        x = Math.max(0, Math.min(x, imageWidth - 1));
        y = Math.max(0, Math.min(y, imageHeight - 1));

        return image.getRGB(x, y);
    }

    // Fonction d'interpolation bilinéaire
    private static double bilinearInterpolation(double x, double y, double value11, double value12, double value21, double value22) {
        return (1 - x) * (1 - y) * value11 + x * (1 - y) * value21 + (1 - x) * y * value12 + x * y * value22;
    }

    // Fonction pour calculer l'histogramme d'une image en niveaux de gris
    public static int[] calculateHistogram(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[] histogram = new int[256]; // Pour les niveaux de gris de 0 à 255

        // Parcours de chaque pixel de l'image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = image.getRGB(x, y) & 0xFF; // Obtenez la valeur du niveau de gris

                // Incrémentation du compteur d'histogramme pour ce niveau de gris
                histogram[pixelValue]++;
            }
        }

        return histogram;
    }

    // Fonction pour créer une nouvelle image PGM représentant l'histogramme
    public static BufferedImage createHistogramImage(int[] histogram) {
        int width = 256; // Largeur de l'image égale au nombre de niveaux de gris
        int height = 100; // Hauteur de l'image (vous pouvez ajuster selon vos besoins)

        BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Normalisation de l'histogramme pour ajuster la hauteur de l'image
        int maxValue = getMaxValue(histogram);
        double scale = (double) height / maxValue;

        // Dessin de l'histogramme sur l'image
        for (int i = 0; i < histogram.length; i++) {
            int lineHeight = (int) (histogram[i] * scale);
            for (int y = height - 1; y >= height - lineHeight; y--) {
                histogramImage.setRGB(i, y, 255 << 16 | 255 << 8 | 255); // Couleur blanche
            }
        }

        return histogramImage;
    }

    // Fonction utilitaire pour obtenir la valeur maximale dans un tableau
    private static int getMaxValue(int[] array) {
        int maxValue = array[0];
        for (int value : array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    public static BufferedImage calculateDifference(BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth();
        int height = image1.getHeight();

        BufferedImage differenceImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue1 = image1.getRGB(x, y);
                int pixelValue2 = image2.getRGB(x, y);

                int difference = Math.abs(pixelValue1 - pixelValue2);

                differenceImage.setRGB(x, y, difference);
            }
        }

        return differenceImage;
    }
}
