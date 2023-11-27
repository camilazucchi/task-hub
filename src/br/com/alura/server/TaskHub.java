package br.com.alura.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskHub {
    private final ServerSocket serverSocket;
    private final ExecutorService executorService;
    private AtomicBoolean isRunning;

    public TaskHub() throws IOException {
        System.out.println("Servidor esperando por conexões...");
        this.serverSocket = new ServerSocket(12345);
        this.executorService = Executors.newCachedThreadPool();
        this.isRunning = new AtomicBoolean(true);
    }

    // Método para iniciar o servidor:
    public void startServer() throws IOException {
        while (this.isRunning.get()) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Aceitando novo cliente na porta: " + socket.getPort());
                TaskDistributor taskDistributor = new TaskDistributor(executorService, socket, this);
                executorService.execute(taskDistributor);
            } catch (SocketException e) {
                System.out.println("SocketException está rodando? " + this.isRunning);
            }
        }
    }

    // Método para parar o servidor:
    /* Obs.: o servidor não para enquanto tiver um cliente conectado, somente quando o cliente é desconectado
    que o servidor para de rodar. */
    public void stopServer() throws IOException {
        this.isRunning.set(false);
        serverSocket.close();
        executorService.shutdown();
    }

    public static void main(String[] args) throws IOException {
        TaskHub server = new TaskHub();
        server.startServer();
        server.stopServer();
    }
}
