package calculator;

import java.util.List;
import java.util.Set;

import calculator.bean.InputCategory;

public class SimpleClearInputCategoryOperation implements InputCategoryOperation {

  @Override
  public Set<InputCategory> getNextAnticipatedInputCategory(InputCategory inputCategory) {
    return null;
  }

  @Override
  public List<String> performOperation(char input, List<String> expression) {
    return null;
  }
}
