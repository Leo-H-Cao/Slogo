package slogo.Frontend;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import slogo.Backend.TurtleState.Turtle;
import slogo.FrontendExternalAPIs.DisplayTurtle;

public class TurtleView implements DisplayTurtle {


  public static final int TURTLE_SIZE = 40;
  public static final int DEFAULT_SPEED = 4;

  private ImageView turtleImage;
  private Turtle currentTurtle;
  private Turtle initialTurtle;
  private double myAnimationSpeed;

  public TurtleView(double animationSpeed, String turtleImagePath) {
    createTurtleImage(turtleImagePath);
    myAnimationSpeed = animationSpeed;

//    set current and initial turtle
//    currentTurtle = initTurtle;
//    initialTurtle = initTurtle;
  }

  // create sequence of animations
  Animation makeAnimation(Turtle nextTurtle) {
    // create a path for the animation to follow
    Path path = new Path();
    path.getElements()
        .addAll(
            new MoveTo(currentTurtle.getLocation().getX(), currentTurtle.getLocation().getY()),
            new LineTo(nextTurtle.getLocation().getX(), nextTurtle.getLocation().getY()));
    // create an animation that follows the path
    PathTransition pt =
        new PathTransition(Duration.seconds(DEFAULT_SPEED / myAnimationSpeed), path, turtleImage);
    // animation that rotates the turtle before ending
    RotateTransition rt = new RotateTransition(Duration.seconds(DEFAULT_SPEED / myAnimationSpeed));
    double angleToRotate = nextTurtle.getDirection().getDirectionInDegrees() - currentTurtle.getDirection().getDirectionInDegrees();
    rt.setByAngle(angleToRotate);
    // put them together in order
    return new SequentialTransition(turtleImage, pt, rt);
  }

  /**
   * creates turtle at coordinates specified by turtle object
   *
   * @param newTurtle
   */
  @Override
  public void createTurtle(Turtle newTurtle){
    currentTurtle = newTurtle;
    initialTurtle = newTurtle;
    resetTurtle();
  }

  // create something to animate
  private void createTurtleImage(String turtleImagePath) {
    Image image = new Image(turtleImagePath);
    turtleImage = new ImageView(image);
    turtleImage.setId("turtleImage");
    turtleImage.setFitWidth(TURTLE_SIZE);
    turtleImage.setFitHeight(TURTLE_SIZE);
    turtleImage.getStyleClass().add("turtle-image");
  }

  public Node getTurtleNode() {
    return turtleImage;
  }

  /**
   * move turtle to coordinates as specified by turtle object
   *
   * @param nextTurtle
   */
  @Override
  public void moveTurtle(Turtle nextTurtle) {
    makeAnimation(nextTurtle).play();
    currentTurtle = nextTurtle;
  }

  /**
   * Change image for turtle (instead of CSS style's default)
   *
   * @param turtleImage
   */
  @Override
  public void setTurtleImage(Image turtleImage) {
    this.turtleImage.setImage(turtleImage);
  }

  @Override
  public void rotateTurtle() {}

  /**
   * returns current turtle graphic
   *
   * @return
   */
  @Override
  public Image getCurrentTurtleImage() {
    return turtleImage.getImage();
  }

  /** clears screen and resets turtle to original position */
  public void resetTurtle() {
    turtleImage.setX(initialTurtle.getLocation().getX());
    turtleImage.setX(initialTurtle.getLocation().getY());
  }

}
