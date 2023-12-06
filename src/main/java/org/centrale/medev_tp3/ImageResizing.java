package org.centrale.medev_tp3;

/**
 *
 * @author bebom
 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizing {

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

    public static void main(String[] args) {
        try {
            // Remplacez les chemins d'accès par les chemins de vos images
            String inputFilePath = "path/to/your/input/image.pgm";

            // Lecture de l'image PGM
            BufferedImage originalImage = ImageIO.read(new File(inputFilePath));

            // Agrandissement de l'image (par exemple, double de la taille)
            int newWidth = originalImage.getWidth() * 2;
            int newHeight = originalImage.getHeight() * 2;
            BufferedImage enlargedImage = enlargeImage(originalImage, newWidth, newHeight);

            // Réduction de l'image (par exemple, moitié de la taille)
            int reducedWidth = originalImage.getWidth() / 2;
            int reducedHeight = originalImage.getHeight() / 2;
            BufferedImage reducedImage = reduceImage(originalImage, reducedWidth, reducedHeight);

            // Enregistrement des images agrandies et réduites
            ImageIO.write(enlargedImage, "PNG", new File("path/to/your/output/enlarged_image.png"));
            ImageIO.write(reducedImage, "PNG", new File("path/to/your/output/reduced_image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

