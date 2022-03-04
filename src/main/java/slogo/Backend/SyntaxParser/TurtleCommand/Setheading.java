package slogo.Backend.SyntaxParser.TurtleCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import slogo.Backend.SyntaxParser.LogoRuntimeState;
import slogo.Backend.SyntaxParser.Operator;

public class Setheading extends Operator {

  public Setheading(int seqNum){
    super(seqNum);
    myNumArgs = 1;
  }

  public Setheading(Operator op1){
    super(0);
    arguments.add(op1);
  }

  public double getRetVal(LogoRuntimeState runtimeState)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    double retVal = arguments.get(0).getRetVal(runtimeState);
    ArrayList<Double> argList = new ArrayList<>();
    argList.add(retVal);
    runtimeState.getHistory().addTurtleThroughTransformation("Setheading", argList);
    return retVal;
  }

}
