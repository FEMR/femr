package femr.util.InternetConnnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.typesafe.config.ConfigFactory;

import java.io.*;
import java.net.*;

public final class InternetConnectionUtil {
    private static boolean existsConnection = false;

    private static final URL locationDataEndpoint = configLocationDataEndpoint();
    private static final int connectionTimeoutInMilliseconds = configConnectionTimeoutInMilliseconds();
    private static final int connectionCheckIntervalInSeconds = configConnectionCheckIntervalInSeconds();
    private static final int sendLocationDataInvervalInSeconds = configSendLocationDataIntervalInSeconds();

    private static URL configLocationDataEndpoint(){
        try {
            return new URL(ConfigFactory.load().getString("internetconnection.locationDataEndpoint"));
        } catch (MalformedURLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private static int configConnectionTimeoutInMilliseconds(){
        return ConfigFactory.load().getInt("internetconnection.timeoutInMiliseconds");
    }

    private static int configConnectionCheckIntervalInSeconds(){
        return ConfigFactory.load().getInt("internetconnection.connectionCheckIntervalInSeconds");
    }

    private static int configSendLocationDataIntervalInSeconds(){
        return ConfigFactory.load().getInt("internetconnection.locationDataSendIntervalInSeconds");
    }

    private static void setExistsConnection(boolean existsConnection){
        InternetConnectionUtil.existsConnection = existsConnection;
    }

    /**
     * Tests whether https://teamfemr.org is reachable
     *
     * @return Boolean value for whether connection was made
     */
    private static Boolean existsConnection() {
        try {
            URL url = new URL("https://google.com");

            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();

            //Set connection timout
            urlConnect.setConnectTimeout(connectionTimeoutInMilliseconds);

            //trying to retrieve data from the source. If there
            //is no connection, this line will fail
            Object objData = urlConnect.getContent();

        } catch (UnknownHostException e) {
            return false;

        } catch (IOException e) {
            return false;
        }

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
     * @param
     * @return
     */
    private static JsonObject getLocationDataByIp(){
        JsonObject locationDataJson = null;
        try{
            URL url = new URL("https://api.ipdata.co/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(connectionTimeoutInMilliseconds);
            con.setReadTimeout(connectionTimeoutInMilliseconds);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            locationDataJson = new JsonParser().parse(content.toString()).getAsJsonObject();
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
                filteredJson.add(key, locationDataJson.get(key));
            }
            return filteredJson;
        }
    }

    private InternetConnectionUtil(){
        //There should be no objects of this type.
        //This utility should just be accessed by tasks and the controllers/service layer
    }

    public static boolean getExistsConnection(){
        return InternetConnectionUtil.existsConnection;
    }

    public static int getConnectionCheckIntervalInSeconds(){
        return connectionCheckIntervalInSeconds;
    }

    public static int getSendLocationDataInvervalInSeconds(){
        return sendLocationDataInvervalInSeconds;
    }

    public static void updateExistsConnection(){
        setExistsConnection(existsConnection());
    }

    public static Boolean sendLocationInformation(){
        try{
            JsonObject rawLocationJson = getLocationDataByIp();
            JsonObject jsonToSend = filterJsonByKeys(rawLocationJson, "ip","country_name");
            HttpURLConnection urlConnect = (HttpURLConnection)locationDataEndpoint.openConnection();
            urlConnect.setRequestMethod("POST");
            urlConnect.setRequestProperty("Content-Type",  "application/json");
            urlConnect.setDoOutput(true);
            urlConnect.setDoInput(true);
            DataOutputStream outputStreamWriter = new DataOutputStream(urlConnect.getOutputStream());
            outputStreamWriter.writeBytes(jsonToSend.toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();
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
