import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cs5010.register.CashRegister;
import cs5010.register.InsufficientCashException;
import cs5010.register.SimpleRegister;
import cs5010.register.Utils;
import cs5010.register.bean.Denomination;
import cs5010.register.bean.TransactionType;

/**
 * A Junit class to Test {@link cs5010.register.SimpleRegister}
 */
public class SimpleRegisterTest {

  private CashRegister cashRegister;

  @Before
  public void setUp() {
    this.cashRegister = new SimpleRegister();
  }

  @Test
  public void testInitializationOfCashRegister() throws InsufficientCashException {
    Assert.assertEquals("", this.cashRegister.getAuditLog());
    Assert.assertEquals(0, this.cashRegister.getContents().size());

    try {
      this.cashRegister.withdraw(1, 1);
    } catch (InsufficientCashException e) {
      Assert.assertEquals("insufficient cash in the register", e.getMessage());
    }

    this.cashRegister.addTens(10);
    Assert.assertEquals(Collections.singletonMap(1000, 10), this.cashRegister.getContents());

    this.cashRegister.withdraw(2, 0);
    Assert.assertEquals(Collections.singletonMap(1000, 8), this.cashRegister.getContents());

    String expectedAuditLog = TransactionType.DEPOSIT.getTypeString() + ": 100.00" + System.lineSeparator();
    expectedAuditLog += TransactionType.WITHDRAWL.getTypeString() + ": 20.00" + System.lineSeparator();

    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());
  }

  @Test
  public void testWithdrawMethod() {
    this.cashRegister.addPennies(10);
    this.cashRegister.addNickels(10);
    this.cashRegister.addDimes(10);
    this.cashRegister.addQuarters(10);
    this.cashRegister.addOnes(10);
    this.cashRegister.addFives(10);
    this.cashRegister.addTens(10);

    String expectedAuditLog = String.format("%s: 0.10%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 0.50%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 1.00%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 2.50%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 10.00%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 50.00%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 100.00", TransactionType.DEPOSIT.getTypeString());

    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());
  }

  @Test
  public void testAddingMultipleCashDenominationToRegister() {
    Map<Integer, Integer> expectedContents = new HashMap<>();

    expectedContents.put(1, 11221);
    this.cashRegister.addPennies(11221);
    Assert.assertEquals(expectedContents, this.cashRegister.getContents());

    expectedContents.put(5, Denomination.NICKELS.getPennies(11221));
    this.cashRegister.addNickels(11221);
    Assert.assertEquals(expectedContents, this.cashRegister.getContents());

    expectedContents.put(10, Denomination.DIMES.getPennies(11221));
    this.cashRegister.addDimes(11221);
    Assert.assertEquals(expectedContents, this.cashRegister.getContents());

    expectedContents.put(25, Denomination.QUARTER.getPennies(11221));
    this.cashRegister.addQuarters(11221);
    Assert.assertEquals(expectedContents, this.cashRegister.getContents());

    expectedContents.put(100, Denomination.ONES.getPennies(11221));
    this.cashRegister.addOnes(11221);
    Assert.assertEquals(expectedContents, this.cashRegister.getContents());

    expectedContents.put(500, Denomination.FIVES.getPennies(11221));
    this.cashRegister.addFives(11221);
    Assert.assertEquals(expectedContents, this.cashRegister.getContents());

    expectedContents.put(1000, Denomination.TENS.getPennies(11221));
    this.cashRegister.addTens(11221);
    Assert.assertEquals(expectedContents, this.cashRegister.getContents());
  }

  @Test
  public void testAddingPennies() {
    String expectedTransactionLog = "";

    this.cashRegister.addPennies(1);
    expectedTransactionLog += String.format("%s: 0.01", TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
    Assert.assertTrue(Utils.areMapEqual(
            Collections.singletonMap(1, 1), this.cashRegister.getContents()));

    this.cashRegister.addPennies(100);
    expectedTransactionLog += String.format("%s%s: 1.00", System.lineSeparator(),
            TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
  }

  @Test
  public void testAddingNickels() {
    String expectedTransactionLog = "";

    this.cashRegister.addNickels(1);
    expectedTransactionLog += String.format("%s: 0.05", TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
    Assert.assertTrue(Utils.areMapEqual(
            Collections.singletonMap(5, 1), this.cashRegister.getContents()));

    this.cashRegister.addNickels(100);
    expectedTransactionLog += String.format("%s%s: 5.00", System.lineSeparator(),
            TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
  }


  @Test
  public void testAddingDimes() {
    String expectedTransactionLog = "";

    this.cashRegister.addDimes(1);
    expectedTransactionLog += String.format("%s: 0.10", TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
    Assert.assertTrue(Utils.areMapEqual(
            Collections.singletonMap(10, 1), this.cashRegister.getContents()));

    this.cashRegister.addDimes(100);
    expectedTransactionLog += String.format("%s%s: 10.00", System.lineSeparator(),
            TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
  }

  @Test
  public void testAddingQuarters() {
    String expectedTransactionLog = "";

    this.cashRegister.addQuarters(1);
    expectedTransactionLog += String.format("%s: 0.25", TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
    Assert.assertTrue(Utils.areMapEqual(
            Collections.singletonMap(25, 1), this.cashRegister.getContents()));

    this.cashRegister.addQuarters(100);
    expectedTransactionLog += String.format("%s%s: 25.00", System.lineSeparator(),
            TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
  }


  @Test
  public void testAddingOnes() {
    String expectedTransactionLog = "";

    this.cashRegister.addOnes(1);
    expectedTransactionLog += String.format("%s: 1.00", TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
    Assert.assertTrue(Utils.areMapEqual(
            Collections.singletonMap(100, 1), this.cashRegister.getContents()));

    this.cashRegister.addOnes(100);
    expectedTransactionLog += String.format("%s%s: 100.00", System.lineSeparator(),
            TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
  }

  @Test
  public void testAddingFives() {
    String expectedTransactionLog = "";

    this.cashRegister.addFives(1);
    expectedTransactionLog += String.format("%s: 5.00", TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
    Assert.assertTrue(Utils.areMapEqual(
            Collections.singletonMap(500, 1), this.cashRegister.getContents()));

    this.cashRegister.addFives(100);
    expectedTransactionLog += String.format("%s%s: 500.00", System.lineSeparator(),
            TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
  }


  @Test
  public void testAddingTens() {
    String expectedTransactionLog = "";

    this.cashRegister.addTens(1);
    expectedTransactionLog += String.format("%s: 10.00", TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
    Assert.assertTrue(Utils.areMapEqual(
            Collections.singletonMap(1000, 1), this.cashRegister.getContents()));

    this.cashRegister.addTens(100);
    expectedTransactionLog += String.format("%s%s: 1000.00", System.lineSeparator(),
            TransactionType.DEPOSIT.getTypeString());
    Assert.assertEquals(expectedTransactionLog, this.cashRegister.getAuditLog());
  }

  @Test
  public void testInvalidAmountForDeposit() {
    for (int i : Arrays.asList(0, -1)) {
      try {
        this.cashRegister.addPennies(i);
        Assert.fail("should have failed");
      } catch (IllegalArgumentException e) {
        Assert.assertEquals("invalid deposit amount", e.getMessage());
        Assert.assertEquals(0, this.cashRegister.getContents().size());
        Assert.assertEquals("", this.cashRegister.getAuditLog());
      }

      try {
        this.cashRegister.addNickels(i);
        Assert.fail("should have failed");
      } catch (IllegalArgumentException e) {
        Assert.assertEquals("invalid deposit amount", e.getMessage());
        Assert.assertEquals(0, this.cashRegister.getContents().size());
        Assert.assertEquals("", this.cashRegister.getAuditLog());
      }

      try {
        this.cashRegister.addDimes(i);
        Assert.fail("should have failed");
      } catch (IllegalArgumentException e) {
        Assert.assertEquals("invalid deposit amount", e.getMessage());
        Assert.assertEquals(0, this.cashRegister.getContents().size());
        Assert.assertEquals("", this.cashRegister.getAuditLog());
      }

      try {
        this.cashRegister.addQuarters(i);
        Assert.fail("should have failed");
      } catch (IllegalArgumentException e) {
        Assert.assertEquals("invalid deposit amount", e.getMessage());
        Assert.assertEquals(0, this.cashRegister.getContents().size());
        Assert.assertEquals("", this.cashRegister.getAuditLog());
      }

      try {
        this.cashRegister.addOnes(i);
        Assert.fail("should have failed");
      } catch (IllegalArgumentException e) {
        Assert.assertEquals("invalid deposit amount", e.getMessage());
        Assert.assertEquals(0, this.cashRegister.getContents().size());
        Assert.assertEquals("", this.cashRegister.getAuditLog());
      }

      try {
        this.cashRegister.addFives(i);
        Assert.fail("should have failed");
      } catch (IllegalArgumentException e) {
        Assert.assertEquals("invalid deposit amount", e.getMessage());
        Assert.assertEquals(0, this.cashRegister.getContents().size());
        Assert.assertEquals("", this.cashRegister.getAuditLog());
      }

      try {
        this.cashRegister.addTens(i);
        Assert.fail("should have failed");
      } catch (IllegalArgumentException e) {
        Assert.assertEquals("invalid deposit amount", e.getMessage());
        Assert.assertEquals(0, this.cashRegister.getContents().size());
        Assert.assertEquals("", this.cashRegister.getAuditLog());
      }
    }
  }
}
