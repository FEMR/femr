package femr.data.models.mysql;

import femr.data.models.core.ILanguageCode;
import javax.persistence.*;

@Entity
@Table(name = "language_codes")
public class LanguageCode implements ILanguageCode {
    @Id
    @Column(name = "code", nullable = false, length = 5)
    private String code;

    @Column(name = "language_name", nullable = false, length = 64)
    private String languageName;

    @Column(name = "status", length = 64)
    private String status;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getLanguageName() {
        return languageName;
    }

    @Override
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
    @Override
    public String getStatus()  {return status;}

    @Override
    public void setStatus(String status) { this.status = status;}

    @Override
    public int compareTo(ILanguageCode b) {
        return this.getStatus().compareTo(b.getStatus());
    }
}
