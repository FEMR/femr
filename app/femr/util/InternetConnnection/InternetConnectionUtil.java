package femr.util.InternetConnnection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
        String user="";
        String host="";
        String password="";

        try{
            Session session=jsch.getSession(user, host, 22);
            session.setPassword(password);

//            JFileChooser chooser = new JFileChooser();
////            chooser.setDialogTitle("Choose your known_hosts(ex. ~/.ssh/known_hosts)");
//            chooser.setFileHidingEnabled(false);
//            System.out.println("AAA");
//            int returnVal=chooser.showOpenDialog(null);
//            if(returnVal==JFileChooser.APPROVE_OPTION) {
//                System.out.println("You chose "+
//                        chooser.getSelectedFile().getAbsolutePath()+".");
//                jsch.setKnownHosts(chooser.getSelectedFile().getAbsolutePath());
//            }
            jsch.setKnownHosts("");
            HostKeyRepository hkr=jsch.getHostKeyRepository();
            HostKey[] hks=hkr.getHostKey();
            if(hks!=null){
                System.out.println("Host keys in "+hkr.getKnownHostsRepositoryID());
                for(int i=0; i<hks.length; i++){
                    HostKey hk=hks[i];
                    System.out.println(hk.getHost()+" "+
                            hk.getType()+" "+
                            hk.getFingerPrint(jsch));
                }
                System.out.println("");
            }
            session.setConfig("server_host_key", hks[hks.length-1].getType());
//            System.exit(1);
            //UNSAFE, PROBABLY FIND A WORKAOUND
//            JSch.setConfig("StrictHostKeyChecking", "no");
            //something with aes encryption. No idea what this is
//            session.setConfig("cipher.s2c", "aes128-cbc,3des-cbc,blowfish-cbc");
//            session.setConfig("cipher.c2s", "aes128-cbc,3des-cbc,blowfish-cbc");
//            session.setConfig("CheckCiphers", "aes128-cbc");
//            session.setUserInfo();

//            HostKeyRepository hkr = jsch.getHostKeyRepository();
//            System.out.println(hkr);
//            for(HostKey hk : hkr.getHostKey()){
//                System.out.println("inloop");
//                if(hk.getHost().equals("")){
//                    String type = hk.getType();
//                    session.setConfig("server_host_key",type);
//                    System.out.println("found key");
//                }
//            }
//            session.setConfig("server_host_key", "ssh-ed25519");
            session.connect();
            session.setPortForwardingR(1234, "localhost", 22);
//            Channel channel=session.openChannel("shell");
//
//            channel.setInputStream(System.in);
//            channel.setOutputStream(System.out);
//
//            channel.connect();
            //https://stackoverflow.com/questions/2405885/run-a-command-over-ssh-with-jsch/11902536
            StringBuilder outputBuffer = new StringBuilder();
            Channel channel = session.openChannel("exec");
            ((ChannelExec)channel).setCommand("ifconfig | grep inet");//echo 5
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int readByte = commandOutput.read();

            while(readByte != 0xffffffff) {
                outputBuffer.append((char) readByte);
                readByte = commandOutput.read();
            }

            session.disconnect();
            System.out.println(outputBuffer.toString());
            System.out.println("nofail");
        } catch(JSchException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }
}

