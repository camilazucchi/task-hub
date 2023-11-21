package br.com.alura.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskHub {
    private static volatile boolean isRunning = true;
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor esperando por conexões...");

            while (isRunning) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Aceitando novo cliente na porta: " + socket.getPort());

                    TaskDistributor taskDistributor = new TaskDistributor(socket);
                    executorService.execute(taskDistributor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    public static void stopServer() {
        isRunning = false;
    }
}
