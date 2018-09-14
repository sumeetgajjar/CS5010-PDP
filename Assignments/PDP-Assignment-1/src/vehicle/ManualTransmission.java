package vehicle;

public interface ManualTransmission {

  String getStatus();

  int getSpeed();

  int getGear();

  ManualTransmission increaseSpeed();

  ManualTransmission decreaseSpeed();

  ManualTransmission increaseGear();

  ManualTransmission decreaseGear();
}
