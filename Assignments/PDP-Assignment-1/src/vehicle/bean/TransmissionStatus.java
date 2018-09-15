package vehicle.bean;

/**
 * This enum represents all the Transmission status the ManualTransmission can have.
 */
public enum TransmissionStatus {

  /**
   * Indicates the speed or gear (but not both) was changed successfully.
   * <ul>
   * <li>If speed was changed then it was changed successfully without changing gear</li>
   * <li>If gear was changed then it was changed successfully without changing speed</li>
   * </ul>
   */
  OK("OK: everything is OK."),

  /**
   * Indicates the speed was increased successfully, but it is now within the range of the next
   * gear.
   */
  MAY_INCREASE_GEAR("OK: you may increase the gear."),

  /**
   * Indicates the speed was decreased successfully, but it is now within the range of the previous
   * gear.
   */
  MAY_DECREASE_GEAR("OK: you may decrease the gear."),

  /**
   * Indicates the speed cannot be increased more unless the gear is increased first. This implies
   * that the intended speed is too high for the current gear.
   */
  CANNOT_INCREASE_SPEED_INCREASE_GEAR_FIRST("Cannot increase speed, increase gear first."),

  /**
   * Indicates the speed cannot be decreased more unless the gear is decreased first. This implies
   * that the intended speed is too low for the current gear.
   */
  CANNOT_DECREASE_SPEED_DECREASE_GEAR_FIRST("Cannot decrease speed, decrease gear first."),

  /**
   * Indicates the gear cannot be increased more unless the speed is increased first. This implies
   * that the current speed will be too low for the next gear.
   */
  CANNOT_INCREASE_GEAR_INCREASE_SPEED_FIRST("Cannot increase gear, increase speed first."),

  /**
   * Indicates the gear cannot be decreased more unless the speed is decreased first. This implies
   * that the current speed will be too high for the previous gear.
   */
  CANNOT_DECREASE_GEAR_DECREASE_SPEED_FIRST("Cannot decrease gear, decrease speed first."),

  /**
   * Indicates the speed cannot be increased as it will go beyond the speed limit of the vehicle.
   */
  CANNOT_INCREASE_SPEED_REACHED_MAX_SPEED("Cannot increase speed. Reached maximum speed."),

  /**
   * Indicates the speed cannot be decreased as it is already 0.
   */
  CANNOT_DECREASE_SPEED_REACHED_MIN_SPEED("Cannot decrease speed. Reached minimum speed."),

  /**
   * Indicates the gear cannot be increased as it is already in highest gear.
   */
  CANNOT_INCREASE_GEAR_REACHED_MAX_GEAR("Cannot increase gear. Reached maximum gear."),

  /**
   * Indicates the gear cannot be decreased as it is already in lowest gear.
   */
  CANNOT_DECREASE_GEAR_REACHED_MIN_GEAR("Cannot decrease gear. Reached minimum gear.");

  private final String statusMessage;

  TransmissionStatus(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public String getStatusMessage() {
    return statusMessage;
  }
}
