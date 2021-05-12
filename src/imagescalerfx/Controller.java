package imagescalerfx;

import imagescalerfx.utils.ImageData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label labelStatus;

    @FXML
    private ListView<ImageData> ListViewImages;

    @FXML
    private ListView<ImageData> ListViewScaledInstances;

    @FXML
    private Button ButtonStart;

    @FXML
    private ImageView ImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
