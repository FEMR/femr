package femr.common.dto;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponse<T> {
    private T responseObject;
    private Map<String, String> errors;

    public ServiceResponse() {
        this.responseObject = null;
        this.errors = new HashMap<>();

    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public T getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(T responseObject) {
        this.responseObject = responseObject;
    }

    public void addError(String field, String error) {
        errors.put(field, error);
    }

       public Map<String, String> getErrors() {
        return errors;
    }
}
