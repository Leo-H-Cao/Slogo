package slogo.Frontend.ToolBarButtons;

import javafx.scene.paint.Color;
import slogo.Frontend.TurtleBackground;

public class BackgroundColorPicker extends ColorPickerAbstract {
  private static final String BACKGROUND_COLOR_LABEL = "  Background:";

  public BackgroundColorPicker(TurtleBackground turtleBackground){
    super(BACKGROUND_COLOR_LABEL);
    colorPicker.setValue(Color.ALICEBLUE);
    colorPicker.setOnAction(ActionEvent -> {
      turtleBackground.setBackGroundColor(colorPicker.getValue());
    });
  }
}

