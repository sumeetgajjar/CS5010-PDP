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

  private ImmutableListADTImpl<String> getImmutableListADT() {
    return new ImmutableListADTImpl<>(getListADT());
  }

  @Test
  public void testReadOnlyOperationsOfImmutableList() {
    ImmutableListADT<String> immutableListADT = getImmutableListADT();

    Assert.assertEquals("1", immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());
  }

  @Test
  public void testGettingMutableListFromImmutableList() {
    ImmutableListADT<String> immutableListADT = getImmutableListADT();

    MutableListADT<String> mutableListAdt = immutableListADT.getMutableListAdt();
    Assert.assertEquals(mutableListAdt.get(0), immutableListADT.get(0));
    Assert.assertEquals(mutableListAdt.getSize(), immutableListADT.getSize());
  }

  @Test
  public void testGettingImmutableListFromMutableListOfImmutableList() {
    ImmutableListADT<String> immutableListADT1 = getImmutableListADT();
    MutableListADT<String> mutableListAdt = immutableListADT1.getMutableListAdt();
    ImmutableListADT<String> immutableListADT2 = mutableListAdt.getImmutableListAdt();

    Assert.assertEquals("4", immutableListADT2.get(3));
    Assert.assertEquals(4, immutableListADT2.getSize());
    try {
      immutableListADT2.addBack("5");
      Assert.fail("should have failed");
    } catch (UnsupportedOperationException e) {
      Assert.assertEquals("operation not allowed", e.getMessage());
    }
    Assert.assertEquals("4", immutableListADT2.get(3));
    Assert.assertEquals(4, immutableListADT2.getSize());
  }

  @Test
  public void testMutatingMutableListObtainedFromImmutableListDoesNotMutateImmutableList() {
    ImmutableListADT<String> immutableListADT = getImmutableListADT();
    MutableListADT<String> mutableListAdt = immutableListADT.getMutableListAdt();

    mutableListAdt.add(4, "5");
    Assert.assertEquals("5", mutableListAdt.get(4));
    Assert.assertEquals(5, mutableListAdt.getSize());

    Assert.assertEquals(4, immutableListADT.getSize());

    try {
      immutableListADT.get(4);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid index", e.getMessage());
    }

    mutableListAdt.addFront("-1");
    Assert.assertEquals("-1", mutableListAdt.get(0));
    Assert.assertEquals(6, mutableListAdt.getSize());

    String expectedFirstElementOfImmutableList = "0";
    Assert.assertEquals(expectedFirstElementOfImmutableList, immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());

    try {
      immutableListADT.get(4);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid index", e.getMessage());
    }

    mutableListAdt.addBack("6");
    Assert.assertEquals("6", mutableListAdt.get(6));
    Assert.assertEquals(7, mutableListAdt.getSize());

    String expectedLastElementOfImmutableList = "4";
    Assert.assertEquals(expectedLastElementOfImmutableList, immutableListADT.get(3));
    Assert.assertEquals(4, immutableListADT.getSize());

    try {
      mutableListAdt.get(4);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid index", e.getMessage());
    }

    mutableListAdt.remove("0");
    Assert.assertEquals(6, mutableListAdt.getSize());

    expectedFirstElementOfImmutableList = "0";
    Assert.assertEquals(expectedFirstElementOfImmutableList, immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());
  }

  @Test
  public void testAddFailureOnImmutableList() {
    ImmutableListADT<String> immutableListADT = getImmutableListADT();
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
    ImmutableListADT<String> immutableListADT = getImmutableListADT();
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
    ImmutableListADT<String> immutableListADT = getImmutableListADT();
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
    ImmutableListADT<String> immutableListADT = getImmutableListADT();
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

  @Test
  public void testMapOfImmutableList() {
    ImmutableListADT<String> immutableListADT = getImmutableListADT();
    ImmutableListADT<Integer> integerImmutableListADT = immutableListADT.map(Integer::parseInt);


  }
}
