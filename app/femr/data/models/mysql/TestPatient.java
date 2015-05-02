package femr.data.models.mysql;

import femr.data.models.core.IPatient;
import femr.data.models.core.IPhoto;

import java.util.Date;

/**
 * Created by kevin on 5/3/15.
 */
public class TestPatient implements IPatient {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getUserId() {
        return 0;
    }

    @Override
    public void setUserId(int userId) {

    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public void setFirstName(String firstName) {

    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public void setLastName(String lastName) {

    }

    @Override
    public Date getAge() {
        return null;
    }

    @Override
    public void setAge(Date age) {

    }

    @Override
    public String getSex() {
        return null;
    }

    @Override
    public void setSex(String sex) {

    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public void setAddress(String address) {

    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public void setCity(String city) {

    }

    @Override
    public void setId(int id) {

    }

    @Override
    public IPhoto getPhoto() {
        return null;
    }

    @Override
    public void setPhoto(IPhoto photo) {

    }
}
