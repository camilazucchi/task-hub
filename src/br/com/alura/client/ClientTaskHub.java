package br.com.alura.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTaskHub {
    public static void main(String[] args) throws Exception {
        Socket socket;

        socket = new Socket("localhost", 12345);

        System.out.println("Conexão estabelecida com sucesso!");

        // Separamos as tarefas do lado do cliente:
        // Tarefa para enviar os comandos:
        // Aqui utilizei lambda para o código de Thread, sem o Runnable explicitamente:
        Thread sendCommandThread = new Thread(() -> {
            try {
                System.out.println("Envie seus comandos: ");
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (line.isEmpty()) {
                        break;
                    }
                    printStream.println(line);
                }
                printStream.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Tarefa para receber os dados:
        Thread receiveResponseThread = new Thread(() -> {
            try {
                System.out.println("Recebendo dados do servidor...");
                Scanner serverResponse = new Scanner(socket.getInputStream());
                while (serverResponse.hasNextLine()) {
                    String line = serverResponse.nextLine();
                    System.out.println(line);
                }
                serverResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        receiveResponseThread.start();
        sendCommandThread.start();
        // Faz com que a thread que executa essa linha fique esperando (no nosso caso, main).
        // Só voltas a ser executada quando "sendCommandThread" terminar.
        sendCommandThread.join();

        System.out.println("Fechando o socket do cliente...");
        socket.close();
    }
}
