package br.com.alura.server;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ExceptionHandler exceptionHandler = new ExceptionHandler();

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "TaskHub Thread" + threadNumber.getAndIncrement());
        System.out.println("Thread Factory funcionando...");
        // Cada thread criada dentro do pool utilizará a classe ExceptionHandler!
        thread.setUncaughtExceptionHandler(exceptionHandler);
        return thread;
    }
}
