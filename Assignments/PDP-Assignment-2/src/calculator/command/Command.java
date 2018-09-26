package calculator.command;

import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;

/**
 * This interface represents a Command that a Calculator can perform. The command can be just a
 * simple action of accepting an input or a complex task of evaluating the expression. The
 * <tt>performAction(char input)</tt> throws an RuntimeException if the given input causes an
 * operand overflow for the calculator.
 */
public interface Command {

  /**
   * Returns if the given character belongs to valid character set of the command.
   *
   * @param input input character
   * @return if the given character belongs to valid character set of the command
   */
  boolean doesInputCharacterBelongToCommand(char input);

  /**
   * Returns the associated {@link CommandName} with the Command.
   *
   * @return the associated {@link CommandName} with the Command
   */
  CommandName getCommandName();

  /**
   * Returns the algebraic expression generated as result of performing action on the given input.
   * It throws an RuntimeException if the given input causes an operand overflow for the calculator
   *
   * @param input input character
   * @return the algebraic expression generated as result of performing action on the given input
   * @throws RuntimeException if the input character causes operand overflow
   */
  List<String> performAction(char input) throws RuntimeException;

  /**
   * Returns a {@link Set} of valid {@link CommandName} that the next input character can represent
   * after successful execution of the current command.
   */
  Set<CommandName> getNextValidCommands();
}
