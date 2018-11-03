import org.junit.Assert;
import org.junit.Test;

import listadt.ListADT;
import listadt.ListADTImpl;
import listadt.immutablelistadt.ImmutableListADT;
import listadt.immutablelistadt.ImmutableListADTImpl;
import listadt.mutablelistadt.MutableListADT;

/**
 * A Junit class to test ImmutableListADT.
 */
public class ImmutableListADTTest {

  @Test
  public void testInitializationOfImmutableList() {
    ImmutableListADT<String> mutableListADT = ImmutableListADTImpl.<String>getBuilder().build();
    Assert.assertEquals(0, mutableListADT.getSize());

    try {
      mutableListADT.get(0);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid index", e.getMessage());
    }
  }

  @Test
  public void testReadOnlyOperationsOfImmutableList() {
    ImmutableListADT<String> immutableListADT = getImmutableListADT();

    //demo to get the ith element of ImmutableList.
    for (int i = 0; i < immutableListADT.getSize(); i++) {
      Assert.assertEquals(String.valueOf(i + 1), immutableListADT.get(i));
    }

    //demo to get the size of ImmutableList.
    Assert.assertEquals(4, immutableListADT.getSize());

    //demo to get the string representation of the list.
    Assert.assertEquals("(1 2 3 4)", immutableListADT.toString());
  }

  @Test
  public void testGettingMutableListFromImmutableList() {
    ImmutableListADT<String> immutableListADT = getImmutableListADT();

    //demo to get MutableList from the given ImmutableList.
    MutableListADT<String> mutableListAdt = immutableListADT.getMutableListADT();
    for (int i = 0; i < immutableListADT.getSize(); i++) {
      Assert.assertEquals(mutableListAdt.get(i), immutableListADT.get(i));
    }

    Assert.assertEquals(mutableListAdt.getSize(), immutableListADT.getSize());
  }

  @Test
  public void testGettingImmutableListFromMutableListOfImmutableList() {
    ImmutableListADT<String> immutableListADT1 = getImmutableListADT();
    MutableListADT<String> mutableListAdt = immutableListADT1.getMutableListADT();
    ImmutableListADT<String> immutableListADT2 = mutableListAdt.getImmutableListADT();

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

    /*
    demo to show mutating the MutableList of the given ImmutableList will not mutate the
    ImmutableList.
    */
    MutableListADT<String> mutableListAdt = immutableListADT.getMutableListADT();

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

    String expectedFirstElementOfImmutableList = "1";
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
      immutableListADT.get(4);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid index", e.getMessage());
    }

    mutableListAdt.remove("1");
    Assert.assertEquals(6, mutableListAdt.getSize());

    expectedFirstElementOfImmutableList = "1";
    Assert.assertEquals(expectedFirstElementOfImmutableList, immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());
  }

  @Test
  public void testAddFailureOnImmutableList() {
    ImmutableListADT<String> immutableListADT = getImmutableListADT();
    Assert.assertEquals("1", immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());

    //demo to show updating the ImmutableList is not allowed.
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

    //demo to show updating the ImmutableList is not allowed.
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

    //demo to show updating the ImmutableList is not allowed.
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

    //demo to show updating the ImmutableList is not allowed.
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

    //demo to map the immutableList of Type T to immutableList of Type R.
    ImmutableListADT<Integer> integerImmutableListADT = immutableListADT.map(Integer::parseInt);

    Assert.assertEquals("1", immutableListADT.get(0));
    Assert.assertEquals(4, immutableListADT.getSize());

    Assert.assertNotEquals(immutableListADT.get(0), integerImmutableListADT.get(0));

    Assert.assertEquals(immutableListADT.getSize(), integerImmutableListADT.getSize());
    for (int i = 0; i < immutableListADT.getSize(); i++) {
      Assert.assertEquals(Integer.valueOf(i + 1), integerImmutableListADT.get(i));
      Assert.assertEquals(String.valueOf(i + 1), immutableListADT.get(i));
    }

    try {
      integerImmutableListADT.add(0, -1);
      Assert.fail("should have failed");
    } catch (UnsupportedOperationException e) {
      Assert.assertEquals("operation not allowed", e.getMessage());
    }

    try {
      integerImmutableListADT.remove(1);
      Assert.fail("should have failed");
    } catch (UnsupportedOperationException e) {
      Assert.assertEquals("operation not allowed", e.getMessage());
    }

    Assert.assertEquals(immutableListADT.getSize(), integerImmutableListADT.getSize());
    Assert.assertEquals(Integer.valueOf(1), integerImmutableListADT.get(0));
  }

  @Test
  public void testConstructingImmutableListUsingBuilder() {

    ListADT<Integer> listADT = new ListADTImpl<>();
    listADT.addBack(5);
    listADT.addBack(6);

    //demo to show constructing of ImmutableList using builder.
    ImmutableListADT<Integer> immutableListADT = ImmutableListADTImpl.<Integer>getBuilder()
            .add(1)
            .add(2)
            .add(3)
            .add(4)
            .addAll(listADT)
            .build();

    Assert.assertEquals(6, immutableListADT.getSize());
    for (int i = 0; i < 6; i++) {
      Assert.assertEquals(Integer.valueOf(i + 1), immutableListADT.get(i));
    }

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
  public void testMutatingListADTGivenToBuilderDoesNotMutateImmutableList() {
    ListADT<String> listADT = getListADT();
    Assert.assertEquals(4, listADT.getSize());

    int expectedSizeOfImmutableList = 4;
    ImmutableListADT<String> immutableListADT =
            ImmutableListADTImpl.<String>getBuilder().addAll(listADT).build();
    Assert.assertEquals(expectedSizeOfImmutableList, immutableListADT.getSize());

    listADT.addBack("5");
    Assert.assertEquals(5, listADT.getSize());

    Assert.assertEquals(expectedSizeOfImmutableList, immutableListADT.getSize());
    for (int i = 0; i < expectedSizeOfImmutableList; i++) {
      Assert.assertEquals(String.valueOf(i + 1), immutableListADT.get(i));
    }
  }

  @Test
  public void testToStringOfImmutableList() {
    ListADT<String> listADT = getListADT();
    ImmutableListADT<String> immutableListADT = getImmutableListADT();
    Assert.assertEquals(listADT.toString(), immutableListADT.toString());
  }

  private ImmutableListADT<String> getImmutableListADT() {
    ListADT<String> listADT = getListADT();
    return ImmutableListADTImpl.<String>getBuilder().addAll(listADT).build();
  }

  private ListADT<String> getListADT() {
    ListADT<String> listADT = new ListADTImpl<>();
    listADT.add(0, "1");
    listADT.add(1, "2");
    listADT.add(2, "3");
    listADT.add(3, "4");
    return listADT;
  }
}
