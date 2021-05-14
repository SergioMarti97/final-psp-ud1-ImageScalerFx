package imagescalerfx.utils;

/**
 * This class will store the relevant information about every image that we are going to manage:
 * @field Image file name: a String containing a the file name of the image, such as picture.jpg, for instance.
 * @field Image path: a Path object containing the full path to the image
 *
 * Constructor with all the attributes, and the corresponding getters (not setters).
 * toString method to show image name in the list views.
 */
public class ImageData {

    private String fileName;

    private String path;

    public ImageData(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return fileName;
    }

}
