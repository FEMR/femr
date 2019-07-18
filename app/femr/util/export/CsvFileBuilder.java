package femr.util.export;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import femr.business.helpers.LogicDoer;
import femr.common.models.ResearchExportItem;
import femr.util.stringhelpers.CSVWriterGson;
import femr.util.stringhelpers.GsonFlattener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CsvFileBuilder {

    /**
     * Creates a csv file from a list of ResearchExportItems
     *
     * @param encounters the encounters to be in the file
     * @return a csv formatted file of the encounters
     */
    public static File createCsvFile(List<ResearchExportItem> encounters){

        // Make File and get path
        String csvFilePath = LogicDoer.getCsvFilePath();
        //Ensure folder exists, if not, create it
        File f = new File(csvFilePath);
        if (!f.exists())
            f.mkdirs();

        // trailing slash is included in path
        //CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        SimpleDateFormat format = new SimpleDateFormat("MMddyy-HHmmss");
        String timestamp = format.format(new Date());
        String csvFileName = csvFilePath+"export-"+timestamp+".csv";
        File eFile = new File(csvFileName);
        boolean fileCreated = false;
        if(!eFile.exists()) {
            try {
                fileCreated = eFile.createNewFile();
            }
            catch( IOException e ){

                e.printStackTrace();
            }
        }

        if( fileCreated ) {

            Gson gson = new Gson();
            JsonParser gsonParser = new JsonParser();
            String jsonString = gson.toJson(encounters);

            GsonFlattener parser = new GsonFlattener();
            CSVWriterGson writer = new CSVWriterGson();

            try {

                List<Map<String, String>> flatJson = parser.parse(gsonParser.parse(jsonString).getAsJsonArray());
                writer.writeAsCSV(flatJson, csvFileName);

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
        }

        return eFile;
    }
}
