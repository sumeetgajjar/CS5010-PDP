package vehicle.bean;

/**
 * This class represents the range of a speed for the Gear. It consists of lowerLimit and
 * upperLimit.
 */
public class GearSpeedRange {
  private final int lowerLimit;
  private final int upperLimit;

  /**
   * Constructs the GearSpeedRange object with given lower and upper limit of speed.
   *
   * @param lowerLimit lower limit of the gear speed range
   * @param upperLimit upper limit of the gear speed range
   */
  public GearSpeedRange(int lowerLimit, int upperLimit) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
  }

  /**
   * Returns the lower limit of the gear speed range.
   *
   * @return the lower limit of the gear speed range.
   */
  public int getLowerLimit() {
    return lowerLimit;
  }

  /**
   * Returns the upper limit of the gear speed range.
   *
   * @return the upper limit of the gear speed range.
   */
  public int getUpperLimit() {
    return upperLimit;
  }
}
