package femr.ui.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BackEndControllerHelper {
  public void executePythonScript(String absPath) {
    try {
      ProcessBuilder pb = new ProcessBuilder("python", absPath);
      Process p = pb.start();
      BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));

      System.out.println("--- Starting Python script now ---");
      String line = "";
      while ((line = bfr.readLine()) != null) {
        System.out.println("Python Output: " + line);
      }
      System.out.println("--- Finished Python script ---");

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
