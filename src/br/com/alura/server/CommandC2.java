package br.com.alura.server;

import java.io.PrintStream;

public class CommandC2 implements Runnable {

    private final PrintStream output;

    public CommandC2(PrintStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("Executando o comando C2.");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        output.println("Comando C2 executado com sucesso!");
    }
}
