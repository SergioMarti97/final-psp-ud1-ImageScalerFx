package imagescalerfx.utils;

import javafx.scene.control.Alert;

/**
 * This class is not compulsory, but it has been created to
 * show different Alert messages.
 */
public class MessageUtils {

    /**
     * To show error messages
     */
    public static void showError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * To show information messages
     */
    public static void showMessage(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
