import org.junit.Assert;
import org.junit.Test;

import listadt.ListADT;
import listadt.ListADTImpl;
import listadt.immutablelistadt.ImmutableListADT;
import listadt.immutablelistadt.ImmutableListADTImpl;
import listadt.mutablelistadt.MutableListADT;

/**
 * A Junit class to test various types of List.
 */
public class ListADTTest {

  private ListADT<String> getListADT() {
    ListADT<String> listADT = new ListADTImpl<>();
    listADT.add(0, "1");
    listADT.add(1, "2");
    listADT.add(2, "3");
    listADT.add(3, "4");
    return listADT;
  }

  @Test
  public void testReadOnlyOperationsOfImmutableList() {
    ImmutableListADT<String> immutableListADT = new ImmutableListADTImpl<>(getListADT());

    Assert.assertEquals("1", immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());
  }

  @Test
  public void testGettingMutableListFromImmutableList() {
    ImmutableListADT<String> immutableListADT = new ImmutableListADTImpl<>(getListADT());

    MutableListADT<String> mutableListAdt = immutableListADT.getMutableListAdt();
    Assert.assertEquals(mutableListAdt.get(0), immutableListADT.get(0));
    Assert.assertEquals(mutableListAdt.getSize(), immutableListADT.getSize());
  }

  @Test
  public void testAddFailureOnImmutableList() {
    ImmutableListADT<String> immutableListADT = new ImmutableListADTImpl<>(getListADT());
    Assert.assertEquals("1", immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());
    try {
      immutableListADT.add(0, "5");
      Assert.fail("should have failed");
    } catch (UnsupportedOperationException e) {
      Assert.assertEquals("operation not allowed", e.getMessage());
    }
    Assert.assertEquals("1", immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());
  }

  @Test
  public void testAddBackFailureOnImmutableList() {
    ImmutableListADT<String> immutableListADT = new ImmutableListADTImpl<>(getListADT());
    Assert.assertEquals("4", immutableListADT.get(3));
    Assert.assertEquals(4, immutableListADT.getSize());
    try {
      immutableListADT.addBack("5");
      Assert.fail("should have failed");
    } catch (UnsupportedOperationException e) {
      Assert.assertEquals("operation not allowed", e.getMessage());
    }
    Assert.assertEquals("4", immutableListADT.get(3));
    Assert.assertEquals(4, immutableListADT.getSize());
  }

  @Test
  public void testAddFrontFailureOnImmutableList() {
    ImmutableListADT<String> immutableListADT = new ImmutableListADTImpl<>(getListADT());
    Assert.assertEquals("1", immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());
    try {
      immutableListADT.addFront("5");
      Assert.fail("should have failed");
    } catch (UnsupportedOperationException e) {
      Assert.assertEquals("operation not allowed", e.getMessage());
    }
    Assert.assertEquals("1", immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());
  }

  @Test
  public void testRemoveFailureOnImmutableList() {
    ImmutableListADT<String> immutableListADT = new ImmutableListADTImpl<>(getListADT());
    Assert.assertEquals("4", immutableListADT.get(3));
    Assert.assertEquals(4, immutableListADT.getSize());
    try {
      immutableListADT.remove("4");
      Assert.fail("should have failed");
    } catch (UnsupportedOperationException e) {
      Assert.assertEquals("operation not allowed", e.getMessage());
    }
    Assert.assertEquals("4", immutableListADT.get(3));
    Assert.assertEquals(4, immutableListADT.getSize());
  }
}
