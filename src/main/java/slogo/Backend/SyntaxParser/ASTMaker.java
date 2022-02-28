package slogo.Backend.SyntaxParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ResourceBundle;
import slogo.Backend.LexicalAnalyzer.Token;

public class ASTMaker {
  private final ArrayDeque<Token> tokens;
  private final ArrayDeque<Operator> unevaluated = new ArrayDeque<>();
  private final ArrayDeque<Operator> evaluated = new ArrayDeque<>();
  private Operator root;

  private final String rootdirectory = "slogo.Backend.SyntaxParser.";


  public ASTMaker(ArrayDeque<Token> tokens) {
    this.tokens = tokens;
  }

  public Operator parse() {
    createArgumentStacks();
    generateAST();
    return root;
  }

  private void createArgumentStacks() {
    // TODO: create operands for all tokens and place them in the correct initial stack
    int seqNum = 0;

    while (!tokens.isEmpty()) {
      Token t = tokens.getFirst();
      String tokenType = t.getTyoe().toString();
      ResourceBundle resources = ResourceBundle.getBundle(rootdirectory + "CommandToClassDirectory");

      try {
        // System.out.println(tokenType);
        Class<?> operatorType;
        Operator nextOperator;
        if (tokenType.equals("COMMAND")) {
          // operatorType = Class.forName("slogo.Backend.SyntaxParser." + "Command");
          operatorType = Class.forName(rootdirectory + resources.getString(t.getValue()) + "."  + t.getValue());
          // operatorType = Class.forName("Command");
          Constructor<?> constructor = operatorType.getConstructor(int.class);
          nextOperator = (Operator) constructor.newInstance(seqNum);
        } else {
          operatorType = Class.forName(rootdirectory + "Constant");
          Constructor<?> constructor = operatorType.getConstructor(int.class, double.class);
          nextOperator = (Operator) constructor.newInstance(seqNum, Double.parseDouble(t.getValue()));
        }

        if (tokenType == "CONSTANT") {
          evaluated.addLast(nextOperator);
        } else {
          unevaluated.addLast(nextOperator);
        }
      } catch (ClassNotFoundException e) {
        // TODO: REPLACE THIS LATER
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      tokens.removeFirst();
      seqNum++;
    }
  }

  private void generateAST() {
    // TODO: use the stacks of operands to generate the AST;
    while (!unevaluated.isEmpty()) {
      Operator nextOperator = unevaluated.getFirst();
      handleOperator(nextOperator);
      unevaluated.removeFirst();
    }
    root = evaluated.pop();
  }

  private void handleOperator(Operator operator) {
    int numOperands = operator.getMyNumArgs();
    while (numOperands > 0) {
      operator.addArgument(evaluated.pop());
      numOperands--;
    }
    evaluated.addLast(operator);
  }
}
