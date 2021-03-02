package femr.common.dtos;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceResponseTest {

    @Test
    public void hasErrors() {
        ServiceResponse<String> responseObject = new ServiceResponse<>();
        assertFalse(responseObject.hasErrors());
    }

    @Test
    public void getResponseObject() {
        ServiceResponse<String> responseObject = new ServiceResponse<>();
        assertNull(responseObject.getResponseObject());
    }

    @Test
    public void setResponseObject() {
        ServiceResponse<String> responseObject = new ServiceResponse<>();
        responseObject.setResponseObject("String Object");
        assertEquals("String Object", responseObject.getResponseObject());
    }

    @Test
    public void addError() {
        ServiceResponse<String> responseObject = new ServiceResponse<>();
        responseObject.addError("bad error", "bad");
        assertTrue(responseObject.hasErrors());
    }

    @Test
    public void getErrors() {
        ServiceResponse<String> responseObject = new ServiceResponse<>();
        responseObject.addError("bad error", "bad");
        assertEquals("bad", responseObject.getErrors().get("bad error"));
    }

    @Test
    public void getErrorsEmpty() {
        ServiceResponse<String> responseObject = new ServiceResponse<>();
        assertTrue(responseObject.getErrors().isEmpty());
    }
}
