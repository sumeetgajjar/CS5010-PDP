import org.junit.Assert;
import org.junit.Test;

/**
 * A JUnit Test Class for SimpleProjectile.
 */
public class SimpleProjectileTest {

  @Test
  public void testNegativeTime() {
    Particle particle = new SimpleProjectile(1.1f, 3f, 0f, 0f);
    Assert.assertEquals(particle.getState(-1), "At time -1.00: position is (1.10,3.00)");
  }

  @Test
  public void testOriginZeroVelocity() {
    Particle particle = new SimpleProjectile(0.00f, 0.00f, 0.00f, 0.00f);
    Assert.assertEquals(particle.getState(0.00f), "At time 0.00: position is (0.00,0.00)");
  }

  @Test
  public void testNonOriginZeroXVelocity() {
    Particle particle = new SimpleProjectile(1.1f, 3f, 0f, 10f);
    Assert.assertEquals(particle.getState(0.1f), "At time 0.10: position is (1.10,3.95)");
  }

  @Test
  public void testNonOriginZeroYVelocity() {
    Particle particle = new SimpleProjectile(1.1f, 0f, 10f, 0f);
    Assert.assertEquals(particle.getState(10), "At time 10.00: position is (1.10,0.00)");
  }

  @Test
  public void testOrigin45Degree() {
    Particle particle = new SimpleProjectile(0f, 0f, 10f, 10f);
    Assert.assertEquals(particle.getState(2f), "At time 2.00: position is (20.00,0.38)");
  }

  @Test
  public void testNonOriginObliqueMovement() {
    Particle particle = new SimpleProjectile(-4.5f, 4f, 3.5f, 4.6f);
    Assert.assertEquals(particle.getState(0.5f), "At time 0.50: position is (-2.75,5.07)");
  }
}