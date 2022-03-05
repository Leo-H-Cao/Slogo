package slogo.Backend.SyntaxParser.ControlFlow;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Backend.SyntaxParser.LogoRuntimeState;

public class IfelseTest {
  Ifelse myIfelse;
  private LogoRuntimeState myRuntimeState;

  @BeforeEach
  void setup() {
    myIfelse = new Ifelse(0);


  }


  @Test
  void testGetMyNumArgs() {
    assertEquals(3, myIfelse.getMyNumArgs());
  }

}