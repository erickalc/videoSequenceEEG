package logic;

import java.io.IOException;
import java.util.Queue;

public class NextStep implements Step {

	private Queue<String> media;
	private String logPath;
	
	public NextStep(Queue<String> media, String logPath){
		this.media = media;
		this.logPath = logPath;
	}
	
    @Override
    /**
     * Executa a proxima coisa e retorna true se ainda houver mais nexts ou false se nao houver
     */
    public boolean next() throws IOException, InterruptedException {
        if (media.isEmpty())
            return false;

        VLC.play(media.poll(), logPath);
        return true;
    }
	
}
