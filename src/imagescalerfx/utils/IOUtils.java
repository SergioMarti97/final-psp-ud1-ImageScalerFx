package imagescalerfx.utils;

import java.nio.file.Path;

/**
 * This class is for needed to get the information from the images to be processed,
 * and scale the images to different sizes.
 */
public class IOUtils {

    /**
     * It takes the image located in inputImagePath and scales it to
     * scaledWidth x scaledHeight pixels, placing the new image in outputImagePath
     */
    public static void resize(
            String inputImagePath,
            String outputImagePath,
            int scaledWidth,
            int scaledHeight
    ) {

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

    }

    /**
     * recursively deletes the directory referenced by
     * the given path, including every files and folders that it may have.
     */
    public static void deleteDirectory(Path path) {

    }

}
