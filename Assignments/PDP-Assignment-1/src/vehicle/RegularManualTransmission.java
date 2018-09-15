package vehicle;

import vehicle.bean.GearSpeedRange;
import vehicle.bean.TransmissionStatus;

/**
 * This class represents a RegularManualTransmission and it implements ManualTransmission interface.
 * A RegularManualTransmission has a currentSpeed, currentGear, transmissionStatus and Speed ranges
 * for its Gears.
 */
public class RegularManualTransmission implements ManualTransmission {

  /**
   * Fixed amount by which the Speed of the vehicle changes, 1.
   */
  private static final int SPEED_CHANGE = 1;

  /**
   * Total number of gears in the vehicle, 5.
   */
  private static final int TOTAL_GEARS_IN_VEHICLE = 5;

  private final GearSpeedRange[] gearSpeedRanges;

  private int currentGear;
  private int currentSpeed;
  private TransmissionStatus transmissionStatus;

  /**
   * Constructs the RegularManualTransmission object with given gear speed ranges.
   *
   * @param gear1Low  lower limit of the 1st gear
   * @param gear1High upper limit of the 1st gear
   * @param gear2Low  lower limit of the 2nd gear
   * @param gear2High upper limit of the 2nd gear
   * @param gear3Low  lower limit of the 3rd gear
   * @param gear3High upper limit of the 3rd gear
   * @param gear4Low  lower limit of the 4th gear
   * @param gear4High upper limit of the 4th gear
   * @param gear5Low  lower limit of the 5th gear
   * @param gear5High upper limit of the 5th gear
   * @throws IllegalArgumentException if any of the sanity checks for gear range fails
   */
  public RegularManualTransmission(int gear1Low, int gear1High,
                                   int gear2Low, int gear2High,
                                   int gear3Low, int gear3High,
                                   int gear4Low, int gear4High,
                                   int gear5Low, int gear5High) throws IllegalArgumentException {

    this.gearSpeedRanges = new GearSpeedRange[TOTAL_GEARS_IN_VEHICLE];
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

  /**
   * Increases the speed of the vehicle by 1 if its possible and changes the status. Following
   * scenarios are possible:
   * <ul>
   * <li>If speed can be increased without changing gear then speed increases and status is set to
   * <code>{@link TransmissionStatus#OK}</code></li>
   *
   * <li>If speed can be increased and the intended speed lies within range of next gear then speed
   * increases and status is set to <code>{@link TransmissionStatus#MAY_INCREASE_GEAR}</code></li>
   *
   * <li>If speed cannot be increased as the intended speed is to high for current gear then status
   * is set to <code>{@link TransmissionStatus#CANNOT_INCREASE_SPEED_INCREASE_GEAR_FIRST}</code></li>
   *
   * <li>If speed cannot be increased as it will go beyond the speed limit of the vehicle then
   * status is set to <code>{@link TransmissionStatus#CANNOT_INCREASE_SPEED_REACHED_MAX_SPEED}</code></li>
   * </ul>
   *
   * @return the caller ManualTransmission object with the modified information
   */
  @Override
  public ManualTransmission increaseSpeed() {
    int intendedSpeed = this.currentSpeed + SPEED_CHANGE;
    if (canIncreaseSpeedInCurrentGear(intendedSpeed)) {
      this.currentSpeed = intendedSpeed;

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
    int intendedSpeed = this.currentSpeed - SPEED_CHANGE;
    if (canDecreaseSpeedInCurrentGear(intendedSpeed)) {
      this.currentSpeed = intendedSpeed;

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
    if (doesHigherGearExist()) {
      if (canShiftToHigherGear()) {
        this.currentGear += 1;
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
    if (doesLowerGearExist()) {
      if (canShiftToLowerGear()) {
        this.currentGear -= 1;
        this.transmissionStatus = TransmissionStatus.OK;
      } else {
        this.transmissionStatus = TransmissionStatus.CANNOT_DECREASE_GEAR_DECREASE_SPEED_FIRST;
      }
    } else {
      this.transmissionStatus = TransmissionStatus.CANNOT_DECREASE_GEAR_REACHED_MIN_GEAR;
    }
    return this;
  }

  /**
   * Performs various sanity checks on all given gear speed ranges.
   */
  private void performGearSpeedRangeSanityChecks() throws IllegalArgumentException {
    checkLowSpeedOfFirstGear();
    checkInvalidGearSpeedRange();
    checkInvalidLowSpeedsOfGears();
    checkNonOverlappingAdjacentGears();
    checkInvalidGearOverLapping();
  }

  /**
   * Checks if the lower speed of the 1st gear is not 0.
   *
   * @throws IllegalArgumentException if the lower speed of the 1st gear is not 0
   */
  private void checkLowSpeedOfFirstGear() throws IllegalArgumentException {
    if (gearSpeedRanges[0].getLowerLimit() != 0) {
      throw new IllegalArgumentException("Lower limit of First Gear is not 0");
    }
  }

  /**
   * Checks if low speed of gear is greater than high speed.
   *
   * @throws IllegalArgumentException if low speed of gear is greater than high speed
   */
  private void checkInvalidGearSpeedRange() throws IllegalArgumentException {
    for (int i = 0; i < gearSpeedRanges.length; i++) {
      if (gearSpeedRanges[i].getLowerLimit() > gearSpeedRanges[i].getUpperLimit()) {
        throw new IllegalArgumentException(
                String.format("Lower limit of Gear: %d is greater than Upper limit",
                        i + 1));
      }
    }
  }

  /**
   * Checks if low speed of previous gear is greater than low speed of next gear.
   *
   * @throws IllegalArgumentException if low speed of previous gear is greater than low speed of
   *                                  next gear
   */
  private void checkInvalidLowSpeedsOfGears() throws IllegalArgumentException {
    for (int i = 0; i < gearSpeedRanges.length; i++) {
      for (int j = i + 1; j < gearSpeedRanges.length; j++) {
        if (gearSpeedRanges[i].getLowerLimit() >= gearSpeedRanges[j].getLowerLimit()) {
          String exceptionMessage = String.format(
                  "Lower limit of Gear: %d is greater than or equal to Lower limit of Gear: %d",
                  i + 1, j + 1);
          throw new IllegalArgumentException(exceptionMessage);
        }
      }
    }
  }

  /**
   * Checks if there exists non overlapping adjacent gears.
   *
   * @throws IllegalArgumentException if there exists non overlapping adjacent gears
   */
  private void checkNonOverlappingAdjacentGears() throws IllegalArgumentException {
    for (int i = 0; i < gearSpeedRanges.length - 1; i++) {
      if (gearSpeedRanges[i].getUpperLimit() < gearSpeedRanges[i + 1].getLowerLimit()) {
        throw new IllegalArgumentException(
                String.format("Speed of Gears: %d and %d are non-overlapping",
                        i + 1, i + 2));
      }
    }
  }

  /**
   * Checks if only adjacent gears overlaps.
   *
   * @throws IllegalArgumentException if non adjacent gears overlaps
   */
  private void checkInvalidGearOverLapping() throws IllegalArgumentException {
    checkNonAdjacentGearOverLappingWithPrevGears();
    checkNonAdjacentGearOverLappingWithNextGears();
  }

  /**
   * Checks if overlapping exists between a gear and the gears before it. If any such overlapping
   * exists and the gears are non adjacent then it throws an exception.
   *
   * @throws IllegalArgumentException if non adjacent gears overlaps
   */
  private void checkNonAdjacentGearOverLappingWithPrevGears() throws IllegalArgumentException {
    for (int i = 1; i < gearSpeedRanges.length - 1; i++) {
      GearSpeedRange gearSpeedRange = gearSpeedRanges[i];

      int previousGearSpeedRangeOverlapping = 0;
      for (int j = i - 1; j >= 0; j--) {
        GearSpeedRange previousGearSpeedRange = gearSpeedRanges[j];
        if (gearSpeedRange.getLowerLimit() <= previousGearSpeedRange.getUpperLimit()) {
          previousGearSpeedRangeOverlapping++;
        }
      }

      if (previousGearSpeedRangeOverlapping != 1) {
        throw new IllegalArgumentException(
                String.format("Non Adjacent Overlapping(%d) for Gear: %d with previous Gears",
                        previousGearSpeedRangeOverlapping, i + 1));
      }
    }
  }

  /**
   * Checks if overlapping exists between a gear and the gears after it. If any such overlapping
   * exists and the gears are non adjacent then it throws an exception.
   *
   * @throws IllegalArgumentException if non adjacent gears overlaps
   */
  private void checkNonAdjacentGearOverLappingWithNextGears() throws IllegalArgumentException {
    for (int i = 1; i < gearSpeedRanges.length - 1; i++) {
      GearSpeedRange gearSpeedRange = gearSpeedRanges[i];

      int nextGearSpeedRangeOverlapping = 0;
      for (int k = i + 1; k < gearSpeedRanges.length; k++) {
        GearSpeedRange nextGearSpeedRange = gearSpeedRanges[k];
        if (gearSpeedRange.getUpperLimit() >= nextGearSpeedRange.getLowerLimit()) {
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


  private boolean canIncreaseSpeedInCurrentGear(int intendedSpeed) {
    return intendedSpeed <= this.gearSpeedRanges[this.currentGear].getUpperLimit();
  }

  private boolean canDecreaseSpeedInCurrentGear(int intendedSpeed) {
    return intendedSpeed >= this.gearSpeedRanges[this.currentGear].getLowerLimit();
  }

  private boolean doesHigherGearExist() {
    return this.currentGear + 1 < TOTAL_GEARS_IN_VEHICLE;
  }

  private boolean doesLowerGearExist() {
    return this.currentGear - 1 >= 0;
  }

  private boolean canShiftToHigherGear() {
    return this.currentSpeed >= gearSpeedRanges[this.currentGear + 1].getLowerLimit();
  }

  private boolean canShiftToLowerGear() {
    return this.currentSpeed <= this.gearSpeedRanges[this.currentGear - 1].getUpperLimit();
  }
}
