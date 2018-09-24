package calculator.bean;

import java.util.HashMap;
import java.util.Map;

public enum Operation {
  ADD('+') {
    @Override
    public int perform(int n1, int n2) {
      return Math.addExact(n1, n2);
    }
  },
  SUBTRACT('-') {
    @Override
    public int perform(int n1, int n2) {
      return Math.subtractExact(n1, n2);
    }
  },
  MULTIPLY('*') {
    @Override
    public int perform(int n1, int n2) {
      return Math.multiplyExact(n1, n2);
    }
  };

  private static final Map<Character, Operation> SYMBOL_OPERATION_MAPPING = new HashMap<Character, Operation>() {{
    for (Operation operation : Operation.values()) {
      this.put(operation.getSymbol(), operation);
    }
  }};

  private final char symbol;

  Operation(char symbol) {
    this.symbol = symbol;
  }

  public abstract int perform(int n1, int n2) throws ArithmeticException;

  public char getSymbol() {
    return symbol;
  }

  public static Operation getOperation(char symbol) {
    return Operation.SYMBOL_OPERATION_MAPPING.get(symbol);
  }
}
