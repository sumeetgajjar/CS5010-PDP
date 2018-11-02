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
    Assert.assertEquals("2", immutableListADT.get(1));
    Assert.assertEquals("3", immutableListADT.get(2));
    Assert.assertEquals("4", immutableListADT.get(3));

    Assert.assertEquals(4, immutableListADT.getSize());
  }

  @Test
  public void testGettingMutableListFromImmutableList() {
    ImmutableListADT<String> immutableListADT = getImmutableListADT();

    MutableListADT<String> mutableListAdt = immutableListADT.getMutableListAdt();
    Assert.assertEquals(mutableListAdt.get(0), immutableListADT.get(0));
    Assert.assertEquals(mutableListAdt.get(1), immutableListADT.get(1));
    Assert.assertEquals(mutableListAdt.get(2), immutableListADT.get(2));
    Assert.assertEquals(mutableListAdt.get(3), immutableListADT.get(3));

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

    Assert.assertEquals("1", immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());

    Assert.assertNotEquals(immutableListADT.get(0), integerImmutableListADT.get(0));

    Assert.assertEquals(immutableListADT.getSize(), integerImmutableListADT.getSize());
    Assert.assertEquals(Integer.valueOf(1), integerImmutableListADT.get(0));
    Assert.assertEquals(Integer.valueOf(2), integerImmutableListADT.get(1));
    Assert.assertEquals(Integer.valueOf(3), integerImmutableListADT.get(2));
    Assert.assertEquals(Integer.valueOf(4), integerImmutableListADT.get(3));

    try {
      integerImmutableListADT.add(0, -1);
      Assert.fail("should have failed");
    } catch (UnsupportedOperationException e) {
      Assert.assertEquals("operation not allowed", e.getMessage());
    }

    Assert.assertEquals(immutableListADT.getSize(), integerImmutableListADT.getSize());
    Assert.assertEquals(Integer.valueOf(1), integerImmutableListADT.get(0));
  }

  @Test
  public void testConstructingImmutableListUsingBuilder() {
    ImmutableListADT<Integer> immutableListADT = ImmutableListADTImpl.<Integer>getBuilder()
            .add(1)
            .add(2)
            .add(3)
            .add(4)
            .build();

    Assert.assertEquals(4, immutableListADT.getSize());
    Assert.assertEquals(Integer.valueOf(1), immutableListADT.get(0));
    Assert.assertEquals(Integer.valueOf(2), immutableListADT.get(1));
    Assert.assertEquals(Integer.valueOf(3), immutableListADT.get(2));
    Assert.assertEquals(Integer.valueOf(4), immutableListADT.get(3));

    try {
      immutableListADT.add(0, -1);
      Assert.fail("should have failed");
    } catch (UnsupportedOperationException e) {
      Assert.assertEquals("operation not allowed", e.getMessage());
    }

    Assert.assertEquals(immutableListADT.getSize(), immutableListADT.getSize());
    Assert.assertEquals(Integer.valueOf(1), immutableListADT.get(0));
  }

  @Test
  public void testMutabilityOnDataInImmutableList() {
    StringBuilder firstElementInList = new StringBuilder("a");
    ImmutableListADT<StringBuilder> immutableListADT =
            ImmutableListADTImpl.<StringBuilder>getBuilder()
                    .add(firstElementInList)
                    .add(new StringBuilder("c"))
                    .build();

    Assert.assertEquals(2, immutableListADT.getSize());
    Assert.assertEquals("a", immutableListADT.get(0).toString());

    firstElementInList.append("b");
    Assert.assertNotEquals("a", immutableListADT.get(0).toString());
  }

  @Test
  public void testConstructorOfImmutableList() {
    ListADT<String> listADT = getListADT();
    Assert.assertEquals(4, listADT.getSize());

    //constructing immutableListADT by using listADT
    ImmutableListADT<String> immutableListADT1 =
            new ImmutableListADTImpl<>(listADT);
    Assert.assertEquals(4, immutableListADT1.getSize());

    MutableListADT<String> mutableListAdt =
            immutableListADT1.getMutableListAdt();
    Assert.assertEquals(4, mutableListAdt.getSize());

    //constructing immutableListADT by using mutableListADT
    ImmutableListADTImpl<String> stringImmutableListADT2 =
            new ImmutableListADTImpl<>(mutableListAdt);
    Assert.assertEquals(4, stringImmutableListADT2.getSize());

    //constructing immutableListADT by using immutableListADT
    ImmutableListADTImpl<String> stringImmutableListADT3 =
            new ImmutableListADTImpl<>(immutableListADT1);
    Assert.assertEquals(4, stringImmutableListADT3.getSize());
  }

  @Test
  public void testToStringOfImmutableList() {
    ListADT<String> listADT = getListADT();
    ImmutableListADTImpl<String> immutableListADT = new ImmutableListADTImpl<>(listADT);
    Assert.assertEquals(listADT.toString(), immutableListADT.toString());
  }
}
