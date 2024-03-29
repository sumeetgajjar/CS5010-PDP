package vehicle;

/**
 * This interface represents a set of operations that a ManualTransmission can perform.
 */
public interface ManualTransmission {

  /**
   * Returns the current status of the ManualTransmission.
   *
   * @return the current status of the ManualTransmission
   */
  String getStatus();

  /**
   * Returns the current speed of the vehicle.
   *
   * @return the current speed of the vehicle
   */
  int getSpeed();

  /**
   * Returns the current gear of the vehicle.
   *
   * @return the current gear of the vehicle
   */
  int getGear();

  /**
   * Increases the speed of the vehicle by fixed amount. The speed change amount is up to
   * implementation. If speed can be increased without changing gears, it returns the
   * ManualTransmission object with the updated speed else returns the ManualTransmission object
   * with the same speed as before.
   *
   * @return the ManualTransmission object with the current info
   */
  ManualTransmission increaseSpeed();

  /**
   * Decreases the speed of the vehicle by fixed amount. The speed change amount is up to
   * implementation. If speed can be decreased without changing gears, it returns the
   * ManualTransmission object with the updated speed else returns the ManualTransmission object
   * with the same speed as before.
   *
   * @return the ManualTransmission object with the current info
   */
  ManualTransmission decreaseSpeed();

  /**
   * Increases the gear by 1. If gear can be increased without increasing the speed, it returns the
   * ManualTransmission object with the updated gear else returns the ManualTransmission object with
   * the same gear.
   *
   * @return the ManualTransmission object with the current info
   */
  ManualTransmission increaseGear();

  /**
   * Decreases the gear by 1. If gear can be decreased without decreasing the speed, it returns the
   * ManualTransmission object with the updated gear else returns the ManualTransmission object with
   * the same gear.
   *
   * @return the ManualTransmission object with the current info
   */
  ManualTransmission decreaseGear();
}
