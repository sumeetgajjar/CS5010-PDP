package calculator.bean;

import java.util.HashMap;
import java.util.Map;

public enum Operator {
  ADD('+') {
    @Override
    public int performOperation(int n1, int n2) {
      return Math.addExact(n1, n2);
    }
  },
  SUBTRACT('-') {
    @Override
    public int performOperation(int n1, int n2) {
      return Math.subtractExact(n1, n2);
    }
  },
  MULTIPLY('*') {
    @Override
    public int performOperation(int n1, int n2) {
      return Math.multiplyExact(n1, n2);
    }
  };

  private static final Map<Character, Operator> SYMBOL_OPERATOR_MAPPING = new HashMap<Character, Operator>() {{
    for (Operator operator : Operator.values()) {
      this.put(operator.getSymbol(), operator);
    }
  }};

  private final char symbol;

  Operator(char symbol) {
    this.symbol = symbol;
  }

  public abstract int performOperation(int n1, int n2) throws ArithmeticException;

  public char getSymbol() {
    return symbol;
  }

  public static Operator getOperator(char symbol) {
    return Operator.SYMBOL_OPERATOR_MAPPING.get(symbol);
  }
}
