package calculator.command;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;

/**
 * This class provides a skeletal implementation of the {@link Command} interface. It minimize the
 * effort required to implement this interface. It has following protected fields.
 * <ul>
 * <li><code>expression</code>: current algebraic expression</li>
 * <li><code>supportedCharacters</code>: characters supported by the command</li>
 * <li><code>commandName</code>: name of the Command</li>
 * </ul>
 *
 * <p>To implement a {@link Command} the programmer needs to extend this class and provide
 * implementations for following methods.
 * <ul>
 * <li>performAction</li>
 * <li>getNextValidCommands</li>
 * </ul>
 * The programmer should also provide a constructor, of following signature Command({@link List}
 * expression, {@link Set} supportedCharacters)
 */
public abstract class AbstractCommand implements Command {

  protected final List<String> expression;
  protected final Set<Character> supportedCharacters;
  protected final CommandName commandName;

  /**
   * Protected Constructor for invocation by subclass constructors. It initializes the fields to
   * given values. This values once initialized cannot be modified.
   *
   * @param commandName       the name of the command
   * @param supportCharacters set of characters supported by the command
   * @param expression        current algebraic expression
   */
  protected AbstractCommand(CommandName commandName, Set<Character> supportCharacters, List<String> expression) {
    this.commandName = commandName;
    this.expression = Collections.unmodifiableList(expression);
    this.supportedCharacters = Collections.unmodifiableSet(supportCharacters);
  }

  /**
   * Checks if the given character is present the supportedCharacters {@link Set}.
   *
   * @param input input character
   * @return if the given character is present the supportedCharacters {@link Set}
   */
  @Override
  public boolean doesInputCharacterBelongToCommand(char input) {
    return this.supportedCharacters.contains(input);
  }

  /**
   * returns the command name.
   *
   * @return the command name
   */
  @Override
  public CommandName getCommandName() {
    return this.commandName;
  }
}
