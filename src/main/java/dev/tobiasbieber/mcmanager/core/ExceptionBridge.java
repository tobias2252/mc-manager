package dev.tobiasbieber.mcmanager.core;

import java.util.function.Consumer;

// is used to handle exceptions from the ui thread to the main thread
public class ExceptionBridge {

    private static Consumer<Throwable> handler;

    public static void setHandler(Consumer<Throwable> handler) {
        ExceptionBridge.handler = handler;
    }

    // installs exception handler
    public static void install() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            if (handler != null) {
                handler.accept(throwable);
            }
        });
    }
}
