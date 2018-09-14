/**
 * This class represents a SimpleProjectile and it implements Particle interface. It has initial
 * (x,y) position and initial (vx,vy) velocity.
 */
public class SimpleProjectile implements Particle {

  /**
   * Vertical Acceleration due to gravity.
   */
  private static final float VERTICAL_ACCELERATION = 9.81f;

  /**
   * Horizontal Acceleration is zero since the particle is only under influence of gravity.
   */
  private static final float HORIZONTAL_ACCELERATION = 0f;

  private float initialXPosition;
  private float initialYPosition;
  private float initialXVelocity;
  private float initialYVelocity;

  /**
   * Constructs a SimpleProjectile object and assigns the given initial (x,y) position and initial
   * (vx,vy) velocity.
   *
   * @param initialXPosition the initial x Position to be given to the SimpleProjectile
   * @param initialYPosition the initial Y Position to be given to the SimpleProjectile
   * @param initialXVelocity the initial x Velocity to be given to the SimpleProjectile
   * @param initialYVelocity the initial y Velocity to be given to the SimpleProjectile
   */
  public SimpleProjectile(float initialXPosition, float initialYPosition,
                          float initialXVelocity, float initialYVelocity) {
    this.initialXPosition = initialXPosition;
    this.initialYPosition = initialYPosition;
    this.initialXVelocity = initialXVelocity;
    this.initialYVelocity = initialYVelocity;
  }

  /**
   * Calculates and returns the state of SimpleProjectile in formatted string.
   *
   * @param givenTime the givenTime at which the state must be obtained
   */
  @Override
  public String getState(float givenTime) {
    float finalXPosition = this.initialXPosition;
    float finalYPosition = this.initialYPosition;

    if (isInvalidTime(givenTime)) {
      return formatResult(givenTime, finalXPosition, finalYPosition);
    }

    float timeToHitGround = getTimeToHitGround();
    float finalTime = Math.min(givenTime, timeToHitGround);

    finalXPosition = calculateXPosition(finalTime);
    finalYPosition = calculateYPosition(finalTime);

    return formatResult(givenTime, finalXPosition, finalYPosition);
  }

  /**
   * Calculates when will projectile hit the ground.
   *
   * @return time at which projectile will hit the ground
   */
  private float getTimeToHitGround() {
    return (2f * this.initialYVelocity) / VERTICAL_ACCELERATION;
  }

  /**
   * Checks if the given time if invalid.
   *
   * @param time the time at which the state must be obtained
   * @return whether the time is invalid or not
   */
  private boolean isInvalidTime(float time) {
    return time < 0;
  }

  /**
   * Calculate the x position at given time.
   *
   * @param time the time at which the x position must be obtained
   * @return the x position at the given time
   */
  private float calculateXPosition(float time) {
    float xPosition = calculatePosition(this.initialXVelocity, time, HORIZONTAL_ACCELERATION);
    return this.initialXPosition + xPosition;
  }

  /**
   * Calculate the y position at given time.
   *
   * @param time the time at which the y position must be obtained
   * @return the y position at the given time
   */
  private float calculateYPosition(float time) {
    float yPosition = calculatePosition(this.initialYVelocity, time, -VERTICAL_ACCELERATION);
    return this.initialYPosition + yPosition;
  }

  /**
   * Calculate the displacement of projectile.
   *
   * @param initialVelocity the initial velocity of the projectile
   * @param time            the time at which displacement is to be obtained
   * @param acceleration    the acceleration of the projectile
   * @return the displacement of projectile at given time
   */
  private float calculatePosition(float initialVelocity, float time, float acceleration) {
    return (initialVelocity * time) + (0.5f * acceleration * time * time);
  }

  /**
   * Formats the final state in required format.
   *
   * @param xPosition the final x position
   * @param yPosition the final y position
   * @return the final state in desired formatted string
   */
  private String formatResult(float time, float xPosition, float yPosition) {
    return String.format("At time %.2f: position is (%.2f,%.2f)", time, xPosition, yPosition);
  }
}
