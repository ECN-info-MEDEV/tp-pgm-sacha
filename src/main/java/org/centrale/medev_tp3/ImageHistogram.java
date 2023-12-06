package org.centrale.medev_tp3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author bebom
 */
public class ImageHistogram {

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

    public static void main(String[] args) {
        try {
            // Remplacez le chemin d'accès par le chemin de votre image
            String inputFilePath = "path/to/your/input/image.pgm";

            // Lecture de l'image PGM
            BufferedImage pgmImage = ImageIO.read(new File(inputFilePath));

            // Calcul de l'histogramme
            int[] histogram = calculateHistogram(pgmImage);

            // Création de l'image représentant l'histogramme
            BufferedImage histogramImage = createHistogramImage(histogram);

            // Enregistrement de l'image représentant l'histogramme
            ImageIO.write(histogramImage, "PGM", new File("path/to/your/output/histogram.pgm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}