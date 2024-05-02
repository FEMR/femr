package femr.util.translation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

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
            //Make a GET request
            URL url = new URL("http://localhost:" + portNumber +"/?text=" +
                    text + "&from=" + from + "&to=" + to);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            //read response from server
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            response = in.readLine();
            in.close();

            con.disconnect();
        } catch(IOException e){
            return makeServerRequest(text, from, to);
        }
        return response;
    }
    public static boolean serverNotRunning(){
        //initial value of portNumber
        if(portNumber == -1){
            return true;
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
        File log = new File("translator/server.log");

        if(serverNotRunning()){
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
                Scanner s;
                //Wait for server.log to be written to (port number)
                do {
                    s = new Scanner(log);
                } while (!s.hasNext());

                portNumber = Integer.parseInt(s.nextLine().split(": ")[1]);
                System.out.println(portNumber);
            } catch (FileNotFoundException e) {
                System.out.println("A FileNotFound error has occurred.");
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Translation server running!");
    }
}
