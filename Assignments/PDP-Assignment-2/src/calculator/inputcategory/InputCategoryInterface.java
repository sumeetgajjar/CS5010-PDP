package calculator.inputcategory;

import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;

public interface InputCategoryInterface {

  boolean belongToInputCategory(char input);

  InputCategory getInputCategory();

  List<String> performAction(char input);

  Set<InputCategory> getNextValidInputCategorySet();
}
