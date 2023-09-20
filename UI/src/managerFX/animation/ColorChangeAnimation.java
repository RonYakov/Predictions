package managerFX.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ColorChangeAnimation {
    private Label SimInQueue;
    private Label SimInQueueCounter;
    private Label SimRunning;
    private Label SimRunningCounter;
    private Label SimDone;
    private Label SimDoneCounter;
    private Timeline timeline;


    public ColorChangeAnimation(Label simInQueue, Label simInQueueCounter, Label simRunning, Label simRunningCounter, Label simDone, Label simDoneCounter) {
        SimInQueue = simInQueue;
        SimInQueueCounter = simInQueueCounter;
        SimRunning = simRunning;
        SimRunningCounter = simRunningCounter;
        SimDone = simDone;
        SimDoneCounter = simDoneCounter;
        timeline = new Timeline( new KeyFrame(Duration.seconds(1), event -> changeLabelColors()));
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
    }

    // Start the color change animation
    public void start() {
        timeline.play();
    }

    // Stop the color change animation and reset labels to black
    public void stop() {
        timeline.stop();
        resetLabelColors();
    }

    // Helper method to change label colors randomly
    private void changeLabelColors() {
        SimInQueue.setTextFill(randomColor());
        SimInQueueCounter.setTextFill(randomColor());
        SimRunning.setTextFill(randomColor());
        SimRunningCounter.setTextFill(randomColor());
        SimDone.setTextFill(randomColor());
        SimDoneCounter.setTextFill(randomColor());
    }

    // Helper method to reset label colors to black
    private void resetLabelColors() {
        SimInQueue.setTextFill(Color.BLACK);
        SimInQueueCounter.setTextFill(Color.BLACK);
        SimRunning.setTextFill(Color.BLACK);
        SimRunningCounter.setTextFill(Color.BLACK);
        SimDone.setTextFill(Color.BLACK);
        SimDoneCounter.setTextFill(Color.BLACK);
    }

    // Helper method to generate a random color
    private Color randomColor() {
        double red = Math.random();
        double green = Math.random();
        double blue = Math.random();
        return new Color(red, green, blue, 1.0); // Opaque color
    }
}
