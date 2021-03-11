package unit.app.femr.data.models.mysql;

import femr.data.models.mysql.KitStatus;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class KitStatusTest {
  KitStatus ks;

  @Before
  public void setUp() {
    ks = new KitStatus();
  }

  @Test
  public void checkId(){
    ks.setId(123);
    assertEquals(123, ks.getId());
  }

  @Test
  public void checkName(){
    ks.setName("KitA");
    assertEquals("KitA", ks.getName());
  }

  @Test
  public void checkValue(){
    ks.setValue("deployed");
    assertEquals("deployed", ks.getValue());
  }
}
