package imagescalerfx.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for needed to get the information from the images to be processed,
 * and scale the images to different sizes.
 */
public class IOUtils {

    private static Image resize(Image input, int scaleFactorX, int scaleFactorY) {
        int W = (int) input.getWidth();
        int H = (int) input.getHeight();

        WritableImage output = new WritableImage(
                W * scaleFactorX,
                H * scaleFactorY
        );

        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int argb = reader.getArgb(x, y);
                for (int dy = 0; dy < scaleFactorY; dy++) {
                    for (int dx = 0; dx < scaleFactorX; dx++) {
                        writer.setArgb(x * scaleFactorX + dx, y * scaleFactorY + dy, argb);
                    }
                }
            }
        }

        return output;
    }

    public static void saveToFile(Image image, String outputImagePath) {
        File outputFile = new File(outputImagePath);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It takes the image located in inputImagePath and scales it to
     * scaledWidth x scaledHeight pixels, placing the new image in outputImagePath
     */
    private static void resize(
            String inputImagePath,
            String outputImagePath,
            int scaledWidth,
            int scaledHeight
    ) {
        Image outputImage = resize(new Image(IOUtils.class.getResourceAsStream(inputImagePath)), scaledWidth, scaledHeight);
        saveToFile(outputImage, outputImagePath);
    }

    /**
     * It relies on previous method to scale an input image to a given percentage. Indeed,
     * the first method is private, and you can only use this method out of this class
     */
    public static void resize(
            String inputImagePath,
            String outputImagePath,
            double percent
    ) {
        Image inputImage = new Image(IOUtils.class.getResourceAsStream(inputImagePath));
        int scaledW = (int)(percent * (int)inputImage.getWidth());
        int scaledH = (int)(percent * (int)inputImage.getHeight());
        resize(inputImagePath, outputImagePath, scaledW, scaledH);
    }

    /**
     * recursively deletes the directory referenced by
     * the given path, including every files and folders that it may have.
     */
    public static void deleteDirectory(Path path) {

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
