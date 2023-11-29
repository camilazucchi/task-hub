package br.com.alura.server;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {
    public static void main(String[] args) {
        // Cria um "ExecutorService" com um número fixo de threads:
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<String> future = executorService.submit(() -> {
           // Simula uma tarefa assíncrona:
            Thread.sleep(2000);
            return "Resultado da tarefa";
        });

        try {
            // Aguarda a conclusão da tarefa e obtém o resultado:
            String result = future.get();
            System.out.println("Resultado: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
