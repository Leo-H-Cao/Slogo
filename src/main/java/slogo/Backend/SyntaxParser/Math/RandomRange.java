package slogo.Backend.SyntaxParser.Math;

import java.lang.reflect.InvocationTargetException;
import slogo.Backend.SyntaxParser.Operator;

public class RandomRange extends ArithmeticOp {
  public RandomRange() {
    myNumArgs = 2;
  }

  public RandomRange(Operator op1, Operator op2) {
    super();
    this.arguments.add(op1);
    this.arguments.add(op2);
    myNumArgs = 2;
  }

  public double getRetVal()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    // TODO: Check to make sure max >= min
    double min = this.arguments.get(0).getRetVal();
    double max = this.arguments.get(1).getRetVal();
    return (Math.random() * (max - min)) + min;
  }
}
