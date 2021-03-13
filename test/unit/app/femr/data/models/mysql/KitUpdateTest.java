package unit.app.femr.data.models.mysql;

import femr.data.models.mysql.KitUpdate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class KitUpdateTest {
  KitUpdate ku;

  @Before
  public void setUp() {
    ku = new KitUpdate();
  }

  @Test
  public void checkId(){
    ku.setId(123);
    assertEquals(123, ku.getId());
  }

  @Test
  public void checkName(){
    ku.setName("new schema update");
    assertEquals("new schema update", ku.getName());
  }

  @Test
  public void checkValue(){
    ku.setValue("completed");
    assertEquals("completed", ku.getValue());
  }
}
