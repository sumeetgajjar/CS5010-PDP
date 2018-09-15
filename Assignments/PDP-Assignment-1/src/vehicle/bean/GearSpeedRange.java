package vehicle.bean;

/**
 * This class represents the range of a speed for the Gear. It consists of lowerLimit and
 * upperLimit.
 */
public class GearSpeedRange {
  private final int lowerLimit;
  private final int upperLimit;

  public GearSpeedRange(int lowerLimit, int upperLimit) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
  }

  public int getLowerLimit() {
    return lowerLimit;
  }

  public int getUpperLimit() {
    return upperLimit;
  }
}
