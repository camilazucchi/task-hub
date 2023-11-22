package br.com.alura.server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TaskDistributor implements Runnable {
    private static final Object lock = new Object();
    private Socket socket;
    private TaskHub server;

    public TaskDistributor(Socket socket, TaskHub server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            System.out.println("Distribuindo tarefas para " + socket);

            // Bloqueia o acesso a esta seção crítica para garantir que apenas duas threads entrem aqui de cada vez
            synchronized (lock) {
                // Recebe a entrada através do método ".getInputStream()":
                Scanner clientInput = new Scanner(socket.getInputStream());
                PrintStream clientOutput = new PrintStream(socket.getOutputStream());

                // Entra em um loop enquanto houver novas linhas:
                while (clientInput.hasNextLine()) {
                    String command = clientInput.nextLine();
                    System.out.println("Comando recebido: " + command);

                    switch (command) {
                        case "c1":
                            clientOutput.println("O comando C1 foi enviado!");
                            clientOutput.flush();
                            break;
                        case "c2":
                            clientOutput.println("O comando C2 foi enviado!");
                            clientOutput.flush();
                            break;
                        case "fim":
                            clientOutput.println("Encerrando o servidor...");
                            server.stopServer();
                            break;
                        default:
                            clientOutput.println("Comando não encontrado.");
                            clientOutput.flush();
                    }

                    System.out.println(command);
                }

                clientOutput.close();
                clientInput.close();
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
