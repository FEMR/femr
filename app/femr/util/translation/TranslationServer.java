package femr.util.translation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Singleton
public class TranslationServer {
    @Inject
    public TranslationServer(){
        this.start();
    }

    private static int portNumber = -1;

    //Takes a string, a from code and a to code, and returns the translatedtext
    public static String makeServerRequest(String text, String from, String to) throws MalformedURLException {
        if(serverNotRunning()){
            start();
            while(serverNotRunning()); //block
        }
        //Build GET request argument, replacing spaces and newlines
        text = text.replaceAll(" ", "+").replaceAll("\n", "+");

        String response = "";
        try {
            // Harrison Shu
            // Encode the URL String parameter before creating URL to allow arabic and hebrew to be in the URL
            String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());

            //Make GET request
            URL url = new URL("http://localhost:" + portNumber +"/?text=" +
                    encodedText + "&from=" + from + "&to=" + to);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            //read response from server
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            response = in.readLine();
            in.close();

            con.disconnect();
        } catch(IOException e){
            System.out.println(e) ;
            return makeServerRequest(text, from, to);
        }
        return response;
    }
    public static boolean serverNotRunning(){
        //initial value of portNumber
        if(portNumber == -1){
            File log = new File("translator/server.log");
            try {
                Scanner s = new Scanner(log);
                if(s.hasNext()){
                    portNumber = Integer.parseInt(s.nextLine().split(": ")[1]);
                }
                else{
                    return true;
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
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
    public static void start(){
        System.out.println("Starting translation server...");

        if(serverNotRunning()){
            File log = new File("translator/server.log");
            String absPath = "translator/server.py";
            try {
                ProcessBuilder pb = new ProcessBuilder("python", absPath);
                pb.redirectOutput(log);
                pb.redirectErrorStream(true);
                pb.start();

            } catch (IOException e) {
                System.out.println("An I/O error has occurred.");
                System.out.println(e.getMessage());
            }

            try {
                Scanner s = new Scanner(log);
                //Wait for server.log to be written to (port number)
                while(log.length() == 0);
                portNumber = Integer.parseInt(s.nextLine().split(": ")[1]);
                s.close();
            } catch (FileNotFoundException e) {
                System.out.println("A FileNotFound error has occurred.");
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Translation server running!");
    }
}
