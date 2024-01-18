package femr.util.translation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Singleton
public class TranslationServer {
    @Inject
    public TranslationServer(){
        this.start();
    }

    public static boolean appRunning(){
        try{
            final URL url = new URL("http://localhost:8000");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (IOException e){
            return false;
        }
    }
    public static void start(){
        System.out.println("Starting translation server");

        if(!appRunning()){
            String absPath = "translator/server.py";
            try {
                ProcessBuilder pb = new ProcessBuilder("py", absPath);
                File log = new File("translator/log");
                pb.redirectOutput(log);
                pb.redirectErrorStream(true);
                pb.start();
            } catch (NullPointerException e) {
                System.out.println("The python script does not exist or could not be opened.");
            } catch (IOException e) {
                System.out.println("An I/O error has occurred.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("The command list is empty");
            }
        } else{
            System.out.println("Translation server already running");
        }
    }
}
