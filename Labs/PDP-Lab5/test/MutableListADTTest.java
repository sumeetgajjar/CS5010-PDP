import org.junit.Assert;
import org.junit.Test;

import listadt.mutablelistadt.MutableListADT;
import listadt.mutablelistadt.MutableListADTImpl;

/**
 * A Junit class to test MutableListADT.
 */
public class MutableListADTTest {

  @Test
  public void testInitializationOfMutableList() {
    MutableListADT<String> mutableListADT = new MutableListADTImpl<>();
    Assert.assertEquals(0, mutableListADT.getSize());

    try {
      mutableListADT.get(0);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid index", e.getMessage());
    }
  }

  @Test
  public void testModifyingMutableList() {
    MutableListADT<String> mutableListADT = new MutableListADTImpl<>();

    mutableListADT.add(0, "1");
    Assert.assertEquals(1, mutableListADT.getSize());
    Assert.assertEquals("1", mutableListADT.get(0));

    mutableListADT.addFront("-1");
    Assert.assertEquals(2, mutableListADT.getSize());
    Assert.assertEquals("-1", mutableListADT.get(0));

    mutableListADT.addBack("2");
    Assert.assertEquals(3, mutableListADT.getSize());
    Assert.assertEquals("2", mutableListADT.get(2));

    mutableListADT.remove("1");
    Assert.assertEquals(2, mutableListADT.getSize());
    Assert.assertEquals("2", mutableListADT.get(1));
  }
}