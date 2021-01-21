package femr.ui.models.search.json;

public class PatientSearch {
    //an object designed to represent a patient while a user is searching
    String id;
    String firstName;
    String lastName;
    String phoneNumber;
    String age;
    String photo;//the file location

    public void setId(String id){
        this.id = id;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; }
    public void setAge(String age){
        this.age = age;
    }
    public void setPhoto(String photo){
        this.photo = photo;
    }


}
