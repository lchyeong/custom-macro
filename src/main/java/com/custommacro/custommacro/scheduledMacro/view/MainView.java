package com.custommacro.custommacro.scheduledMacro.view;

import com.custommacro.custommacro.scheduledMacro.controller.repeatedController.ScheduledMacroController;
import com.custommacro.custommacro.scheduledMacro.service.repeatedMacros.CaptureMacroService;
import com.custommacro.custommacro.scheduledMacro.service.repeatedMacros.KeyMacroService;
import com.custommacro.custommacro.scheduledMacro.view.repeatedView.ScheduledMacroView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Application {
    private final ScheduledMacroController controller = new ScheduledMacroController(new KeyMacroService(),
            new CaptureMacroService());
    private final ScheduledMacroView scheduledMacroView = new ScheduledMacroView();

    private TabPane tabPane;
    private Button startButton;
    private Button stopButton;

    @Override
    public void start(Stage primaryStage) {
        // TabPane 및 탭 구성
        tabPane = new TabPane();

        // Schedule Macro 탭 추가
        Tab scheduleMacroTab = new Tab("Schedule Macro");
        scheduleMacroTab.setContent(scheduledMacroView);
        scheduleMacroTab.setClosable(false);

        // Video Recording Macro 탭 추가 (예시)
        Tab videoRecordingTab = new Tab("Video Recording Macro");
        videoRecordingTab.setContent(new Label("To be implemented"));
        videoRecordingTab.setClosable(false);

        tabPane.getTabs().addAll(scheduleMacroTab, videoRecordingTab);

        // 시작 및 중지 버튼
        startButton = new Button("Start Macro");
        stopButton = new Button("Stop Macro");

        // 버튼 액션 설정
        startButton.setOnAction(e -> startMacro());
        stopButton.setOnAction(e -> stopMacro());

        // 버튼 초기 상태 설정
        updateButtonState();

        // 버튼 레이아웃 구성
        HBox controlButtons = new HBox(10, startButton, stopButton);
        controlButtons.setPadding(new Insets(10, 0, 0, 0));

        // 메인 레이아웃 설정
        VBox mainLayout = new VBox(10, tabPane, controlButtons);
        mainLayout.setPadding(new Insets(15));

        // Scene 설정
        Scene scene = new Scene(mainLayout, 600, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Macro Scheduler");
        primaryStage.show();
    }

    private synchronized void startMacro() {
        // 먼저 버튼 상태를 설정하여 startButton을 비활성화하고 stopButton을 활성화합니다.
        startButton.setDisable(true);
        stopButton.setDisable(false);

        Platform.runLater(() -> {
            if (scheduledMacroView.isKeyMacroEnabled()) {
                int keyCode = scheduledMacroView.getKeyCode();
                int interval = scheduledMacroView.getKeyInterval();
                controller.startKeyMacro(keyCode, interval);
            }

            if (scheduledMacroView.isCaptureMacroEnabled()) {
                String saveDirectory = scheduledMacroView.getSaveDirectory();

                int x = scheduledMacroView.getCaptureX();
                int y = scheduledMacroView.getCaptureY();
                int width = scheduledMacroView.getCaptureWidth();
                int height = scheduledMacroView.getCaptureHeight();
                int interval = scheduledMacroView.getCaptureInterval();

                controller.startCaptureMacro(x, y, width, height, interval, saveDirectory);
            }

            // UI 업데이트가 완료된 후 버튼 상태를 다시 업데이트
            updateButtonState();
            tabPane.requestFocus();
        });
    }

    private synchronized void stopMacro() {
        Platform.runLater(() -> {
            try {
                controller.stopKeyMacro();
                controller.stopCaptureMacro();
            } catch (Exception e) {
                System.err.println("Error stopping macro: " + e.getMessage());
            }
            updateButtonState();
        });
    }

    private void updateButtonState() {
        // 각 매크로가 실행 중인지 확인
        boolean isKeyMacroRunning = controller.isKeyMacroRunning();
        boolean isCaptureMacroRunning = controller.isCaptureMacroRunning();

        // 실행 중인 매크로가 하나라도 있으면 시작 버튼을 비활성화하고 중지 버튼을 활성화
        startButton.setDisable(isKeyMacroRunning || isCaptureMacroRunning);
        stopButton.setDisable(!isKeyMacroRunning && !isCaptureMacroRunning);
    }
}
