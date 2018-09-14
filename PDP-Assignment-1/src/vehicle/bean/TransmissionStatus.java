package vehicle.bean;

public enum TransmissionStatus {

  OK("OK: everything is OK."),
  MAY_INCREASE_GEAR("OK: you may increase the gear."),
  MAY_DECREASE_GEAR("OK: you may decrease the gear."),
  CANNOT_INCREASE_SPEED_INCREASE_GEAR_FIRST("Cannot increase speed, increase gear first."),
  CANNOT_DECREASE_SPEED_DECREASE_GEAR_FIRST("Cannot decrease speed, decrease gear first."),
  CANNOT_INCREASE_GEAR_INCREASE_SPEED_FIRST("Cannot increase gear, increase speed first."),
  CANNOT_DECREASE_GEAR_DECREASE_SPEED_FIRST("Cannot decrease gear, decrease speed first."),
  CANNOT_INCREASE_SPEED_REACHED_MAX_SPEED("Cannot increase speed. Reached maximum speed."),
  CANNOT_DECREASE_SPEED_REACHED_MIN_SPEED("Cannot decrease speed. Reached minimum speed."),
  CANNOT_INCREASE_GEAR_REACHED_MAX_GEAR("Cannot increase gear. Reached maximum gear."),
  CANNOT_DECREASE_GEAR_REACHED_MIN_GEAR("Cannot decrease gear. Reached minimum gear.");

  private final String statusMessage;

  TransmissionStatus(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public String getStatusMessage() {
    return statusMessage;
  }
}
