package femr.ui.controllers;

import femr.util.translation.TranslationServer;
import femr.util.translation.TranslationJson;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;


public class BackEndControllerHelper  {

  public static void executePythonScript(String absPath) {
    try {
      ProcessBuilder pb = new ProcessBuilder("python", absPath);
      pb.start();
    } catch (NullPointerException e) {
      System.out.println("The python script does not exist or could not be opened.");
    } catch (IOException e) {
      System.out.println("An I/O error has occurred.");
    } catch (IndexOutOfBoundsException e) {
      System.out.println("The command list is empty");
    }
  }

  public static ArrayList<String> executeSpeedTestScript(String absPath) {
    ArrayList<String> speedInfo = new ArrayList<>();
    try {
      ProcessBuilder pb = new ProcessBuilder("python", absPath);
      Process p = pb.start();
      BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));

      String line = "";
      while ((line = bfr.readLine()) != null) {
        System.out.println("Python Output: " + line);
        speedInfo.add(line);
      }

    } catch (NullPointerException e) {
      System.out.println("The python script does not exist or could not be opened.");
    } catch (IOException e) {
      System.out.println("An I/O error has occurred.");
    } catch (IndexOutOfBoundsException e) {
      System.out.println("The command list is empty");
    }

    return speedInfo;
  }

  public static String translate(String arg, String from, String to) {
    String output = "";
    System.out.println(arg);
//    JSONArray j = new JSONArray(arg);
//    String s = j.getString("onsetTab");
//    System.out.println(j);
    try {
      //Build GET request argument, replacing spaces and newlines
      arg = arg.replaceAll(" ", "+").replaceAll("\n", "+");
      String translatedText = "";
      System.out.println(arg);

      //Make GET request
      URL url = new URL("http://localhost:5000/?text=" + arg + "&from=" + from + "&to=" + to);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");

      int status = con.getResponseCode();
      if(status == 200){
        //read response data
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine = in.readLine();
        in.close();

        System.out.println(arg);

        output = TranslationServer.makeServerRequest(arg, from, to);

        //parse translation from JSON
        ObjectMapper mapper = new ObjectMapper();
        TranslationJson api = mapper.readValue(output, TranslationJson.class);
        output = api.translatedText;

      } catch(MalformedURLException e){
        System.out.println("Malformed URL Exception");
        System.out.println(e.getMessage());
      } catch(IOException e){
        System.out.println("IOException for parsing JSON");
        System.out.println(e.getMessage());
      }
      con.disconnect();

    } catch (NullPointerException e) {
      System.out.println("The python script does not exist, or failed to translate.");
    } catch (IOException e) {
      System.out.println("An I/O error has occurred. (Translation server could be down)");

      TranslationServer.start();
      //busy wait for server to start
      while(!TranslationServer.appRunning());
      return translate(arg, from, to);

    } catch (IndexOutOfBoundsException e) {
      System.out.println("The command list is empty");

    }
    return output;
  }
}
