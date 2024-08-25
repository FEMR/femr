package femr.util.translation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

@Singleton
public class TranslationServer {
    @Inject
    public TranslationServer(){
        start(timeout);
    }

    private static int portNumber = -1;
    private static final String timeout = "600";

    public static int getPortFromLog(String logPath, Boolean wait){
        int portNumber = 0;
        try{
            File log = new File(logPath);
            Scanner s = new Scanner(log);
            if(wait){
                while(log.length() == 0);
            }
            if (s.hasNext()) {
                portNumber = Integer.parseInt(s.nextLine().split(": ")[1]);
            }
            s.close();
        }
        catch(FileNotFoundException e){
            return portNumber;
        }
        catch(Exception e){
            System.out.println("Problem retrieving port number from log");
            throw new RuntimeException(e);
        }
        return portNumber;
    }
    public static boolean serverNotRunning(String logPath){
        //initial value of portNumber
        if(portNumber == -1){
            portNumber = getPortFromLog(logPath, false);
            if(portNumber == 0){
                return true;
            }
        }
        try{
            final URL url = new URL("http://localhost:" + portNumber);
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return false;
        } catch (IOException e){
            return true;
        }
    }
    public static String makeServerRequest(String jsonString, String from, String to) {
        String logPath = "translator/server.log";
        if(serverNotRunning(logPath)){
            start(timeout);
            while(serverNotRunning(logPath)); //block
        }

        String response;
        try {
            byte[] json = jsonString.getBytes(StandardCharsets.UTF_8);
            int length = json.length;

            //Make POST request
            URL url = new URL("http://localhost:" + portNumber + "/?from=" + from + "&to=" + to);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setFixedLengthStreamingMode(length);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.connect();

            try(OutputStream os = con.getOutputStream()) {
                os.write(json);
            }

            //read response from server
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            response = in.readLine();
            in.close();

            con.disconnect();
        } catch(IOException e){
            System.out.println(e.getMessage());
            return "Translation Unavailable";
        }
        return response;
    }
    public static void start(String timeout) {
        System.out.println("Starting translation server...");

        String logPath = "translator/server.log";
        String absPath = "translator/server.py";
        if(serverNotRunning(logPath)){
            File log = new File(logPath);
            try {
                log.createNewFile();
                ProcessBuilder pb = new ProcessBuilder("python", absPath, timeout);
                pb.redirectOutput(log);
                pb.redirectErrorStream(true);
                pb.start();
            } catch (IOException e) {
                System.out.println("An I/O error has occurred.");
                System.out.println(e.getMessage());
            }

            portNumber = getPortFromLog(logPath, true);

        }
        System.out.println("Translation server running!");
    }
}
