package femr.business.dtos;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponse<T> {
    private boolean successful;
    private T responseObject;
    private Map<String, String> errors;

    public ServiceResponse() {
        this.successful = true;
        this.responseObject = null;
        this.errors = new HashMap<>();
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isNullResponse() {
        return responseObject == null;
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
