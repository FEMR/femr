package femr.data.models.core;

public interface ILanguageCode extends Comparable<ILanguageCode>{

    String getCode();

    void setCode(String code);

    String getLanguageName();

    void setLanguageName(String languageName);

    String getStatus();

    void setStatus(String status);

    Boolean getUpdateScheduled();

    void setUpdateScheduled(Boolean updateScheduled);

}
