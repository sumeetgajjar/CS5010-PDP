import org.junit.Before;
import org.junit.Test;

import cs5010.dummy.Book;
import cs5010.dummy.Person;

import static org.junit.Assert.assertEquals;

/**
 * A Junit Test Class for Book.
 */
public class BookTest {

  private Book book;

  @Before
  public void setUp() {
    Person author = new Person("John", "Doe", 1945);
    this.book = new Book("The Book", author, 99.99f);
  }

  @Test
  public void testTitle() {
    assertEquals(this.book.getTitle(), "The Book");
  }

  @Test
  public void testPrice() {
    assertEquals(this.book.getPrice(), 99.99f, 0f);
  }

  @Test
  public void testAuthor() {
    assertEquals(this.book.getAuthor().getFirstName(), "John");
    assertEquals(this.book.getAuthor().getLastName(), "Doe");
    assertEquals(this.book.getAuthor().getYearOfBirth(), 1945);
  }
}