package femr.ui.controllers;

import java.io.IOException;

public class BackEndControllerHelper {
  public static void executePythonScript(String absPath) {
    try {
      ProcessBuilder pb = new ProcessBuilder("python", absPath);
//      System.out.println("Running script");
      pb.start();
    } catch (NullPointerException e) {
      System.out.println("The python script does not exist or could not be opened.");
    } catch (IOException e) {
      System.out.println("An I/O error has occurred.");
    } catch (IndexOutOfBoundsException e) {
      System.out.println("The command list is empty");
    }
  }
}
