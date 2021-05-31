package imagescalerfx.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for needed to get the information from the images to be processed,
 * and scale the images to different sizes.
 */
public class IOUtils {

    /**
     * It takes the image located in inputImagePath and scales it to
     * scaledWidth x scaledHeight pixels, placing the new image in outputImagePath
     */
    private static void resize(
            String inputImagePath,
            String outputImagePath,
            int scaledWidth,
            int scaledHeight
    ) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    /**
     * It relies on previous method to scale an input image to a given percentage. Indeed,
     * the first method is private, and you can only use this method out of this class
     */
    public static void resize(
            String inputImagePath,
            String outputImagePath,
            double percent
    ) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }

    /**
     * recursively deletes the directory referenced by
     * the given path, including every files and folders that it may have.
     */
    public static void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static List<ImageData> loadImages(File folder) {
        List<ImageData> mainImages = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            return mainImages;
        }

        for (File file : listOfFiles) {
            try {
                if (ImageIO.read(file) != null) {
                    mainImages.add(new ImageData(file.getName(), file.getPath()));
                }
            } catch (IOException ignored) { }
        }

        return mainImages;
    }

    public static List<ImageData> loadMainImages() {
        return loadImages(new File(System.getProperty("user.dir") + File.separator + "images"));
    }

    public static List<ImageData> loadChildImages(ImageData imageData) {
        String imageFolderName = imageData.getFileName().split("\\.")[0];
        return loadImages(new File(System.getProperty("user.dir") + File.separator + "images"
                + File.separator + imageFolderName));
    }

}
