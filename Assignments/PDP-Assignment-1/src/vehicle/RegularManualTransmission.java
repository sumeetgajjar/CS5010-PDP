package vehicle;

import vehicle.bean.GearSpeedRange;
import vehicle.bean.TransmissionStatus;

public class RegularManualTransmission implements ManualTransmission {

  private static final int SPEED_CHANGE = 1;
  private static final int MAX_GEARS = 5;

  private final GearSpeedRange[] gearSpeedRanges;

  private int currentGear;
  private int currentSpeed;
  private TransmissionStatus transmissionStatus;

  public RegularManualTransmission(int gear1Low, int gear1High,
                                   int gear2Low, int gear2High,
                                   int gear3Low, int gear3High,
                                   int gear4Low, int gear4High,
                                   int gear5Low, int gear5High) {

    this.gearSpeedRanges = new GearSpeedRange[MAX_GEARS];
    this.gearSpeedRanges[0] = new GearSpeedRange(gear1Low, gear1High);
    this.gearSpeedRanges[1] = new GearSpeedRange(gear2Low, gear2High);
    this.gearSpeedRanges[2] = new GearSpeedRange(gear3Low, gear3High);
    this.gearSpeedRanges[3] = new GearSpeedRange(gear4Low, gear4High);
    this.gearSpeedRanges[4] = new GearSpeedRange(gear5Low, gear5High);

    performGearSpeedRangeSanityChecks();

    this.currentGear = 0;
    this.currentSpeed = 0;
    this.transmissionStatus = TransmissionStatus.OK;
  }

  @Override
  public String getStatus() {
    return this.transmissionStatus.getStatusMessage();
  }

  @Override
  public int getSpeed() {
    return this.currentSpeed;
  }

  @Override
  public int getGear() {
    return this.currentGear + 1;
  }

  @Override
  public ManualTransmission increaseSpeed() {
    int speedAfterIncrease = this.currentSpeed + SPEED_CHANGE;
    if (canIncreaseSpeedInCurrentGear(speedAfterIncrease)) {
      this.currentSpeed = speedAfterIncrease;

      if (doesHigherGearExist() && canShiftToHigherGear()) {
        this.transmissionStatus = TransmissionStatus.MAY_INCREASE_GEAR;
      } else {
        this.transmissionStatus = TransmissionStatus.OK;
      }
    } else {
      if (doesHigherGearExist()) {
        this.transmissionStatus = TransmissionStatus.CANNOT_INCREASE_SPEED_INCREASE_GEAR_FIRST;
      } else {
        this.transmissionStatus = TransmissionStatus.CANNOT_INCREASE_SPEED_REACHED_MAX_SPEED;
      }
    }
    return this;
  }

  @Override
  public ManualTransmission decreaseSpeed() {
    int speedAfterDecrease = this.currentSpeed - SPEED_CHANGE;
    if (canDecreaseSpeedInCurrentGear(speedAfterDecrease)) {
      this.currentSpeed = speedAfterDecrease;

      if (doesLowerGearExist() && canShiftToLowerGear()) {
        this.transmissionStatus = TransmissionStatus.MAY_DECREASE_GEAR;
      } else {
        this.transmissionStatus = TransmissionStatus.OK;
      }
    } else {
      if (doesLowerGearExist()) {
        this.transmissionStatus = TransmissionStatus.CANNOT_DECREASE_SPEED_DECREASE_GEAR_FIRST;
      } else {
        this.transmissionStatus = TransmissionStatus.CANNOT_DECREASE_SPEED_REACHED_MIN_SPEED;
      }
    }
    return this;
  }

  @Override
  public ManualTransmission increaseGear() {
    int gearAfterIncrease = this.currentGear + 1;
    if (gearAfterIncrease < MAX_GEARS) {
      if (canShiftToHigherGear()) {
        this.currentGear = gearAfterIncrease;
        this.transmissionStatus = TransmissionStatus.OK;
      } else {
        this.transmissionStatus = TransmissionStatus.CANNOT_INCREASE_GEAR_INCREASE_SPEED_FIRST;
      }
    } else {
      this.transmissionStatus = TransmissionStatus.CANNOT_INCREASE_GEAR_REACHED_MAX_GEAR;
    }
    return this;
  }

  @Override
  public ManualTransmission decreaseGear() {
    int gearAfterDecrease = this.currentGear - 1;
    if (gearAfterDecrease >= 0) {
      if (canShiftToLowerGear()) {
        this.currentGear = gearAfterDecrease;
        this.transmissionStatus = TransmissionStatus.OK;
      } else {
        this.transmissionStatus = TransmissionStatus.CANNOT_DECREASE_GEAR_DECREASE_SPEED_FIRST;
      }
    } else {
      this.transmissionStatus = TransmissionStatus.CANNOT_DECREASE_GEAR_REACHED_MIN_GEAR;
    }
    return this;
  }

  private void performGearSpeedRangeSanityChecks() {
    checkLowSpeedOfFirstGear();
    checkInvalidGearSpeedRange();
    checkInvalidLowSpeedsOfGears();
    checkInvalidGearOverLapping();
  }

  private void checkInvalidGearSpeedRange() {
    for (int i = 0; i < gearSpeedRanges.length; i++) {
      if (gearSpeedRanges[i].getLowSpeed() > gearSpeedRanges[i].getHighSpeed()) {
        throw new IllegalArgumentException(
                String.format("Low Speed of Gear: %d is greater than High Speed",
                        i + 1));
      }
    }
  }

  private void checkInvalidLowSpeedsOfGears() {
    for (int i = 0; i < gearSpeedRanges.length; i++) {
      for (int j = i + 1; j < gearSpeedRanges.length; j++) {
        if (gearSpeedRanges[i].getLowSpeed() >= gearSpeedRanges[j].getLowSpeed()) {
          throw new IllegalArgumentException(
                  String.format(
                          "Low Speed of Gear: %d is greater than or equal to Low Speed of Gear: %d",
                          i + 1, j + 1));
        }
      }
    }
  }

  private void checkInvalidGearOverLapping() {
    checkNonAdjacentGearOverLappingWithPrevGears();
    checkNonAdjacentGearOverLappingWithNextGears();
  }

  private void checkNonAdjacentGearOverLappingWithPrevGears() {
    for (int i = 1; i < gearSpeedRanges.length - 1; i++) {
      GearSpeedRange gearSpeedRange = gearSpeedRanges[i];

      int previousGearSpeedRangeOverlapping = 0;
      for (int j = i - 1; j >= 0; j--) {
        GearSpeedRange previousGearSpeedRange = gearSpeedRanges[j];
        if (gearSpeedRange.getLowSpeed() <= previousGearSpeedRange.getHighSpeed()) {
          previousGearSpeedRangeOverlapping++;
        }
      }

      if (previousGearSpeedRangeOverlapping == 0) {
        throw new IllegalArgumentException(
                String.format("Speed of Gears: %d and %d are non-overlapping",
                        i, i + 1));

      } else if (previousGearSpeedRangeOverlapping != 1) {
        throw new IllegalArgumentException(
                String.format("Non Adjacent Overlapping(%d) for Gear: %d with previous Gears",
                        previousGearSpeedRangeOverlapping, i + 1));
      }
    }
  }

  private void checkNonAdjacentGearOverLappingWithNextGears() {
    for (int i = 1; i < gearSpeedRanges.length - 1; i++) {
      GearSpeedRange gearSpeedRange = gearSpeedRanges[i];

      int nextGearSpeedRangeOverlapping = 0;
      for (int k = i + 1; k < gearSpeedRanges.length; k++) {
        GearSpeedRange nextGearSpeedRange = gearSpeedRanges[k];
        if (gearSpeedRange.getHighSpeed() >= nextGearSpeedRange.getLowSpeed()) {
          nextGearSpeedRangeOverlapping++;
        }
      }

      if (nextGearSpeedRangeOverlapping != 1) {
        throw new IllegalArgumentException(
                String.format("Non Adjacent Overlapping(%d) for Gear: %d with next Gears",
                        nextGearSpeedRangeOverlapping, i + 1));
      }
    }
  }

  private void checkLowSpeedOfFirstGear() {
    if (gearSpeedRanges[0].getLowSpeed() != 0) {
      throw new IllegalArgumentException("Lower Speed of First Gear is not 0");
    }
  }


  private boolean canIncreaseSpeedInCurrentGear(int speedAfterIncrease) {
    return speedAfterIncrease <= this.gearSpeedRanges[this.currentGear].getHighSpeed();
  }

  private boolean canShiftToHigherGear() {
    return this.currentSpeed >= gearSpeedRanges[this.currentGear + 1].getLowSpeed();
  }

  private boolean doesHigherGearExist() {
    return this.currentGear + 1 < MAX_GEARS;
  }

  private boolean canShiftToLowerGear() {
    return this.currentSpeed <= this.gearSpeedRanges[this.currentGear - 1].getHighSpeed();
  }

  private boolean doesLowerGearExist() {
    return this.currentGear - 1 >= 0;
  }

  private boolean canDecreaseSpeedInCurrentGear(int speedAfterDecrease) {
    return speedAfterDecrease >= this.gearSpeedRanges[this.currentGear].getLowSpeed();
  }
}
