package br.com.alura.server;

import java.net.Socket;
import java.util.Scanner;

public class TaskDistributor implements Runnable {
    private static final Object lock = new Object();
    private Socket socket;

    public TaskDistributor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Distribuindo tarefas para " + socket);

            // Bloqueia o acesso a esta seção crítica para garantir que apenas duas threads entrem aqui de cada vez
            synchronized (lock) {
                // Recebe a entrada através do método ".getInputStream()":
                Scanner clientInput = new Scanner(socket.getInputStream());

                // Entra em um loop enquanto houver novas linhas:
                while (clientInput.hasNextLine()) {
                    String command = clientInput.nextLine();
                    System.out.println(command);
                }

                clientInput.close();
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
