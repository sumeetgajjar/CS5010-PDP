import org.junit.Before;
import org.junit.Test;

import cs5010.dummy.Person;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class for the Person.
 */
public class PersonTest {

  private Person john;

  @Before
  public void setUp() {

    john = new Person("John", "Doe", 1945);
  }

  @Test
  public void testFirst() {
    assertEquals(john.getFirstName(), "John");

  }

  @Test
  public void testSecond() {
    assertEquals(john.getLastName(), "Doe");
  }

  @Test
  public void testYearOfBirth() {
    assertEquals(john.getYearOfBirth(), 1945);
  }

}
