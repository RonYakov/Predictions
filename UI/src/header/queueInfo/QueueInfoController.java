package header.queueInfo;

import ex2DTO.QueueInfoDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import manager.PredictionManager;


public class QueueInfoController {

    @FXML
    private Label simDone;
    @FXML
    private Label simInQueue;
    @FXML
    private Label simRunning;
    private Thread thread;
    private PredictionManager predictionManager;

    public void setPredictionManager(PredictionManager predictionManager) {
        this.predictionManager = predictionManager;

        manageInfo();
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
}