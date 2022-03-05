package slogo.Backend.State;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import slogo.Backend.FileReadingException;
import slogo.Backend.LexicalAnalyzer.InvalidTokenException;
import slogo.Backend.LexicalAnalyzer.LexResult;
import slogo.Backend.Preferences;
import slogo.Backend.Result;
import slogo.Backend.SyntaxParser.ASTMaker;
import slogo.Backend.SyntaxParser.LogoRuntimeState;
import slogo.Backend.SyntaxParser.Operator;
import slogo.Backend.TurtleState.Turtle;
import slogo.BackendExternalAPIs.StateManager;
import slogo.BackendExternalAPIs.Initialiazable;
import slogo.BackendExternalAPIs.ModifiesModelState;
import slogo.Backend.className;
import slogo.SLogoController;

/**
 * This class has the "brains" of the Model - it has the Turtle for model (or Turtles), as well as
 * other "stateful" information contained in runtimeState
 * @author Alex & Edison
 */
public class ModelState implements Initialiazable, ModifiesModelState, StateManager {
  private CommandLanguage commandLanguage;
  private Turtle turtle;
  private LogoRuntimeState runtimeState;
  private UserVariables userVariables;
  private UserCommands userCommands;

  /**
   * Class constructor.
   */
  public ModelState(Preferences prefs) {
    // All this initialization could go into initializeBackend()
    this.turtle = SLogoController.INITIAL_TURTLE;
    this.commandLanguage = prefs.getLanguage();
    //this.tracks = new Tracks();
    this.runtimeState = new LogoRuntimeState();
    this.userVariables = new UserVariables();
    this.userCommands = null; //FIX
  }

  /**
   * This methods sends an instruction entered by the user to the backend.
   * <p>
   * If the instruction is "successful" meaning it is syntactically and semantically valid, then
   * this method will return a Deque of Turtle objects which in their totality execute the command
   * from the user by the end of the Deque, but where adjacent Turtle objects in the Deque are
   * separated by one basic command.
   * <p>
   * If the instruction is "unsuccessful," meaning that it is either syntactically and/or
   * semantically invalid or unsuccessful for any other reason, then this method will throw an
   * InvalidTokenException or an instance of one of its subclasses.
   *
   * @param instructionText a String with the instruction text entered by the user to be executed
   * @return Deque<Turtle> which has the Turtle objects created by executing the instruction, each
   * subsequent Turtle object separated by only one basic command.
   */
  @Override
  public Result postInstruction(String instructionText)
      throws InvalidTokenException, ClassNotFoundException, InvocationTargetException,
      NoSuchMethodException, InstantiationException, IllegalAccessException {
    LexResult lexedString = new LexResult(instructionText, this.commandLanguage);
    ASTMaker astMaker = new ASTMaker(lexedString.getEvaluatedTokens());
    Operator root =  astMaker.parse();
    LogoRuntimeState runtimeState = new LogoRuntimeState();
    ArrayDeque<Turtle> currentHistory = new ArrayDeque<>();
    currentHistory.addLast(turtle);
    runtimeState.getHistory().getTurtleHistory().addLast(currentHistory);
    Result res = new Result(root.getRetVal(runtimeState), runtimeState.getHistory().getTurtleHistory().getLast());
    turtle = runtimeState.getHistory().getTurtleHistory().getLast().getLast();
    return res;
  }




  /**
   * Gets history.
   *
   * @return the history
   */
  @Override
  public TurtleHistory getTurtleHistory() {
    return this.runtimeState.getHistory();
  }

  /**
   * Gets user variables.
   *
   * @return the user variables
   */
  @Override
  public UserVariables getUserVariables() {
    return userVariables;
  }

  /**
   * Gets user commands.
   *
   * @return the user commands
   */
  @Override
  public UserCommands getUserCommands() {
    return userCommands;
  }

  /**
   * Gets command language.
   *
   * @return the command language
   */
  @Override
  public CommandLanguage getCommandLanguage() {
    return commandLanguage;
  }

  /**
   * @param overrides is the information needed to initialize the backend
   * @return Boolean which is True on initialization success and False otherwise
   */
  @Override
  public ModelState initalizeBackend(Preferences overrides) {
    return null;
  }

  /**
   * This method initializes the backend by telling the model to load files containing a previous
   * state of the backend. It throws a FileNotFoundException if any of the Path objects given do not
   * lead to files.
   *
   * @param state the state
   * @return ModelState object which was initialized by the backend
   * @throws FileNotFoundException is thrown when a file is not found at any of the given Path
   *                               objects
   * @throws FileReadingException  the file reading exception is throw when the file Path objects
   *                               point to files that exist, but the backend is unable to read
   *                               them
   */
  @Override
  public ModelState loadBackendStateFromFiles(HashMap<className, Path> state)
      throws FileNotFoundException, FileReadingException {
    return null;
  }

  /**


  /**
   * Sets command language. See
   * https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#toUpperCase() for why
   * toUpperCase(Locale.ENGLISH)
   *
   */
  @Override
  public void setCommandLanguage(String commandLanguage)
      throws IllegalArgumentException, NullPointerException {
    Boolean success = Boolean.FALSE;
    try {
      this.commandLanguage = CommandLanguage.valueOf(commandLanguage.toUpperCase(Locale.ENGLISH));
      success = Boolean.TRUE;
    } catch (IllegalArgumentException | NullPointerException exception) {
      Logger.getGlobal()
          .throwing(
              this.getClass().getName(),
              this.getClass().getEnclosingMethod().toGenericString(),
              exception);
    } catch (Exception exception) {
      Logger.getGlobal()
          .logp(
              Level.SEVERE,
              this.getClass().getName(),
              this.getClass().getEnclosingMethod().toGenericString(),
              "Unexpected exception not in method signature",
              exception);
    }
    return;
  }
}
