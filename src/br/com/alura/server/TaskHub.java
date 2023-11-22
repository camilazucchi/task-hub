package br.com.alura.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskHub {
    private final ServerSocket serverSocket;
    private final ExecutorService executorService;
    private boolean isRunning;

    public TaskHub() throws IOException {
        System.out.println("Servidor esperando por conexões...");
        this.serverSocket = new ServerSocket(12345);
        this.executorService = Executors.newCachedThreadPool();
        this.isRunning = true;
    }

    // Método para iniciar o servidor:
    public void startServer() throws IOException {
        while (this.isRunning) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Aceitando novo cliente na porta: " + socket.getPort());
                TaskDistributor taskDistributor = new TaskDistributor(socket, this);
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
        this.isRunning = false;
        serverSocket.close();
        executorService.shutdown();
    }

    public static void main(String[] args) throws IOException {
        TaskHub server = new TaskHub();
        server.startServer();
        server.stopServer();
    }
}
