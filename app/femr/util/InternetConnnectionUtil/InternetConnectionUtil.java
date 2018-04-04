package femr.util.InternetConnnectionUtil;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import play.libs.Json;
import play.mvc.Http;
import play.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public final class InternetConnectionUtil {
    static Boolean existsConnection;

    /**
     * Tests whether https://teamfemr.org is reachable
     *
     * @param timeout in milliseconds
     * @return Boolean value for whether connection was made
     */
    public static Boolean existsConnection(int timeout) {
        try {
            URL url = new URL("https://google.com");

            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();

            //Set connection timout
            urlConnect.setConnectTimeout(timeout);

            //trying to retrieve data from the source. If there
            //is no connection, this line will fail
            Object objData = urlConnect.getContent();

        } catch (UnknownHostException e) {
            //This would be adding an error to some ServiceResponse<T>
            return false;

        } catch (IOException e) {
            //This would be adding an error to some ServiceResponse<T>
            return false;
        }

        //This would be a ServiceResponse<T> or similar when incorporated into production code.
        return true;
    }

    /**
     * Find our IP address by pinging checkip.amazonaws.com
     * Only Makes Sense if there exists an internet connection
     *
     * @return InetAddress with current external ip; null if the check fails
     */
    private static InetAddress getExternalIpAddress(){
        InetAddress externalIp = null;
        try{
            URL amazonAWS = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(amazonAWS.openStream()));

            String ip = in.readLine();
            externalIp = InetAddress.getByName(ip);
        } catch(MalformedURLException e){
        } catch(IOException e){
        }

        return externalIp;
    }

    /**
     * Get some subset of the location data provided by a GET request of api.ipdata.co
     * Valid args
     * @param timeout
     * @return
     */
    private static JsonObject getLocationDataByIp(int timeout){
        JsonObject locationDataJson = null;
        try{
            URL url = new URL("https://api.ipdata.co/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(timeout);
            con.setReadTimeout(timeout);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JsonObject locationData = (new JsonParser().parse(content.toString())).getAsJsonObject();
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return locationDataJson;
    }

    /**
     *
     * @param locationDataJson
     * @param keys
     * @return
     */
    private static JsonObject filterJsonByKeys(JsonObject locationDataJson, String... keys){
        if(locationDataJson == null){
            return null;
        } else {
            JsonObject filteredJson = new JsonObject();
            for(String key: keys){
                filteredJson.add("key", locationDataJson.get(key));
            }
            return filteredJson;
        }
    }

    public Boolean sendLocationInformation(URL url){
        try{
            JsonObject rawLocationJson = getLocationDataByIp(1000);
            JsonObject jsonToSend = filterJsonByKeys(rawLocationJson, "ip","country_name");

            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            urlConnect.setRequestMethod("POST");
            urlConnect.setRequestProperty("User-Agent", "USER_AGENT");
            urlConnect.setRequestProperty("Accept-Language", "UTF-8");
            urlConnect.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnect.getOutputStream());
            outputStreamWriter.write(jsonToSend.toString());
            outputStreamWriter.flush();
            int responseCode = urlConnect.getResponseCode();
            if(responseCode < 200 || responseCode > 299){
                return false;
            }

        } catch(IOException e) {
            return false;
        }
        return true;
    }
}
