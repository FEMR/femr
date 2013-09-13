package femr.business.dtos;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponse<T> {
    private boolean valid;
    private T responseObject;
    private Map<String, String> errors;

    public ServiceResponse() {
        this.valid = true;
        this.responseObject = null;
        this.errors = new HashMap<>();
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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
