package femr.business.dtos;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponse<T> {
    private T responseObject;
    private Map<String, String> errors;
    private int resultSize;

    public ServiceResponse() {
        this.responseObject = null;
        this.errors = new HashMap<>();
        this.resultSize = 0;
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

    public int getSizeSearchResult (){
        return resultSize;
    }

    public void setSizeSearchResult (int size){
         this.resultSize = size;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
