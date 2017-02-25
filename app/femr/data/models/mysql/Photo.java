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
package femr.data.models.mysql;

import femr.data.models.core.IPhoto;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "photos")
public class Photo implements IPhoto {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int _id;
    @Column(name = "description", length = 3072)
    private String _description;
    @Column(name = "file_path", length = 1024)
    private String _filePath;
    @Column(name = "insertTS", nullable = true)
    private Date _insertTS;

    @Lob
    @Basic(fetch=FetchType.LAZY) //Lazy load to prevent downloading full image unless needed
    @Column(name = "photo")
    private byte[] _photo;

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

    @Override
    public byte[]   getPhotoBlob() { return _photo; }

    @Override
    public void   setPhotoBlob(byte[] photo) { _photo = photo; }

}
