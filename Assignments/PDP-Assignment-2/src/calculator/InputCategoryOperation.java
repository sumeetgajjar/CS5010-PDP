package calculator;

import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;

public interface InputCategoryOperation {
  Set<InputCategory> getNextAnticipatedInputCategory(InputCategory inputCategory);

  List<String> performOperation(char input, List<String> expression);
}
