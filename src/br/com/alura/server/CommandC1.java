package br.com.alura.server;

import java.io.PrintStream;

public class CommandC1 implements Runnable {

    private final PrintStream output;

    public CommandC1(PrintStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("Executando o comando C1.");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        output.println("Comando C1 executado com sucesso!");
    }
}
