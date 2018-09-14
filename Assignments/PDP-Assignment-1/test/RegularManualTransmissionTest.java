import org.junit.Assert;
import org.junit.Test;

import vehicle.ManualTransmission;
import vehicle.RegularManualTransmission;

public class RegularManualTransmissionTest {

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
      Assert.assertEquals(e.getMessage(), "Low Speed of Gear: 3 is greater than High Speed");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLowSpeedGearSpeedRange() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 10,
            5, 20,
            15, 25,
            12, 30,
            25, 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonAdjacentOverlappingGearSpeedRange1() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 10,
            5, 20,
            8, 30,
            25, 40,
            35, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonAdjacentOverlappingGearSpeedRange2() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 10,
            5, 20,
            15, 30,
            25, 40,
            4, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonAdjacentOverlappingGearSpeedRange3() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 10,
            5, 40,
            15, 30,
            25, 40,
            35, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonOverlappingGearSpeedRange() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            0, 10,
            5, 20,
            22, 30,
            25, 40,
            35, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLowSpeedOfFirstGearSpeedRange() {
    ManualTransmission manualTransmission = new RegularManualTransmission(
            10, 10,
            5, 20,
            8, 30,
            25, 40,
            35, 50);
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

    manualTransmission = manualTransmission.increaseSpeed().increaseSpeed().increaseGear()
            .increaseSpeed().increaseSpeed().increaseSpeed().increaseSpeed().increaseGear()
            .increaseSpeed().increaseSpeed().increaseSpeed().increaseSpeed().increaseGear()
            .increaseSpeed().increaseSpeed().increaseSpeed().increaseSpeed().increaseGear()
            .increaseSpeed().increaseSpeed().increaseSpeed().increaseSpeed()
            .increaseSpeed().increaseSpeed().increaseSpeed();


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
            .decreaseGear().decreaseSpeed();
    Assert.assertEquals(manualTransmission.getSpeed(), 3);
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
