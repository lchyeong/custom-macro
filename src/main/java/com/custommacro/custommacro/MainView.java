package com.custommacro.custommacro;

import com.custommacro.custommacro.scheduledMacro.controller.ScheduledMacroController;
import com.custommacro.custommacro.scheduledMacro.service.CaptureMacroService;
import com.custommacro.custommacro.scheduledMacro.service.KeyMacroService;
import com.custommacro.custommacro.scheduledMacro.view.ScheduledMacroView;
import javafx.application.Application;
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
    private final ScheduledMacroView scheduledMacroView = new ScheduledMacroView(controller);

    @Override
    public void start(Stage primaryStage) {
        // TabPane 및 탭 구성
        TabPane tabPane = new TabPane();

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
        Button startButton = new Button("Start Macro");
        Button stopButton = new Button("Stop Macro");

        startButton.setOnAction(e -> startMacro());
        stopButton.setOnAction(e -> stopMacro());

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

    private void startMacro() {
        if (scheduledMacroView.isKeyMacroEnabled()) {
            int keyCode = scheduledMacroView.getKeyCode();
            int interval = scheduledMacroView.getKeyInterval();
            controller.startKeyMacro(keyCode, interval);
        }

        if (scheduledMacroView.isCaptureMacroEnabled()) {
            int x = scheduledMacroView.getCaptureX();
            int y = scheduledMacroView.getCaptureY();
            int width = scheduledMacroView.getCaptureWidth();
            int height = scheduledMacroView.getCaptureHeight();
            int interval = scheduledMacroView.getCaptureInterval();
            controller.startCaptureMacro(x, y, width, height, interval);
        }

        //if로 기타 매크로들도 추가 가능
    }

    private void stopMacro() {
        controller.stopKeyMacro();
        controller.stopCaptureMacro();
    }
}
