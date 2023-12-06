package org.centrale.medev_tp3;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author bebom
 */
public class ImageCheck {

    public static void main(String[] args) {
        String filePath = "C:/Users/bebom/OneDrive - Universidad Autonoma de San Luis Potosi - UASLP/ECN/Segundo Ano/Segundo Bimestre/MEDEV/TP3/ImagesTestPGM/baboon.pgm";
        File file = new File(filePath);
        if (file.exists()) {
            // Verifica si el archivo tiene permisos de lectura
            if (file.canRead()) {
                System.out.println("El archivo tiene permisos de lectura.");
            } else {
                System.out.println("El archivo no tiene permisos de lectura.");
            }
        } else {
            System.out.println("El archivo no existe.");
        }
        try {
            BufferedImage image = readPGMImage(filePath);

            if (image != null) {
                printImageContents(image);
            } else {
                System.out.println("La imagen es nula.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Crear un lector de archivos
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;

            // Leer y mostrar cada línea del archivo
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Cerrar el lector de archivos
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage readPGMImage(String filePath) throws IOException {
        File file = new File(filePath);
        return ImageIO.read(file);
    }

    private static void printImageContents(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        System.out.println("Contenido de la imagen:");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = image.getRGB(x, y) & 0xFF;
                System.out.print(pixelValue + " ");
            }
            System.out.println();
        }
    }
}
