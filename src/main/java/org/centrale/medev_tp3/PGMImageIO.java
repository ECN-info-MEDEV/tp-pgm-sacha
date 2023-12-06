package org.centrale.medev_tp3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author bebom
 */

public class PGMImageIO {

    // Fonction pour lire une image PGM à partir d'un fichier
    public static BufferedImage readPGMImage(String filePath) {
        try {
            File file = new File(filePath);
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Fonction pour écrire une image PGM dans un fichier
    public static void writePGMImage(BufferedImage image, String filePath) {
        try {
            File file = new File(filePath);
            ImageIO.write(image, "pgm", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Exemple d'utilisation
        String inputFilePath = "C:\\Users\\bebom\\OneDrive - Universidad Autonoma de San Luis Potosi - UASLP\\ECN\\Segundo Ano\\Segundo Bimestre\\MEDEV\\TP3\\ImagesTestPGM\\baboon.pgm";
        String outputFilePath = "C:\\Users\\bebom\\OneDrive - Universidad Autonoma de San Luis Potosi - UASLP\\ECN\\Segundo Ano\\Segundo Bimestre\\MEDEV\\TP3\\ImagesTestPGM\\baboonOutPut.pgm";

        // Lecture de l'image PGM
        BufferedImage pgmImage = readPGMImage(inputFilePath);
        System.out.println("Hizo algo");
        if (pgmImage != null) {
            // Traitement de l'image (vous pouvez ajouter vos opérations ici)
            System.out.println("No hizo nada");
            // Écriture de l'image PGM traitée dans un nouveau fichier
            writePGMImage(pgmImage, outputFilePath);
        }
    }
}