package imagescalerfx;

import imagescalerfx.utils.IOUtils;
import imagescalerfx.utils.ImageData;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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

    private ScheduledService<Boolean> scheduledService;

    private ThreadPoolExecutor executor;

    @FXML
    public void initialize() {
        setScheduleService();
        listViewImages.getItems().addAll(IOUtils.loadMainImages());
        setSelectionImageScaled();
    }

    private void setScheduleService() {
        scheduledService = new ScheduledService<>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<>() {
                    @Override
                    protected Boolean call() {
                        Platform.runLater(() -> {
                            labelStatus.setText(executor.getCompletedTaskCount() + " of " + executor.getTaskCount() + " tasks finished");
                        });
                        return executor.isTerminated();
                    }
                };
            }
        };

        scheduledService.setDelay(Duration.millis(500));
        scheduledService.setPeriod(Duration.seconds(1));
        scheduledService.setOnSucceeded(e -> {
            if (scheduledService.getValue()) {
                scheduledService.cancel();
                btnStart.setDisable(false);
            }
        });
    }

    private void setSelectionImageScaled() {
        listViewImages.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, lastImage, currentImage) -> {
                    listViewScaledInstances.getItems().clear();
                    listViewScaledInstances.getItems().addAll(IOUtils.loadChildImages(currentImage));
                });
    }

    public void handleStart() {
        btnStart.setDisable(true);
        imageView.setImage(null);
        listViewImages.getItems().clear();
        listViewScaledInstances.getItems().clear();

        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for(int i = 1; i < 10; i++) {
            executor.execute(() -> {

            });
        }

        executor.shutdown();
        scheduledService.restart();
    }

}
