package mock.femr.data.models;
import femr.data.models.core.IPhoto;
import java.util.Date;

public class MockPhoto implements IPhoto {

    private String contentType;
    private byte[] photoData;

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id){

    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void setDescription(String desc) {

    }

    @Override
    public String getFilePath() {
        return "";
    }

    @Override
    public void setFilePath(String path) {

    }

    @Override
    public Date getInsertTS() {
        return null;
    }

    @Override
    public void setInsertTS(Date dt) {

    }

    @Override
    public byte[] getPhotoBlob() {
        return new byte[0];
    }

    @Override
    public void setPhotoBlob(byte[] photo) {

    }

    @Override
    public String getLanguageCode() {
        return "";
    }

    @Override
    public void setLanguageCode(String languageCode) {

    }

    @Override
    public String getContentType() {
        return "";
    }

    @Override
    public byte[] getPhotoData() {
        return new byte[0];
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

}