package calculator.command;

import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;

public interface Command {

  boolean doesInputCharacterBelongToCommand(char input);

  CommandName getCommandName();

  List<String> performAction(char input) throws RuntimeException;

  Set<CommandName> getNextValidCommands();
}
