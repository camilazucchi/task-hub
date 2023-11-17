package br.com.alura.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TaskHub {
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor esperando por conexões...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Aceitando novo cliente " + socket.getPort());
            }
        } catch (IOException exception) {
            System.err.println("Erro ao lidar com o servidor: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}
