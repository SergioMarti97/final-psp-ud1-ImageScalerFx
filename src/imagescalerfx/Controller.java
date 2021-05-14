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
<<<<<<< HEAD
        imageView.setImage(null);
        listViewImages.getItems().clear();
        listViewScaledInstances.getItems().clear();
=======
        System.out.println("START");
        ExecutorService executorService = Executors.newCachedThreadPool();
        //executorService.execute();

        Runnable task2 = () -> System.out.println("Running task2...");
>>>>>>> 0809fb688fadae9389d28997d8b0b836a0894074

        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

<<<<<<< HEAD
        for(int i = 1; i < 10; i++) {
            executor.execute(() -> {

            });
        }
=======
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
>>>>>>> 0809fb688fadae9389d28997d8b0b836a0894074

        executor.shutdown();
        scheduledService.restart();
    }

}
