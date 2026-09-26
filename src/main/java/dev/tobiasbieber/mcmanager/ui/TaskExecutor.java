package dev.tobiasbieber.mcmanager.ui;

import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskExecutor {

    private static final Logger log = LoggerFactory.getLogger(TaskExecutor.class);

    public static Task<Void> createTask(Runnable runnable) {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                runnable.run();
                return null;
            }
        };
    }

    public static void runTask(Task<?> task, String name) {
        log.info("Running task: {}", name);
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.setName(name);
        thread.start();
    }
}
