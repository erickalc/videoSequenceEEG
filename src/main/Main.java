package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import logic.NextStep;
import logic.Step;
import logic.VLC;
import logic.WatchFile;

public class Main {
    private static final String SEARCHED_WORD = "nothing to play";

    public static void main(String[] args) throws IOException {
        Queue<String> media = new LinkedList<>();
        
        String videos = createListOfVideos();
        media.add(videos);

        // A cada execucao o arquivo log eh esvaziado. Senao ele vai ler lixo
        String logPath = "C:\\Users\\eanes\\workspace\\VideoSequence\\vlc_log.txt";
        
        //Cria uma thread que vai executar cada vídeo da lista
        playMedia(media, logPath);
    }

	private static String createListOfVideos() throws FileNotFoundException, IOException {
		BufferedReader bf = new BufferedReader(new FileReader("C:\\Users\\eanes\\workspace\\VideoSequence\\name_videos.txt"));
        
        String videos = "";
        String line = "";
        while (true) {
        	line = bf.readLine();
        	if(line == null) break;
        	videos = videos + "\"C:\\Users\\eanes\\Documents\\ProjetoEEG\\LIRIS-ACCEDE\\data\\" + line + "\" ";
        }
        bf.close();
		return videos;
	}
    
    private static void playMedia(Queue<String> media, String logPath) throws IOException {    	
        Step nextStep = new NextStep(media, logPath);

        WatchFile watchFile = new WatchFile(logPath, SEARCHED_WORD, nextStep);
        watchFile.start();
        
        VLC.play(media.poll(), logPath);
    }
}
