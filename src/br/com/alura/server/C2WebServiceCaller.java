package br.com.alura.server;

import java.io.PrintStream;

public class C2WebServiceCaller implements Runnable {

    private final PrintStream output;

    public C2WebServiceCaller(PrintStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("Executando o comando C2.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Exception no comando C2...");

        //output.println("Comando C2 executado com sucesso!");
    }
}
