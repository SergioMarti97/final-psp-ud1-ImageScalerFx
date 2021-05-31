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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        setSelectionImages();
    }

    private void setScheduleService() {
        scheduledService = new ScheduledService<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
<<<<<<< HEAD
                return new Task() {
=======
                return new Task<Boolean>() {
>>>>>>> 70f4a657dc9e349136d4432d8cc7180816b9462f
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

    private void setSelectionImages() {
        listViewImages.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, lastImage, currentImage) -> {
                    listViewScaledInstances.getItems().clear();
                    listViewScaledInstances.getItems().addAll(IOUtils.loadChildImages(currentImage));
                });

        listViewScaledInstances.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, lastImage, currentImage) -> {
                    if (currentImage == null) {
                        return;
                    }
                    try {
                        imageView.setImage(new Image(new FileInputStream(currentImage.getPath())));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void handleStart() {
        btnStart.setDisable(true);
        imageView.setImage(null);
        listViewImages.getItems().clear();
        listViewScaledInstances.getItems().clear();

        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (ImageData imageData : IOUtils.loadMainImages()) {
            executor.execute(() -> {
                String imageFolderName = imageData.getFileName().split("\\.")[0];
                File file = new File(System.getProperty("user.dir") + File.separator + "images" + File.separator + imageFolderName);
                if (file.isDirectory()) {
                    file.delete();
                }

                try {
                    file.createNewFile();
                    for (int i = 1; i < 10; i++) {
                        int percent = i * 10;
                        String imageName = percent + "_" + imageData.getFileName();
                        String pathImage = file.getPath() + File.separator + imageName;
                        try {
                            IOUtils.resize(imageData.getPath(), pathImage, percent / 100.0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> listViewImages.getItems().add(imageData));
            });
        }

        executor.shutdown();
        scheduledService.restart();
    }

}
