package br.com.alura.server;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class C2WebServiceCaller implements Callable<String> {

    private final PrintStream output;
    private final Random random = new Random();

    public C2WebServiceCaller(PrintStream output) {
        this.output = output;
    }

    @Override
    public String call() throws Exception {
        // Saída no console do SERVIDOR:
        System.out.println("Servidor recebeu o comando C2 - WebService.");
        // Saída para o CLIENTE:
        output.println("Processando o comando C2 - WebService.");
        // Aguarda 20 segundos:
        Thread.sleep(25000);
        // Gera um número aleatório:
        int number = random.nextInt(100) + 1;
        // Saída no console do SERVIDOR:
        System.out.println("Servidor finalizou a execução do comando C2 - WebService.");
        return Integer.toString(number);
    }
}
