package calculator.command;

import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;

/**
 * This interface represents a Command that a Calculator can perform. The command can be just a
 * simple action of accepting an input or a complex task of evaluating the expression.
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
   * Returns the {@link CommandName} associated with the Command.
   *
   * @return the {@link CommandName} associated with the Command
   */
  CommandName getCommandName();

  /**
   * Returns the algebraic expression generated as result of performing action on the given input.
   *
   * @param input input character
   * @return the algebraic expression generated as result of performing action on the given input
   */
  List<String> performAction(char input);

  /**
   * Returns a {@link Set} of valid {@link CommandName} that the next input character can represent
   * after execution of the current command.
   *
   * @return a set of valid CommandName that the next input character can belong to
   */
  Set<CommandName> getNextValidCommands();
}
