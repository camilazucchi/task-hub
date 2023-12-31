package br.com.alura.server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TaskDistributor implements Runnable {
    private static final Object lock = new Object();
    private final ExecutorService executorService;
    private final Socket socket;
    private final TaskHub server;

    public TaskDistributor(ExecutorService executorService, Socket socket, TaskHub server) {
        this.executorService = executorService;
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            System.out.println("Distribuindo tarefas para " + socket);

            // Bloqueia o acesso a esta seção crítica para garantir que apenas duas threads entrem aqui de cada vez
            synchronized (lock) {
                // Recebe a entrada através do método ".getInputStream()":
                Scanner clientInput = new Scanner(socket.getInputStream());
                PrintStream clientOutput = new PrintStream(socket.getOutputStream());

                // Entra em um loop enquanto houver novas linhas:
                while (clientInput.hasNextLine()) {
                    String command = clientInput.nextLine();
                    System.out.println("Comando recebido: " + command);

                    switch (command) {
                        case "c1":
                            clientOutput.println("O comando C1 foi enviado!");
                            CommandC1 c1 = new CommandC1(clientOutput);
                            this.executorService.execute(c1);
                            clientOutput.flush();
                            break;
                        case "c2":
                            clientOutput.println("O comando C2 foi enviado!");
                            C2WebServiceCaller c2WebServiceCaller = new C2WebServiceCaller(clientOutput);
                            C2DatabaseAcessor c2DatabaseAcessor = new C2DatabaseAcessor(clientOutput);
                            Future<String> futureWebServiceCaller = this.executorService.submit(c2WebServiceCaller);
                            Future<String> futureDatabaseAcessor = this.executorService.submit(c2DatabaseAcessor);
                            clientOutput.flush();

                            this.executorService.submit(new MergeResultsFutureBankWs(futureWebServiceCaller, futureDatabaseAcessor, clientOutput));

                            break;
                        case "fim":
                            clientOutput.println("Encerrando o servidor...");
                            server.stopServer();
                            break;
                        default:
                            clientOutput.println("Comando não encontrado.");
                            clientOutput.flush();
                    }

                    System.out.println(command);
                }

                clientOutput.close();
                clientInput.close();
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
