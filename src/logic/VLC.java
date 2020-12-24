package logic;

import java.io.IOException;

public class VLC {

    // Nao precisamos de um objeto do tipo VLC
    private VLC() {}

    public static void play(String mediaPath, String logPath) throws IOException {
        Runtime run = Runtime.getRuntime();

        String logPathVLC = "\"" + logPath + "\"";
        
        //Para deixar a tela fullscreen
        run.exec("vlc --log-verbose=2 --logfile=" + logPathVLC +
                " --logmode=text --file-logging --save-config --fullscreen" +
                " --rc-quiet --one-instance " + mediaPath);
        
        /*run.exec("vlc --log-verbose=2 --logfile=" + logPathVLC +
                " --logmode=text --file-logging --save-config" +
                " --rc-quiet --one-instance " + mediaPath);*/

        System.out.println("Playing "+ mediaPath);
    }

    public static void taskkillVLC() throws IOException {
        Runtime run = Runtime.getRuntime();
        run.exec("taskkill /im vlc.exe");
    }
}
