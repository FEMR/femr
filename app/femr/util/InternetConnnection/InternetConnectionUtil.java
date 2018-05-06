package femr.util.InternetConnnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.Logger;
import java.io.*;
import java.net.*;

import com.jcraft.jsch.*;

public final class InternetConnectionUtil {
    private static boolean existsConnection = false;

    private static final URL locationDataEndpoint = configLocationDataEndpoint();
    private static final int connectionTimeoutInMilliseconds = configConnectionTimeoutInMilliseconds();
    private static final int connectionCheckIntervalInSeconds = configConnectionCheckIntervalInSeconds();
    private static final int sendLocationDataInvervalInSeconds = configSendLocationDataIntervalInSeconds();
    private static String sshUser = configSshUser();
    private static String sshHost = configSshHost();
    private static String pathToSshKey = configPathToSshKey();
    private static String pathToSshKnownHosts = configPathToSshKnownHosts();

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

    private static String configSshUser(){
        return ConfigFactory.load().getString("internetconnection.sshUser");
    }

    private static String configSshHost(){
        return ConfigFactory.load().getString("internetconnection.sshHost");
    }

    private static String configPathToSshKey(){
        return ConfigFactory.load().getString("internetconnection.pathToSshKey");
    }

    private static String configPathToSshKnownHosts(){
        return ConfigFactory.load().getString("internetconnection.pathToSshKnownHosts");
    }

    private static void setExistsConnection(boolean existsConnection){
        InternetConnectionUtil.existsConnection = existsConnection;
    }

    /**
     * Tests whether https://google.com is reachable
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

            int responseCode = con.getResponseCode();
            if(responseCode < 200 || responseCode > 299){
                //This should trigger when we've hit the daily limit for sending location data
                Logger.error("Getting Location data from https://api.ipdata.co/ failed with response code: " + responseCode + ".");
                return null;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            locationDataJson = new JsonParser().parse(content.toString()).getAsJsonObject();

        } catch(IllegalStateException e){
            Logger.error("Issue with getting location data - https://api.ipdata.co/ did not provide valid Json: ", e.getMessage(), e);
        }
        catch(IOException e){
            Logger.error("There was an issue getting location data from api.ipdata.co: ", e.getMessage(), e);
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
            if(rawLocationJson == null){
                Logger.error("There was an issue getting location data from api.ipdata.co.");
                return false;
            }
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

                Logger.error("Sending data to: " + urlConnect.getURL() + "failed with response code: " + responseCode + ".");
                return false;
            }
        } catch(IOException e) {

            Logger.error("There was an issue sending location data to endpoint: ", e.getMessage(), e);
            return false;
        }
        return true;
    }

    public Boolean doSSH(){
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(sshUser, sshHost, 22);

            jsch.setKnownHosts(pathToSshKnownHosts);
            jsch.addIdentity(pathToSshKey);

            session.connect();
            ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
            channel.get("src", "dst");

            session.disconnect();
        } catch (SftpException e){
            e.printStackTrace();
        } catch(JSchException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }
}

