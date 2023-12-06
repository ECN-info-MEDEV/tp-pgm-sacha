package org.centrale.medev_tp3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author bebom
 */
public class ImageThresholding {
     // Fonction pour appliquer le seuillage à une image en niveaux de gris
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
    public static void main(String[] args) {
        try {
            // Remplacez le chemin d'accès par le chemin de votre image
            String inputFilePath = "path/to/your/input/image.pgm";

            // Lecture de l'image PGM
            BufferedImage pgmImage = ImageIO.read(new File(inputFilePath));

            // Seuillage avec un seuil de 128 (vous pouvez ajuster selon vos besoins)
            int thresholdValue = 128;
            BufferedImage thresholdedImage = applyThreshold(pgmImage, thresholdValue);

            // Enregistrement de l'image seuillée
            ImageIO.write(thresholdedImage, "PGM", new File("path/to/your/output/thresholded_image.pgm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
