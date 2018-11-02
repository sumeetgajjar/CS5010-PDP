import org.junit.Assert;
import org.junit.Test;

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
    MutableListADTImpl<String> mutableListADT = new MutableListADTImpl<>();
    mutableListADT.addBack("1");
    mutableListADT.addBack("2");
    Assert.assertEquals("(1,2)", mutableListADT.toString());
  }

  @Test
  public void testMapOfMutableList() {
    MutableListADTImpl<String> mutableListADT = new MutableListADTImpl<>();
    mutableListADT.addBack("1");
    mutableListADT.addBack("2");

    MutableListADT<Integer> integerMutableListADT = mutableListADT.map(Integer::parseInt);
    Assert.assertEquals(integerMutableListADT.getSize(), mutableListADT.getSize());

    Assert.assertEquals(Integer.valueOf(1), integerMutableListADT.get(0));
    Assert.assertEquals(Integer.valueOf(2), integerMutableListADT.get(1));

    Assert.assertEquals("(1,2)", mutableListADT.toString());

    integerMutableListADT.addFront(-1);
    Assert.assertEquals(Integer.valueOf(-1), integerMutableListADT.get(0));
    Assert.assertEquals(3, integerMutableListADT.getSize());

    integerMutableListADT.add(2, 3);
    Assert.assertEquals(Integer.valueOf(3), integerMutableListADT.get(2));
    Assert.assertEquals(4, integerMutableListADT.getSize());

    integerMutableListADT.addBack(4);
    Assert.assertEquals(Integer.valueOf(4), integerMutableListADT.get(3));
    Assert.assertEquals(4, integerMutableListADT.getSize());
  }

  @Test
  public void testGettingImmutableListFromMutableList() {
    MutableListADTImpl<String> mutableListADT = new MutableListADTImpl<>();
    mutableListADT.addBack("1");
    mutableListADT.addBack("2");

    ImmutableListADT<String> immutableListAdt = mutableListADT.getImmutableListAdt();
    Assert.assertEquals(mutableListADT.getSize(), immutableListAdt.getSize());
    for (int i = 0; i < immutableListAdt.getSize(); i++) {
      Assert.assertEquals(mutableListADT.get(i), immutableListAdt.get(i));
    }
  }
}