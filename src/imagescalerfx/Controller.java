package imagescalerfx;

import imagescalerfx.utils.ImageData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.util.concurrent.*;

public class Controller {

    @FXML
    private Label labelStatus;

    @FXML
    private ListView<ImageData> listViewImages;

    @FXML
    private ListView<ImageData> listViewScaledInstances;

    @FXML
    private Button btnStart;

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {

    }

    public void handleStart() throws InterruptedException {
        btnStart.setDisable(true);
        System.out.println("START");
        ExecutorService executorService = Executors.newCachedThreadPool();
        //executorService.execute();

        Runnable task2 = () -> System.out.println("Running task2...");

        System.out.println("OK");

        //run this task after 5 seconds, nonblock for task3
        //ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task2, 5, 1, TimeUnit.SECONDS);

        /*int i = 0;
        while (true) {
            i++;
            if (i == 100) {
                ses.shutdown();
                break;
            }
        }*/
    }

    private void test() {
        System.out.println("OKAY");
    }

}
