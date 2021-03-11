package unit.app.femr.data.models.mysql;

import femr.data.models.mysql.NetworkStatus;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NetworkStatusTest {
  NetworkStatus nt;

  @Before
  public void setUp() {
    nt = new NetworkStatus();
  }

  @Test
  public void checkId(){
    nt.setId(123);
    assertEquals(123, nt.getId());
  }

  @Test
  public void checkName(){
    nt.setName("NetworkA");
    assertEquals("NetworkA", nt.getName());
  }

  @Test
  public void checkValue(){
    nt.setValue("connected");
    assertEquals("connected", nt.getValue());
  }

}
