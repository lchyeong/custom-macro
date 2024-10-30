package com.custommacro.custommacro.scheduledMacro.view.repeatedView;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScheduledMacroView extends VBox {

    private final CheckBox keyMacroCheckBox = new CheckBox("Enable Key Repetition");

    private final TextField keyCodeField = new TextField();
    private final TextField keyIntervalField = new TextField();

    private final CaptureMacroView captureMacroView; // 캡처 기능을 분리한 클래스 인스턴스
    private int lastKeyCode; // 마지막으로 입력된 키의 정수 코드 저장

    public ScheduledMacroView() {
        this.captureMacroView = new CaptureMacroView(); // 캡처 기능 인스턴스 생성
        setupView();
    }

    private void setupView() {
        setSpacing(20);
        setPadding(new Insets(15));

        // Key Repetition 설정 UI
        VBox keyMacroSection = createKeyMacroSection();

        // 전체 레이아웃에 추가
        getChildren().addAll(keyMacroSection, captureMacroView); // 캡처 기능 추가
    }

    private VBox createKeyMacroSection() {
        keyCodeField.setPromptText("Press a key");
        keyIntervalField.setPromptText("Interval (ms)");

        // TextField의 기본 입력 기능 비활성화 및 이벤트 필터 추가
        keyCodeField.setEditable(false); // 사용자가 텍스트 필드에 직접 입력하는 것을 막음
        keyCodeField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);

        VBox keyMacroSection = new VBox(10);
        keyMacroSection.setPadding(new Insets(10));
        keyMacroSection.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10;");

        keyMacroSection.getChildren().addAll(
                new Label("Key Repetition Settings"),
                keyMacroCheckBox,
                new HBox(10, new Label("Key Code:"), keyCodeField),
                new HBox(10, new Label("Interval (ms):"), keyIntervalField)
        );
        return keyMacroSection;
    }

    // 키 입력 이벤트 핸들러
    private void handleKeyPress(KeyEvent event) {
        String actualInput = event.getText(); // 사용자가 실제로 입력한 값 (일반 문자)
        String keyName = getKeyName(event, actualInput);

        // keyCodeField에 실제 입력된 값과 키 이름을 모두 표시
        keyCodeField.setText((actualInput.isEmpty() ? keyName : actualInput) + " (" + keyName + ")");

        // 키 코드의 정수 값 저장
        lastKeyCode = event.getCode().getCode();

        // 기본 TextField 입력 동작을 방지하여 중복 입력 해결
        event.consume();
    }

    private static String getKeyName(KeyEvent event, String actualInput) {
        String keyName;

        if (actualInput.isEmpty()) {
            // 명령 키 (Space, Command, Control 등)의 경우
            keyName = event.getCode().getName();
        } else if (event.isShiftDown() && actualInput.matches("[a-zA-Z]")) {
            // Shift가 눌린 상태에서 알파벳이 입력된 경우 대문자로 표시
            keyName = actualInput.toUpperCase();
        } else if (event.getCode() == KeyCode.SPACE) {
            keyName = "Space";
        } else {
            // 알파벳 소문자 또는 일반 문자 그대로 표시
            keyName = actualInput;
        }
        return keyName;
    }


    // getter 메서드들
    public boolean isKeyMacroEnabled() {
        return keyMacroCheckBox.isSelected();
    }

    public int getKeyCode() {
        return lastKeyCode; // 키 코드의 정수 값 반환
    }

    public int getKeyInterval() {
        return Integer.parseInt(keyIntervalField.getText());
    }

    public boolean isCaptureMacroEnabled() {
        return captureMacroView.isCaptureMacroEnabled();
    }

    public int getCaptureX() {
        return captureMacroView.getCaptureX();
    }

    public int getCaptureY() {
        return captureMacroView.getCaptureY();
    }

    public int getCaptureWidth() {
        return captureMacroView.getCaptureWidth();
    }

    public int getCaptureHeight() {
        return captureMacroView.getCaptureHeight();
    }

    public int getCaptureInterval() {
        return captureMacroView.getCaptureInterval();
    }

    public String getSaveDirectory() {
        return captureMacroView.getSaveDirectory();
    }
}
