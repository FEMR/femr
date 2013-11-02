package femr.business.services;

import com.google.inject.Inject;
import femr.data.daos.IRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalService implements IMedicalService{

    @Inject
    public MedicalService(){

    }

    @Override
    public String getCurrentDateTime(){
        Date dt = new Date();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateTimeFormat.format(dt);
        return currentTime;
    }
}
