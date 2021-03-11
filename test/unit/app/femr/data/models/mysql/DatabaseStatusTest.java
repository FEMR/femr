package unit.app.femr.data.models.mysql;

import femr.data.models.mysql.DatabaseStatus;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DatabaseStatusTest {
  DatabaseStatus ds;

  @Before
  public void setUp() {
    ds = new DatabaseStatus();
  }

  @Test
  public void checkId(){
    ds.setId(123);
    assertEquals(123, ds.getId());
  }

  @Test
  public void checkName(){
    ds.setName("very cool database");
    assertEquals("very cool database", ds.getName());
  }

  @Test
  public void checkValue(){
    ds.setValue("connected");
    assertEquals("connected", ds.getValue());
  }
}
