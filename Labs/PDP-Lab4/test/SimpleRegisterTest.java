import org.junit.After;
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

  @After
  public void tearDown() {
    this.cashRegister = null;
  }

  @Test
  public void testInitializationOfCashRegister() throws InsufficientCashException {
    Assert.assertEquals("", this.cashRegister.getAuditLog());
    Assert.assertEquals(0, this.cashRegister.getContents().size());

    try {
      this.cashRegister.withdraw(1, 1);
      Assert.fail("should have failed");
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
  public void testWithdrawAmountGreaterThanCashRegister() {
    Map<Integer, Integer> expectedContents = new HashMap<>();
    expectedContents.put(1, 1);
    expectedContents.put(5, 1);
    expectedContents.put(10, 1);
    expectedContents.put(25, 1);
    expectedContents.put(100, 1);
    expectedContents.put(500, 1);
    expectedContents.put(1000, 1);

    String expectedAuditLog = String.format("%s: 0.01%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 0.05%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 0.10%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 0.25%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 1.00%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 5.00%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedAuditLog += String.format("%s: 10.00", TransactionType.DEPOSIT.getTypeString());

    this.cashRegister.addPennies(1);
    this.cashRegister.addNickels(1);
    this.cashRegister.addDimes(1);
    this.cashRegister.addQuarters(1);
    this.cashRegister.addOnes(1);
    this.cashRegister.addFives(1);
    this.cashRegister.addTens(1);

    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    try {
      this.cashRegister.withdraw(16, 42);
      Assert.fail("should have failed");
    } catch (InsufficientCashException e) {
      Assert.assertEquals("insufficient cash in the register", e.getMessage());
    }
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    try {
      this.cashRegister.withdraw(164, 2);
      Assert.fail("should have failed");
    } catch (InsufficientCashException e) {
      Assert.assertEquals("insufficient cash in the register", e.getMessage());
    }
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());
  }

  @Test
  public void testWithdrawAmountOfDenominationNotPresentInRegister() {
    Map<Integer, Integer> expectedContents = new HashMap<>();

    expectedContents.put(1000, 10);
    this.cashRegister.addTens(10);
    try {
      this.cashRegister.withdraw(11, 0);
      Assert.fail("should have failed");
    } catch (InsufficientCashException e) {
      Assert.assertEquals("insufficient cash in the register", e.getMessage());
    }
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));

    expectedContents.put(500, 10);
    this.cashRegister.addFives(10);
    try {
      this.cashRegister.withdraw(11, 0);
      Assert.fail("should have failed");
    } catch (InsufficientCashException e) {
      Assert.assertEquals("insufficient cash in the register", e.getMessage());
    }
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));

    expectedContents.put(100, 1);
    this.cashRegister.addOnes(10);
    try {
      this.cashRegister.withdraw(12, 0);
      Assert.fail("should have failed");
    } catch (InsufficientCashException e) {
      Assert.assertEquals("insufficient cash in the register", e.getMessage());
    }
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));

    expectedContents.put(25, 1);
    this.cashRegister.addQuarters(1);
    try {
      this.cashRegister.withdraw(12, 10);
      Assert.fail("should have failed");
    } catch (InsufficientCashException e) {
      Assert.assertEquals("insufficient cash in the register", e.getMessage());
    }
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));

    expectedContents.put(10, 1);
    this.cashRegister.addDimes(1);
    try {
      this.cashRegister.withdraw(12, 20);
      Assert.fail("should have failed");
    } catch (InsufficientCashException e) {
      Assert.assertEquals("insufficient cash in the register", e.getMessage());
    }
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));

    expectedContents.put(5, 1);
    this.cashRegister.addNickels(1);
    try {
      this.cashRegister.withdraw(12, 20);
      Assert.fail("should have failed");
    } catch (InsufficientCashException e) {
      Assert.assertEquals("insufficient cash in the register", e.getMessage());
    }
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));

    expectedContents.put(1, 1);
    this.cashRegister.addPennies(1);
    try {
      this.cashRegister.withdraw(12, 20);
      Assert.fail("should have failed");
    } catch (InsufficientCashException e) {
      Assert.assertEquals("insufficient cash in the register", e.getMessage());
    }
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
  }

  @Test
  public void testWithdrawMethod() throws InsufficientCashException {
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

    Map<Integer, Integer> expectedContents = new HashMap<>();
    expectedContents.put(1, 10);
    expectedContents.put(5, 10);
    expectedContents.put(10, 10);
    expectedContents.put(25, 10);
    expectedContents.put(100, 10);
    expectedContents.put(500, 10);
    expectedContents.put(1000, 10);

    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));

    expectedAuditLog += String.format("%s%s: 0.01%s", System.lineSeparator(),
            TransactionType.WITHDRAWL.getTypeString(), System.lineSeparator());
    expectedContents.put(1, 9);
    Assert.assertEquals(Collections.singletonMap(1, 1), this.cashRegister.withdraw(0, 1));
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 0.05%s", TransactionType.WITHDRAWL.getTypeString(),
            System.lineSeparator());
    expectedContents.put(5, 9);
    Assert.assertEquals(Collections.singletonMap(5, 1), this.cashRegister.withdraw(0, 5));
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 0.10%s", TransactionType.WITHDRAWL.getTypeString(),
            System.lineSeparator());
    expectedContents.put(10, 9);
    Assert.assertEquals(Collections.singletonMap(10, 1), this.cashRegister.withdraw(0, 10));
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 0.25%s", TransactionType.WITHDRAWL.getTypeString(),
            System.lineSeparator());
    expectedContents.put(25, 9);
    Assert.assertEquals(Collections.singletonMap(25, 1), this.cashRegister.withdraw(0, 25));
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 1.00%s", TransactionType.WITHDRAWL.getTypeString(),
            System.lineSeparator());
    expectedContents.put(100, 9);
    Assert.assertEquals(Collections.singletonMap(100, 1), this.cashRegister.withdraw(1, 0));
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 5.00%s", TransactionType.WITHDRAWL.getTypeString(),
            System.lineSeparator());
    expectedContents.put(500, 9);
    Assert.assertEquals(Collections.singletonMap(500, 1), this.cashRegister.withdraw(5, 0));
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 10.00", TransactionType.WITHDRAWL.getTypeString());
    expectedContents.put(1000, 9);
    Assert.assertEquals(Collections.singletonMap(1000, 1), this.cashRegister.withdraw(10, 0));
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());
  }

  @Test
  public void testWithDrawingInvalidAmount() throws InsufficientCashException {
    try {
      this.cashRegister.withdraw(0, 0);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("invalid withdrawal amount", e.getMessage());
    }

    try {
      this.cashRegister.withdraw(-1, 10);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("invalid withdrawal amount", e.getMessage());
    }

    try {
      this.cashRegister.withdraw(10, -10);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("invalid withdrawal amount", e.getMessage());
    }

    try {
      this.cashRegister.withdraw(-1, -10);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("invalid withdrawal amount", e.getMessage());
    }
  }

  @Test
  public void testAddingMultipleCashDenominationToRegister() {
    Map<Integer, Integer> expectedContents = new HashMap<>();

    String expectedAuditLog = String.format("%s: 0.10%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());

    expectedContents.put(1, 10);
    this.cashRegister.addPennies(10);
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 0.50%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedContents.put(5, Denomination.NICKELS.getPennies(10));
    this.cashRegister.addNickels(10);
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 1.00%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedContents.put(10, Denomination.DIMES.getPennies(10));
    this.cashRegister.addDimes(10);
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 2.50%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedContents.put(25, Denomination.QUARTER.getPennies(10));
    this.cashRegister.addQuarters(10);
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 10.00%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedContents.put(100, Denomination.ONES.getPennies(10));
    this.cashRegister.addOnes(10);
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 50.00%s", TransactionType.DEPOSIT.getTypeString(),
            System.lineSeparator());
    expectedContents.put(500, Denomination.FIVES.getPennies(10));
    this.cashRegister.addFives(10);
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());

    expectedAuditLog += String.format("%s: 100.00", TransactionType.DEPOSIT.getTypeString());
    expectedContents.put(1000, Denomination.TENS.getPennies(10));
    this.cashRegister.addTens(10);
    Assert.assertTrue(Utils.areMapEqual(expectedContents, this.cashRegister.getContents()));
    Assert.assertEquals(expectedAuditLog, this.cashRegister.getAuditLog());
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
