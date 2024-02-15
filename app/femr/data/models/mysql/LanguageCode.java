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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
