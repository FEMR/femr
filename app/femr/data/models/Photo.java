package femr.data.models;
import femr.common.models.IPhoto;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "photos")
public class Photo implements IPhoto {
//basic information of a photo
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int _id;

    @Column(name = "description", length = 3072)
    private String _description;

    @Column(name = "file_path", length = 1024)
    private String _filePath;

    @Column(name = "insertTS", nullable = true)
    private Date _insertTS;

    @Override
    public int getId() {
        return _id;
    }

    @Override
    public void setId(int id) {
        _id = id;
    }

    @Override
    public String getDescription() {
        return _description;
    }

    @Override
    public void setDescription(String desc) {
        _description = desc;
    }

    @Override
    public String getFilePath() {
        return _filePath;
    }

    @Override
    public void setFilePath(String path) {
        _filePath = path;
    }

    @Override
    public Date getInsertTS() { return _insertTS;  }

    @Override
    public void   setInsertTS(Date dt) { _insertTS = dt; }
}
