package br.com.alura.server;

import java.io.PrintStream;
import java.util.concurrent.*;

/* A interface "Callable" é semelhante à interface "Runnable", mas permite que você retorne um resultado
e lance exceções. O tipo entre os sinais de menor e maior (<>) especifica o tipo de resultado que a chamada
pode retornar. Neste caso, "Void" indica que a chamada não retorna nenhum valor. A interface "Callable" exige
a implementação do método "call()", que contém a lógica a ser executada quando a instância da classe é
invocada. */
public class MergeResultsFutureBankWs implements Callable<Void> {
    private final Future<String> futureWebServiceCaller;
    private final Future<String> futureDatabaseAcessor;
    private final PrintStream clientOutput;

    public MergeResultsFutureBankWs(Future<String> futureWebServiceCaller, Future<String> futureDatabaseAcessor, PrintStream clientOutput) {
        this.futureWebServiceCaller = futureWebServiceCaller;
        this.futureDatabaseAcessor = futureDatabaseAcessor;
        this.clientOutput = clientOutput;
    }


    @Override
    public Void call() {
        // Imprime no lado do servidor para acompanhamento:
        System.out.println("Aguardando resultados do future WS e Banco...");
        try {
            String webServiceResult = this.futureWebServiceCaller.get(20, TimeUnit.SECONDS);
            String databaseResult = this.futureDatabaseAcessor.get(20, TimeUnit.SECONDS);

            this.clientOutput.println("Resultado do comando C2: " + webServiceResult + ", " + databaseResult);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Timeout: cancelando a execução do comando C2");
            this.clientOutput.println("Timeout: execução do comando C2");
            this.futureWebServiceCaller.cancel(true);
            this.futureDatabaseAcessor.cancel(true);

            // Re-interrupt a thread após a interrupção dos futuros:
            Thread.currentThread().interrupt();
        }

        System.out.println("A classe MergeResultsFutureBankWs foi finalizada");
        return null;
    }
}
