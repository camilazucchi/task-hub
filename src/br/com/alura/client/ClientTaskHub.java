package br.com.alura.client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTaskHub {
    public static void main(String[] args) throws Exception {
        Socket socket;

        socket = new Socket("localhost", 12345);

        System.out.println("Conexão estabelecida com sucesso!");

        PrintStream stream = new PrintStream(socket.getOutputStream());
        stream.println("C1");

        Scanner scanner = new Scanner(System.in);

        scanner.nextLine();

        stream.close();
        scanner.close();
        socket.close();

    }
}
