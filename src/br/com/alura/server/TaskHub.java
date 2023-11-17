package br.com.alura.server;

import java.net.ServerSocket;
import java.net.Socket;

public class TaskHub {
    public static void main(String[] args) throws Exception {

        System.out.println("--- Iniciando o servidor ---");

        /* A classe "ServerSocket" é usada para criar um servidor que aguarda conexões de clientes em
        uma porta específica. Portas são canais específicos para comunicação em rede. */
        ServerSocket serverSocket = new ServerSocket(12345);

        /* O método "accept()" da classe "ServerSocket" é bloqueante e aguarda até que um cliente se conecte ao
        servidor. Quando um cliente se conecta, o método retorna um novo objeto "Socket", que representa a
        conexão entre o ervidor e o cliente.
        O "Socket" é então usado para estabelecer a comunicação entre o servidor e o cliente, permitindo a
        troca de dados. */
        Socket socket = serverSocket.accept();

    }
}
