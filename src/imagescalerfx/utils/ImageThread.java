package imagescalerfx.utils;

import javafx.application.Platform;

public class ImageThread extends Thread {

    public ImageThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        Platform.runLater(() -> System.out.println("OK RUNNABLE"));
    }
}
