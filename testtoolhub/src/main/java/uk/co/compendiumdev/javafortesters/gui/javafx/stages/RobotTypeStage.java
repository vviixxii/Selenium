package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.javafx.robottasks.RobotTyperTask;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;

/*
    20170306 knocked up quick typer for my use
    Not ready for prime time
    - does not handle upper case
    - does not handle special chars (except * which is hard coded
    - when fix this then can enable in MainGui
 */

public class RobotTypeStage extends Stage {

    private static RobotTypeStage robotTypeStage =null;
    static RobotTyperTask robotTasker;

    public static void singletonActivate(){

        if(robotTypeStage ==null)
            robotTypeStage = new RobotTypeStage(false);

        robotTypeStage.show();
        robotTypeStage.requestFocus();
    }

    public static EventHandler<ActionEvent> getActivationEvent() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                RobotTypeStage.singletonActivate();
            }
        };
    }

    public RobotTypeStage(boolean hidden) {

        BorderPane root = new BorderPane();

        HBox robotTypeControl = new HBox();
            final Label milliPauseLabel = new Label("MilliSeconds:");
            final TextField milliPauseVal = JavaFX.textField("500", "The time to wait between keypresses in milliseconds");
            final Button configureRobotButton = JavaFX.button("Configure Robot","Configure Robot To use current typing settings");
            final Button robotTypeButton = JavaFX.button("Robot","Have robot type string into field");
        robotTypeControl.getChildren().addAll(milliPauseLabel, milliPauseVal, configureRobotButton, robotTypeButton);
        robotTypeControl.setSpacing(10);

        final TextArea textArea = new TextArea("");
        textArea.setWrapText(true);

        VBox form = new VBox();
        form.getChildren().addAll(robotTypeControl);

        root.setTop(form);
        root.setCenter(textArea);

        Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
        this.setTitle("Robot Typer");
        this.setScene(scene);
        if(!hidden)
            this.show();


        robotTasker = new RobotTyperTask(robotTypeButton);
        robotTasker.configureRobot(Long.parseLong(milliPauseVal.getText()), textArea.getText());


        // when close stage, stop the robot typing generation
        this.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<javafx.event.Event>() {
            @Override
            public void handle(Event event) {
                robotTasker.stopTheTask();
            }
        });


        configureRobotButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            robotTasker.configureRobot(Long.parseLong(milliPauseVal.getText()), textArea.getText());
                        }
                        catch(NumberFormatException ex){
                            alertLengthNotNumeric();
                        }catch(Exception ex){
                            JavaFX.alertErrorDialogWithException(ex);
                        }
                    }
                });

    }


    private void alertLengthNotNumeric() {
        JavaFX.showSimpleErrorAlert("Typing Pause Time is not numeric","Pause time needs to be an integer representing a number of milliseconds");
    }


    public static void stopServices() {
        if(robotTasker!=null){
            robotTasker.stopTheTask();
        }
    }

}
