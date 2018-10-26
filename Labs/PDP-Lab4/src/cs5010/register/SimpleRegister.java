package cs5010.register;

import java.util.Map;

/**
 * Created by gajjar.s, on 1:37 PM, 10/26/18
 */
public class SimpleRegister implements CashRegister {

  @Override
  public void addPennies(int num) throws IllegalArgumentException {

  }

  @Override
  public void addNickels(int num) throws IllegalArgumentException {

  }

  @Override
  public void addDimes(int num) throws IllegalArgumentException {

  }

  @Override
  public void addQuarters(int num) throws IllegalArgumentException {

  }

  @Override
  public void addOnes(int num) throws IllegalArgumentException {

  }

  @Override
  public void addFives(int num) throws IllegalArgumentException {

  }

  @Override
  public void addTens(int num) throws IllegalArgumentException {

  }

  @Override
  public Map<Integer, Integer> withdraw(int dollars, int cents) throws InsufficientCashException {
    return null;
  }

  @Override
  public Map<Integer, Integer> getContents() {
    return null;
  }

  @Override
  public String getAuditLog() {
    return null;
  }
}
