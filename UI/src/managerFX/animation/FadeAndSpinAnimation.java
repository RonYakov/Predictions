package managerFX.animation;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class FadeAndSpinAnimation {
    private Button details;
    private Button newExecution;
    private Button result;
    private Button ok;
    private RotateTransition rotateTransitionDetails;
    private RotateTransition rotateTransitionOk;
    private RotateTransition rotateTransitionNewExecution;
    private RotateTransition rotateTransitionResult;
    private FadeTransition fadeTransitionDetails;
    private FadeTransition fadeTransitionOk;
    private FadeTransition fadeTransitionNewExecution;
    private FadeTransition fadeTransitionResult;

    public FadeAndSpinAnimation(Button details, Button newExecution, Button result, Button ok) {
        this.details = details;
        this.newExecution = newExecution;
        this.result = result;
        this.ok = ok;

        // Initialize rotate and fade transitions for each button
        rotateTransitionDetails = new RotateTransition(Duration.seconds(3), details);
        rotateTransitionNewExecution = new RotateTransition(Duration.seconds(3), newExecution);
        rotateTransitionResult = new RotateTransition(Duration.seconds(3), result);
        rotateTransitionOk = new RotateTransition(Duration.seconds(3), ok);

        fadeTransitionDetails = createRandomizedFadeTransition(details);
        fadeTransitionNewExecution = createRandomizedFadeTransition(newExecution);
        fadeTransitionResult = createRandomizedFadeTransition(result);
        fadeTransitionOk = createRandomizedFadeTransition(ok);
    }

    // Start the spin and fade animations for all buttons
    public void start() {
        startAnimation(rotateTransitionDetails, fadeTransitionDetails);
        startAnimation(rotateTransitionNewExecution, fadeTransitionNewExecution);
        startAnimation(rotateTransitionResult, fadeTransitionResult);
        startAnimation(rotateTransitionOk, fadeTransitionOk);
    }

    // Stop the spin and fade animations and reset the buttons
    public void stop() {
        stopAnimation(rotateTransitionDetails, fadeTransitionDetails, details);
        stopAnimation(rotateTransitionNewExecution, fadeTransitionNewExecution, newExecution);
        stopAnimation(rotateTransitionResult, fadeTransitionResult, result);
        stopAnimation(rotateTransitionOk, fadeTransitionOk, ok);
    }

    // Helper method to create a randomized fade transition
    private FadeTransition createRandomizedFadeTransition(Button button) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), button);
        fadeTransition.setFromValue(Math.random()); // Random starting opacity
        fadeTransition.setToValue(Math.random());   // Random ending opacity
        return fadeTransition;
    }

    // Helper method to start animations for a button
    private void startAnimation(RotateTransition rotateTransition, FadeTransition fadeTransition) {
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);

        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.setAutoReverse(true); // Smooth fade in and out

        rotateTransition.play();
        fadeTransition.play();
    }

    // Helper method to stop animations for a button and reset it
    private void stopAnimation(RotateTransition rotateTransition, FadeTransition fadeTransition, Button button) {
        rotateTransition.stop();
        rotateTransition.setByAngle(0);

        fadeTransition.stop();

        button.setRotate(0);
        button.setOpacity(1.0);
    }
}