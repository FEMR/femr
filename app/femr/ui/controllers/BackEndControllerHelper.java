package femr.ui.controllers;


import java.io.*;
import java.util.ArrayList;


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
      ProcessBuilder pb;
      if(absPath.equals("/usr/src/speedtest/sptest.py")){
        pb = new ProcessBuilder("python3", absPath);
      }else{
        pb = new ProcessBuilder("python", absPath);
      }
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
}
