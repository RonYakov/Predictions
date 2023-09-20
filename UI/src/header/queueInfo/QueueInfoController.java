package header.queueInfo;

import ex2DTO.QueueInfoDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import manager.PredictionManager;
import managerFX.animation.ColorChangeAnimation;


public class QueueInfoController {

    @FXML
    private Label simDone;
    @FXML
    private Label simInQueue;
    @FXML
    private Label simRunning;
    @FXML
    private Label simDoneCounter;
    @FXML
    private Label simRunningCounter;
    @FXML
    private Label simInQueueCounter;
    private Thread thread;
    private PredictionManager predictionManager;

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;

        manageInfo();
    }

    public ColorChangeAnimation getColorAnimation() {
        return new ColorChangeAnimation(simInQueueCounter, simInQueue, simRunningCounter, simRunning, simDoneCounter, simDone);
    }

    private void manageInfo(){
        thread = new Thread(() -> {
            while (true) {
                try {
                    QueueInfoDTO queueInfo= predictionManager.getQueueInfo();

                    Platform.runLater(() -> {
                        simDone.setText(queueInfo.getSimDone().toString());
                        simInQueue.setText(queueInfo.getSimInQueue().toString());
                        simRunning.setText(queueInfo.getSimRunning().toString());
                    });
                    Thread.sleep(200);

                } catch (InterruptedException ignore) {
                }
            }
        });

        thread.start();
    }

    public Label getSimDone() {
        return simDone;
    }

    public Label getSimInQueue() {
        return simInQueue;
    }

    public Label getSimRunning() {
        return simRunning;
    }

    public Label getSimDoneCounter() {
        return simDoneCounter;
    }

    public Label getSimRunningCounter() {
        return simRunningCounter;
    }

    public Label getSimInQueueCounter() {
        return simInQueueCounter;
    }
}