package vehicle.bean;

public class GearSpeedRange {
  private final int lowSpeed;
  private final int highSpeed;

  public GearSpeedRange(int lowSpeed, int highSpeed) {
    this.lowSpeed = lowSpeed;
    this.highSpeed = highSpeed;
  }

  public int getLowSpeed() {
    return lowSpeed;
  }

  public int getHighSpeed() {
    return highSpeed;
  }
}
