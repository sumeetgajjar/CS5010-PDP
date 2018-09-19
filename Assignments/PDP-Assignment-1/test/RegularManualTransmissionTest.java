import org.junit.Assert;
import org.junit.Test;

import vehicle.ManualTransmission;
import vehicle.RegularManualTransmission;

/**
 * A JUnit class to test RegularManualTransmission.
 */
public class RegularManualTransmissionTest {

  @Test
  public void testInitialization() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 2,
            1, 8,
            6, 12,
            10, 16,
            14, 20);

    Assert.assertEquals(manualTransmission.getGear(), 1);
    Assert.assertEquals(manualTransmission.getSpeed(), 0);
    Assert.assertEquals(manualTransmission.getStatus(), "OK: everything is OK.");

  }

  @Test
  public void testGearSpeedRange() {
    try {
      ManualTransmission manualTransmission = new RegularManualTransmission(
              0, 10,
              5, 20,
              15, 14,
              25, 40,
              35, 50);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(),
              "Lower limit of Gear: 3 is greater than Upper limit");
    }
  }

  @Test
  public void testLowSpeedsOfGearSpeedRange() {
    try {
      ManualTransmission manualTransmission = new RegularManualTransmission(
              0, 10,
              5, 20,
              15, 25,
              12, 30,
              25, 40);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(),
              "Lower limit of Gear: 3 is greater than or equal to Lower limit of Gear: 4");
    }
  }

  @Test
  public void testNonAdjacentOverlappingGearSpeedRange1() {
    try {
      ManualTransmission manualTransmission = new RegularManualTransmission(
              0, 10,
              5, 20,
              8, 30,
              25, 40,
              35, 50);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(),
              "Non Adjacent Overlapping(2) for Gear: 3 with previous Gears");
    }
  }

  @Test
  public void testNonAdjacentOverlappingGearSpeedRange2() {
    try {
      ManualTransmission manualTransmission = new RegularManualTransmission(
              0, 10,
              5, 40,
              15, 30,
              25, 40,
              35, 50);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(),
              "Non Adjacent Overlapping(2) for Gear: 4 with previous Gears");
    }
  }

  @Test
  public void testNonOverlappingGearSpeedRange() {
    try {
      ManualTransmission manualTransmission = new RegularManualTransmission(
              0, 10,
              5, 20,
              22, 30,
              25, 40,
              35, 50);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(),
              "Speed of Gears: 2 and 3 are non-overlapping");
    }
  }

  @Test
  public void testLowSpeedOfFirstGearSpeedRange() {
    try {
      ManualTransmission manualTransmission = new RegularManualTransmission(
              10, 10,
              5, 20,
              8, 30,
              25, 40,
              35, 50);
      Assert.fail();
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(),
              "Lower limit of First Gear is not 0");
    }
  }

  @Test
  public void testCurrentSpeedCannotBeLessThanZero() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 10,
            5, 20,
            15, 30,
            25, 40,
            35, 50);

    manualTransmission = manualTransmission.increaseSpeed()
            .decreaseSpeed()
            .decreaseSpeed()
            .decreaseSpeed()
            .decreaseSpeed();

    Assert.assertEquals(manualTransmission.getStatus(),
            "Cannot decrease speed. Reached minimum speed.");
  }

  @Test
  public void testCurrentSpeedCannotIncreaseThanMaxSpeed() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 4,
            2, 8,
            6, 12,
            10, 16,
            14, 20);

    manualTransmission = manualTransmission
            .increaseSpeed().increaseSpeed().increaseSpeed().increaseSpeed().increaseGear()
            .increaseSpeed().increaseSpeed().increaseSpeed().increaseSpeed().increaseGear()
            .increaseSpeed().increaseSpeed().increaseSpeed().increaseSpeed().increaseGear()
            .increaseSpeed().increaseSpeed().increaseSpeed().increaseSpeed().increaseGear()
            .increaseSpeed().increaseSpeed().increaseSpeed().increaseSpeed()
            .increaseSpeed().increaseSpeed().increaseSpeed();

    Assert.assertEquals(manualTransmission.getSpeed(), 20);
    Assert.assertEquals(manualTransmission.getStatus(),
            "Cannot increase speed. Reached maximum speed.");
  }

  @Test
  public void testIncreaseSpeedDecreaseSpeed() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 4,
            2, 8,
            6, 12,
            10, 16,
            14, 20);

    manualTransmission = manualTransmission.increaseSpeed().increaseSpeed().increaseSpeed();
    Assert.assertEquals(manualTransmission.getSpeed(), 3);

    manualTransmission = manualTransmission.increaseGear().increaseSpeed()
            .decreaseGear().decreaseSpeed().decreaseSpeed();
    Assert.assertEquals(manualTransmission.getSpeed(), 2);
  }

  @Test
  public void testIncreaseGearDecreaseGear() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 4,
            2, 8,
            6, 12,
            10, 16,
            14, 20);

    manualTransmission = manualTransmission.increaseSpeed().increaseSpeed().increaseSpeed();
    Assert.assertEquals(manualTransmission.getStatus(),
            "OK: you may increase the gear.");

    manualTransmission = manualTransmission.increaseGear();
    Assert.assertEquals(manualTransmission.getGear(), 2);

    manualTransmission = manualTransmission.increaseSpeed().decreaseSpeed().decreaseSpeed();
    Assert.assertEquals(manualTransmission.getStatus(),
            "OK: you may decrease the gear.");

    manualTransmission = manualTransmission.decreaseGear();
    Assert.assertEquals(manualTransmission.getGear(), 1);
  }

  @Test
  public void testChangeInSpeedWithoutChangeInGear() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 2,
            1, 8,
            6, 12,
            10, 16,
            14, 20);

    manualTransmission = manualTransmission.increaseSpeed().increaseSpeed().increaseSpeed()
            .increaseSpeed();
    Assert.assertEquals(manualTransmission.getStatus(),
            "Cannot increase speed, increase gear first.");
    Assert.assertEquals(manualTransmission.getSpeed(), 2);

    manualTransmission = manualTransmission.increaseGear()
            .increaseSpeed().decreaseSpeed().decreaseSpeed().decreaseSpeed().decreaseSpeed();
    Assert.assertEquals(manualTransmission.getStatus(),
            "Cannot decrease speed, decrease gear first.");
    Assert.assertEquals(manualTransmission.getSpeed(), 1);
  }

  @Test
  public void testChangeInGearWithoutChangeInSpeed() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 2,
            1, 8,
            6, 12,
            10, 16,
            14, 20);

    manualTransmission = manualTransmission.increaseSpeed().increaseGear().increaseGear();
    Assert.assertEquals(manualTransmission.getStatus(),
            "Cannot increase gear, increase speed first.");

    manualTransmission = manualTransmission.increaseSpeed().increaseSpeed().increaseSpeed()
            .increaseSpeed().increaseSpeed().increaseGear().decreaseGear().decreaseGear();

    Assert.assertEquals(manualTransmission.getStatus(),
            "Cannot decrease gear, decrease speed first.");
  }

  @Test
  public void testEntireSimulation() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 2,
            1, 8,
            6, 12,
            10, 16,
            14, 20);

    manualTransmission = manualTransmission.increaseSpeed();
    Assert.assertEquals(manualTransmission.getStatus(),
            "OK: you may increase the gear.");

    manualTransmission = manualTransmission.increaseGear().increaseSpeed().increaseSpeed();
    Assert.assertEquals(manualTransmission.getStatus(),
            "OK: everything is OK.");

    manualTransmission = manualTransmission.increaseSpeed().decreaseGear();
    Assert.assertEquals(manualTransmission.getStatus(),
            "Cannot decrease gear, decrease speed first.");

    manualTransmission = manualTransmission.increaseSpeed().increaseSpeed().increaseSpeed()
            .increaseSpeed().increaseSpeed();
    Assert.assertEquals(manualTransmission.getStatus(),
            "Cannot increase speed, increase gear first.");

    manualTransmission = manualTransmission.increaseGear().decreaseSpeed().decreaseGear();
    Assert.assertEquals(manualTransmission.getGear(), 2);

    manualTransmission = manualTransmission.decreaseSpeed().decreaseSpeed().decreaseSpeed()
            .decreaseSpeed().decreaseSpeed().decreaseGear().decreaseSpeed().decreaseGear();
    Assert.assertEquals(manualTransmission.getStatus(),
            "Cannot decrease gear. Reached minimum gear.");

    manualTransmission = manualTransmission.decreaseSpeed();
    Assert.assertEquals(manualTransmission.getSpeed(), 0);

  }
}
