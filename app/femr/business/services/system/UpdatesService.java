/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.business.services.system;

import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IUpdatesService;
import femr.common.dtos.ServiceResponse;
import femr.data.daos.IRepository;
import femr.data.models.core.IKitStatus;
import femr.data.models.core.INetworkStatus;
import femr.data.models.core.IDatabaseStatus;
import femr.data.models.core.ILanguageCode;
import femr.data.models.mysql.*;
import femr.ui.controllers.BackEndControllerHelper;
import io.ebean.ExpressionList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.UnresolvedPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UpdatesService implements IUpdatesService {

    private final IRepository<INetworkStatus> networkStatusRepository;
    private final IRepository<IKitStatus> kitStatusRepository;
    private final IRepository<IDatabaseStatus> databaseStatusRepository;
    private final IRepository<ILanguageCode> languagesRepository;

    @Inject
    public UpdatesService(IRepository<INetworkStatus> networkStatusRepository,
                          IRepository<IKitStatus> kitStatusRepository,
                          IRepository<IDatabaseStatus> databaseStatusRepository,
                          IRepository<ILanguageCode> languagesRepository) {
        this.networkStatusRepository = networkStatusRepository;
        this.kitStatusRepository = kitStatusRepository;
        this.databaseStatusRepository = databaseStatusRepository;
        this.languagesRepository = languagesRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends INetworkStatus>> retrieveNetworkStatuses() {
        ServiceResponse<List<? extends INetworkStatus>> response = new ServiceResponse<>();
        try {
            List<? extends INetworkStatus> networkStatuses = networkStatusRepository.findAll(NetworkStatus.class);
            response.setResponseObject(networkStatuses);

        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public  ServiceResponse<List<? extends INetworkStatus>> updateNetworkStatuses() {
        ServiceResponse<List<? extends INetworkStatus>> response = new ServiceResponse<>();
        ArrayList<String> data = new ArrayList<>();
        try {
            data = BackEndControllerHelper.executeSpeedTestScript("speedtest/sptest.py");
            //Update Status
            Float Ping = Float.parseFloat(data.get(2));
            String updatedStatus = "Connection stable";
            if(Ping <= 0){
                updatedStatus = "Connection unavailable";
            }
            INetworkStatus status = retrieveNetworkStatuses().getResponseObject().get(0);
            status.setValue(updatedStatus);
            networkStatusRepository.update(status);

            //Update Download
            String updatedDownload = data.get(0);
            INetworkStatus download = retrieveNetworkStatuses().getResponseObject().get(1);
            download.setValue(updatedDownload);
            networkStatusRepository.update(download);

            //Update Upload
            String updatedUpload = data.get(1);
            INetworkStatus upload = retrieveNetworkStatuses().getResponseObject().get(2);
            upload.setValue(updatedUpload);
            networkStatusRepository.update(upload);

            //Update Ping
            String updatedPing = data.get(2);
            INetworkStatus ping = retrieveNetworkStatuses().getResponseObject().get(3);
            ping.setValue(updatedPing);
            networkStatusRepository.update(ping);

            //Update Date
            String updatedDate = java.time.LocalDate.now().toString().replace("-", ".");
            INetworkStatus date = retrieveNetworkStatuses().getResponseObject().get(4);
            date.setValue(updatedDate);
            networkStatusRepository.update(date);
        } catch (Exception e) {
            response.addError("Network status", e.toString());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends IKitStatus>> retrieveKitStatuses() {
        ServiceResponse<List<? extends IKitStatus>> response = new ServiceResponse<>();
        try {
            List<? extends IKitStatus> kitStatuses = kitStatusRepository.findAll(KitStatus.class);
            response.setResponseObject(kitStatuses);

        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends IKitStatus>> updateKitStatuses() {
        ServiceResponse<List<? extends IKitStatus>> response = new ServiceResponse<>();
        try {
            BackEndControllerHelper.executePythonScript("s3scripts/download.py");
            String updatedDate = java.time.LocalDate.now().toString().replace("-", ".");
            IKitStatus kitStatusDate = retrieveKitStatuses().getResponseObject().get(2);
            kitStatusDate.setValue(updatedDate);
            kitStatusRepository.update(kitStatusDate);
        } catch (Exception e) {
            response.addError("Kit update", e.toString());
            e.printStackTrace();
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends IDatabaseStatus>> retrieveDatabaseStatuses() {
        ServiceResponse<List<? extends IDatabaseStatus>> response = new ServiceResponse<>();
        try {
            List<? extends IDatabaseStatus> databaseStatuses = databaseStatusRepository.findAll(DatabaseStatus.class);
            response.setResponseObject(databaseStatuses);

        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<? extends IDatabaseStatus>> updateDatabaseStatuses() {
        ServiceResponse<List<? extends IDatabaseStatus>> response = new ServiceResponse<>();
        //TODO: Do some more robust error checking
        String[] cmd = new String[]{"/bin/bash", "femr.sh"};
        String workingDir = System.getProperty("user.dir");
        File dir = new File(workingDir, "app/femr/util/backup");
        try {
            Process pr = Runtime.getRuntime().exec(cmd, null, new File("/Users/yashsatyavarpu/Documents/super-femr/app/femr/util/backup"));
            String updated_date = java.time.LocalDate.now().toString().replace("-", ".");
            DatabaseStatus databaseStatus = new DatabaseStatus();
            databaseStatus.setName("Last Backup");
            databaseStatus.setValue(updated_date);
            databaseStatusRepository.update(databaseStatus);
        } catch (IOException e) {
            response.addError("Database update", e.toString());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends ILanguageCode>> retrieveLanguages() {
        ServiceResponse<List<? extends ILanguageCode>> response = new ServiceResponse<>();
        try{
            List<? extends ILanguageCode> languages = languagesRepository.findAll(LanguageCode.class);
            response.setResponseObject(languages);
        } catch (Exception ex){
            response.addError("", ex.getMessage());
        }
        return response;
    }

    List<LanguageCode> getOptimizedLanguages(){
        ExpressionList<LanguageCode> query = QueryProvider.getLanguage()
                .where()
                .eq("status", "Optimized");
        return query.findList();
    }

    void setLanguageOptimized(String code){
        ExpressionList<LanguageCode> query = QueryProvider.getLanguage()
                .where()
                .eq("code", code);
        LanguageCode lang = query.findOne();
        if(lang != null){
            lang.setStatus("Optimized");
            lang.setUpdateScheduled(false);
            languagesRepository.update(lang);
        }
    }

    @Override
    public ServiceResponse<List<? extends ILanguageCode>> updateLanguage(String code, boolean updateScheduled){
        ServiceResponse<List<? extends ILanguageCode>> response = new ServiceResponse<>();

        try{
            ExpressionList<LanguageCode> query = QueryProvider.getLanguage()
                    .where()
                    .eq("code", code);
            LanguageCode langToUpdate = query.findOne();

            if(langToUpdate != null) {
                langToUpdate.setUpdateScheduled(updateScheduled);
                languagesRepository.update(langToUpdate);
            } else{
                response.addError("", "Language not found in DB");
            }

        } catch(Exception ex){
            response.addError("", ex.getMessage());
        }

        return response;
    }

    @Override
    public ServiceResponse<List<? extends ILanguageCode>> downloadPackages(String langCode){
        ServiceResponse<List<? extends ILanguageCode>> response = new ServiceResponse<>();
        try {
            List<LanguageCode> optimizedLanguages = getOptimizedLanguages();
            for (ILanguageCode optLang : optimizedLanguages) {
                System.out.println("Updating " + optLang.getCode() + " and " + langCode);
                ProcessBuilder pb = new ProcessBuilder("python",
                        "translator/optimizeLanguage.py", langCode, optLang.getCode());
                Process p = pb.start();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
                while(!bfr.readLine().contains("Optimized"));

                System.out.println(langCode + " and " + optLang.getCode() + " Optimized");
            }
            setLanguageOptimized(langCode);
        } catch (IOException e) {
            response.addError("", e.getMessage());
        }
        return response;
    }

    public ServiceResponse<List<? extends ILanguageCode>> initializeLanguages() {
        ServiceResponse<List<? extends ILanguageCode>> response = new ServiceResponse<>();
        try {
            languagesRepository.delete(languagesRepository.findAll(LanguageCode.class));
            ProcessBuilder pb = new ProcessBuilder("python", "translator/libargos.py");
            Process p = pb.start();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            String line;
            while ((line = bfr.readLine()) != null) {
                LanguageCode language = new LanguageCode();
                language.setCode(line.split(", ")[0]);
                language.setLanguageName(line.split(", ")[1]);
                language.setStatus("Not Optimized");
                language.setUpdateScheduled(false);
                if(language.getCode().equals("en") || language.getCode().equals("es")){
                    language.setStatus("Optimized");
                }
                languagesRepository.update(language);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}

