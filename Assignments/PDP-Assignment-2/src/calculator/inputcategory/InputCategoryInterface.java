package calculator.inputcategory;

import java.util.List;
import java.util.Set;

import calculator.bean.InputCategoryName;

public interface InputCategoryInterface {

  boolean belongToInputCategory(char input);

  InputCategoryName getInputCategory();

  List<String> performAction(char input) throws RuntimeException;

  Set<InputCategoryName> getNextValidInputCategorySet();
}
