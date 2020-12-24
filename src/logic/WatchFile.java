package logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This program demonstrates how to use the Watch Service API to monitor change
 * events for a specific directory.
 * @author www.codejava.net
 *
 */
public class WatchFile extends Thread {
    private String textFQN;
    private String searchedString;
    private Step nextStep;
    private boolean watch;
    private final long DELAY = 5;
    private BufferedReader bf;


    /**
     * Cria uma Thread pra ficar verificando em um arquivo por uma String especifica. Quando essa string eh encontrada ele executa nextStep
     * @param textFQN O caminho completo do arquivo texto sendo lido
     * @param searchedString A string a ser procurada
     * @param nextStep Quando searchedString for encontrada a funcao next() sera executada
     */
    public WatchFile(String textFQN, String searchedString, Step nextStep) {
        this.textFQN = textFQN;
        this.searchedString = searchedString;
        this.nextStep = nextStep;
    }

    @Override
    public void run() {
        this.watch = true;

        try {
            // Limpando arquivo de log primeiro, pra nao ter problema com lixo
            PrintWriter writer = new PrintWriter(textFQN);
            writer.print("");
            writer.close();

            // Abrindo
            bf = new BufferedReader(new FileReader(textFQN));
            String line;

            System.out.println("Watching the File. and waiting for the message " + this.searchedString);
            //Runtime run = Runtime.getRuntime();
            while (this.watch) {
                line = bf.readLine();
                if (line != null) {
                	
                    if (line.contains("successfully opened")) { // TO-DO It needs to be received as a parameter
                    	System.out.println("O vídeo abriu aqui!");
                    	Runtime.getRuntime().exec("java -jar FinalsendShortPulse.jar");
                    } else if(line.contains("EOF reached")){
                    	System.out.println("Aqui eh quando acaba um video, da pra fazer alguma coisa aqui.");
                    	//Runtime.getRuntime().exec("taskkill /f /im java.exe");
                    } else if (line.contains(this.searchedString)) {
                        System.out.println("Aqui eh quando todos os videos da playlist acabam.");
                        Runtime.getRuntime().exec("java -jar FinalsendShortPulse.jar");
                        
                        // O this.nextStep.next() roda uma playlist se existir e retorna true
                        // Se nao existir nada pra rodar, retorna falso
                        boolean hasNextAfterRunning = this.nextStep.next();
                        if (!hasNextAfterRunning) {
                            stopWatching();
                        }
                        
                    }
                } else {
                    Thread.sleep(DELAY);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopWatching() throws InterruptedException, IOException {
        this.watch = false;
        VLC.taskkillVLC();
        if (this.bf != null) {
            bf.close();
        }
    }

}