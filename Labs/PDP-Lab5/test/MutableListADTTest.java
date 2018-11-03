import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;
import java.util.function.Function;

import listadt.ListADT;
import listadt.ListADTImpl;
import listadt.immutablelistadt.ImmutableListADT;
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

  @Test
  public void testToStringForMutableList() {
    MutableListADT<String> mutableListADT = new MutableListADTImpl<>();
    mutableListADT.addBack("1");
    mutableListADT.addBack("2");
    Assert.assertEquals("(1 2)", mutableListADT.toString());
  }

  @Test
  public void testMapOfMutableList() {
    MutableListADT<String> mutableListADT = new MutableListADTImpl<>();
    mutableListADT.addBack("1");
    mutableListADT.addBack("2");

    MutableListADT<Integer> integerMutableListADT = mutableListADT.map(Integer::parseInt);
    Assert.assertEquals(integerMutableListADT.getSize(), mutableListADT.getSize());

    for (int i = 0; i < mutableListADT.getSize(); i++) {
      Assert.assertEquals(Integer.valueOf(i + 1), integerMutableListADT.get(i));
      Assert.assertEquals(String.valueOf(i + 1), mutableListADT.get(i));
    }

    Assert.assertEquals("(1 2)", mutableListADT.toString());

    integerMutableListADT.addFront(-1);
    Assert.assertEquals(Integer.valueOf(-1), integerMutableListADT.get(0));
    Assert.assertEquals(3, integerMutableListADT.getSize());

    integerMutableListADT.add(3, 3);
    Assert.assertEquals(Integer.valueOf(2), integerMutableListADT.get(2));
    Assert.assertEquals(4, integerMutableListADT.getSize());

    integerMutableListADT.addBack(4);
    Assert.assertEquals(Integer.valueOf(4), integerMutableListADT.get(4));
    Assert.assertEquals(5, integerMutableListADT.getSize());
  }

  @Test
  public void testGettingImmutableListFromMutableList() {
    MutableListADT<String> mutableListADT = new MutableListADTImpl<>();
    mutableListADT.addBack("1");
    mutableListADT.addBack("2");

    ImmutableListADT<String> immutableListAdt = mutableListADT.getImmutableListADT();
    Assert.assertEquals(mutableListADT.getSize(), immutableListAdt.getSize());
    for (int i = 0; i < immutableListAdt.getSize(); i++) {
      Assert.assertEquals(mutableListADT.get(i), immutableListAdt.get(i));
    }

    try {
      immutableListAdt.addFront("3");
    } catch (UnsupportedOperationException e) {
      Assert.assertEquals("operation not allowed", e.getMessage());
    }
  }

  @Test
  public void testGettingMutableListFromImmutableListOfMutableList() {
    MutableListADT<String> mutableListADT1 = new MutableListADTImpl<>();
    mutableListADT1.addBack("1");
    mutableListADT1.addBack("2");
    Assert.assertEquals(2, mutableListADT1.getSize());

    ImmutableListADT<String> immutableListADT1 = mutableListADT1.getImmutableListADT();

    MutableListADT<String> mutableListADT2 = immutableListADT1.getMutableListADT();
    mutableListADT2.addBack("3");
    Assert.assertEquals(3, mutableListADT2.getSize());

    Assert.assertEquals(2, mutableListADT1.getSize());

    mutableListADT2.remove("2");
    Assert.assertEquals("3", mutableListADT2.get(1));
    Assert.assertEquals("2", mutableListADT1.get(1));
  }

  @Test
  public void testMutatingMutableListDoesNotMutateItsImmutableList() {
    MutableListADT<String> mutableListADT = new MutableListADTImpl<>();
    mutableListADT.addBack("1");
    mutableListADT.addBack("2");

    ImmutableListADT<String> immutableListADT = mutableListADT.getImmutableListADT();
    Assert.assertEquals(2, immutableListADT.getSize());

    mutableListADT.addBack("3");
    Assert.assertEquals(3, mutableListADT.getSize());
    Assert.assertEquals(2, immutableListADT.getSize());
    Assert.assertEquals("2", immutableListADT.get(1));

    mutableListADT.add(3, "4");
    Assert.assertEquals(4, mutableListADT.getSize());
    Assert.assertEquals(2, immutableListADT.getSize());
    Assert.assertEquals("2", immutableListADT.get(1));

    mutableListADT.addFront("-1");
    Assert.assertEquals(5, mutableListADT.getSize());
    Assert.assertEquals(2, immutableListADT.getSize());
    Assert.assertEquals("1", immutableListADT.get(0));

    mutableListADT.remove("1");
    Assert.assertEquals(4, mutableListADT.getSize());
    Assert.assertEquals(2, immutableListADT.getSize());
    Assert.assertEquals("2", immutableListADT.get(1));
  }

  @Test
  public void testLiskovSubstitutionPrinciple() {
    ListADT<String> listADT = new ListADTImpl<>();
    listADT.addBack("1");
    listADT.addBack("2");
    listADT.addBack("3");
    listADT.addBack("4");
    Assert.assertTrue(performOperationsOnListADT("5", listADT, Integer::parseInt));

    ListADT<String> mutableListADT = new MutableListADTImpl<>();
    listADT.addBack("1");
    listADT.addBack("2");
    listADT.addBack("3");
    listADT.addBack("4");
    Assert.assertTrue(performOperationsOnListADT("5", mutableListADT, Integer::parseInt));
  }

  private <T, R> boolean performOperationsOnListADT(T data,
                                                    ListADT<T> listADT,
                                                    Function<T, R> mapper) {
    listADT.addFront(data);
    if (!data.equals(listADT.get(0))) {
      return false;
    }

    listADT.add(1, data);
    if (!data.equals(listADT.get(1))) {
      return false;
    }

    listADT.addBack(data);
    if (!data.equals(listADT.get(listADT.getSize() - 1))) {
      return false;
    }

    int sizeBeforeRemoval = listADT.getSize();
    listADT.remove(data);
    if (sizeBeforeRemoval - listADT.getSize() != 1) {
      return false;
    }

    ListADT<R> mappedList = listADT.map(mapper);
    if (listADT.getSize() != mappedList.getSize()) {
      return false;
    }

    String stringRepresentationOfList = listADT.toString();
    if (Objects.isNull(stringRepresentationOfList) || stringRepresentationOfList.isEmpty()) {
      return false;
    }

    return true;
  }
}