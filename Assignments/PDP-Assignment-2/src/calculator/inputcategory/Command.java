package calculator.inputcategory;

import java.util.List;
import java.util.Set;

import calculator.bean.CommandName;

public interface Command {

  boolean belongToInputCategory(char input);

  CommandName getInputCategory();

  List<String> performAction(char input) throws RuntimeException;

  Set<CommandName> getNextValidInputCategorySet();
}
