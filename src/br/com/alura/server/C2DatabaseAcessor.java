package br.com.alura.server;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class C2DatabaseAcessor implements Callable<String> {
    private final PrintStream output;
    private final Random random = new Random();

    public C2DatabaseAcessor(PrintStream output) {
        this.output = output;
    }

    @Override
    public String call() throws Exception {
        // Saída no console do SERVIDOR:
        System.out.println("Servidor recebeu o comando C2 - Database.");
        // Saída para o CLIENTE:
        output.println("Processando o comando C2 - Database.");
        // Aguarda 25 segundos:
        Thread.sleep(25000);
        // Gera um número aleatório:
        int number = random.nextInt(100) + 1;
        // Saída no console do SERVIDOR:
        System.out.println("Servidor finalizou a execução do comando C2 - Database.");
        return Integer.toString(number);
    }
}
