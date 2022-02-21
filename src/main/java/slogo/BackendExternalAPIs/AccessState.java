package slogo.BackendExternalAPIs;

import slogo.Backend.State.CommandLanguage;
import slogo.Backend.State.History;
import slogo.Backend.State.Tracks;
import slogo.Backend.TurtleState.Turtle;
import slogo.Backend.State.UserCommands;
import slogo.Backend.State.UserVariables;

public interface AccessState {
  /**
   * This method retrives the current Turtle state
   * from the backend. This turtle state is returned
   * in the form of a Turtle object, which has all
   * of the information relevant to the state of the
   * turtle which is relevant to the backend.
   *
   * @return Turtle object with the current turtle state
   */
  Turtle getCurrentTurtleState();

  /**
   * A method which calls on the backend to get all the tracks created by the Turtle
   * from the perspective of the backend. The returned object can be thought of
   * as a state machine that has all the information on the tracks that the backend knows about.
   *
   * @return Tracks object which has the backend's understanding of the current tracks
   */
  Tracks getTracks();

  /**
   * Gets history.
   *
   * @return the history
   */
  History getHistory();

  /**
   * Gets user variables.
   *
   * @return the user variables
   */
  UserVariables getUserVariables();

  /**
   * Gets user commands.
   *
   * @return the user commands
   */
  UserCommands getUserCommands();

  /**
   * Gets command language.
   *
   * @return the command language
   */
  CommandLanguage getCommandLanguage();
}