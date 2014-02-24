
package femr.common.models;


import java.util.Date;

public interface IPhoto {
    /*id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT ,
    description VARCHAR(3072) NOT NULL,
    file_path VARCHAR(1024) NOT NULL*/

    int getId();
    void setId(int id);

    String getDescription();
    void   setDescription(String desc);

    String getFilePath();
    void   setFilePath(String path);

    Date   getInsertTS();
    void   setInsertTS(Date dt);

}
