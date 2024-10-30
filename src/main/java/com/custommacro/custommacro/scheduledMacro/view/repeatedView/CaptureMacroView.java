package com.custommacro.custommacro.scheduledMacro.view.repeatedView;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.swing.JWindow;

public class CaptureMacroView extends VBox {
    // UI components
    private final TextField filePathField = new TextField();
    private final CheckBox captureMacroCheckBox = new CheckBox("Enable Capture Repetition");
    private final TextField captureXField = new TextField();
    private final TextField captureYField = new TextField();
    private final TextField captureWidthField = new TextField();
    private final TextField captureHeightField = new TextField();
    private final TextField captureIntervalField = new TextField();
    private final ComboBox<String> displayComboBox = new ComboBox<>();
    private final Button captureButton = new Button("Capture Area");

    private final Rectangle selectionRectangle = new Rectangle();
    private int startX, startY;

    public CaptureMacroView() {
        setupView();
        populateDisplays();
    }

    // Set up the main UI components
    private void setupView() {
        setSpacing(10);
        setPadding(new Insets(10));
        setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10;");

        // Main label and display selection
        Label captureLabel = new Label("Capture Repetition Settings");
        captureLabel.setStyle("-fx-font-weight: bold");

        // Save path selection
        filePathField.setPromptText("No path selected");
        filePathField.setEditable(false);
        Button selectPathButton = new Button("Select Save Path");
        selectPathButton.setOnAction(e -> selectSaveDirectory());
        HBox pathSelectionBox = new HBox(10, selectPathButton, filePathField);

        // Adding components to layout
        getChildren().addAll(
                captureLabel,
                captureMacroCheckBox,
                new HBox(10, new Label("Select Display:"), displayComboBox),
                createCaptureGrid(),
                pathSelectionBox,
                new HBox(10, new Label("Interval (ms):"), captureIntervalField),
                captureButton
        );

        captureButton.setOnAction(e -> initiateAreaSelection());
    }

    // Populate available displays in ComboBox
    private void populateDisplays() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        for (int i = 0; i < devices.length; i++) {
            Rectangle bounds = devices[i].getDefaultConfiguration().getBounds();
            displayComboBox.getItems().add("Display " + (i + 1) + " (" + bounds.width + "x" + bounds.height + ")");
        }
        displayComboBox.getSelectionModel().selectFirst();
        displayComboBox.getSelectionModel().selectedIndexProperty()
                .addListener((obs, oldVal, newVal) -> updateSelectedDisplayBounds());
    }

    // Update selectionRectangle to match selected display bounds
    private void updateSelectedDisplayBounds() {
        int selectedIndex = displayComboBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[selectedIndex];
            selectionRectangle.setBounds(device.getDefaultConfiguration().getBounds());
            updateFieldsWithSelection();
        }
    }

    // Start area selection overlay
    private void initiateAreaSelection() {
        int selectedIndex = displayComboBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            System.out.println("Please select a display first.");
            return;
        }

        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        Rectangle selectedBounds = devices[selectedIndex].getDefaultConfiguration().getBounds();

        JWindow overlay = createOverlayWindow(selectedBounds);
        overlay.setVisible(true);
    }

    // Overlay window for selecting capture area
    private JWindow createOverlayWindow(Rectangle selectedBounds) {
        JWindow overlay = new JWindow();
        overlay.setOpacity(0.3f);
        overlay.setBackground(Color.BLACK);
        overlay.setBounds(selectedBounds);
        overlay.setAlwaysOnTop(true);

        overlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getXOnScreen();
                startY = e.getYOnScreen();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int endX = e.getXOnScreen();
                int endY = e.getYOnScreen();
                selectionRectangle.setBounds(
                        Math.min(startX, endX) - selectedBounds.x,
                        Math.min(startY, endY) - selectedBounds.y,
                        Math.abs(endX - startX),
                        Math.abs(endY - startY)
                );
                updateFieldsWithSelection();
                overlay.dispose();
            }
        });

        overlay.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int currentX = e.getXOnScreen();
                int currentY = e.getYOnScreen();
                selectionRectangle.setBounds(
                        Math.min(startX, currentX) - selectedBounds.x,
                        Math.min(startY, currentY) - selectedBounds.y,
                        Math.abs(currentX - startX),
                        Math.abs(currentY - startY)
                );
                overlay.repaint();
            }
        });
        return overlay;
    }

    // Update capture settings fields with selected rectangle dimensions
    private void updateFieldsWithSelection() {
        captureXField.setText(String.valueOf(selectionRectangle.x));
        captureYField.setText(String.valueOf(selectionRectangle.y));
        captureWidthField.setText(String.valueOf(selectionRectangle.width));
        captureHeightField.setText(String.valueOf(selectionRectangle.height));
    }

    // Select save directory
    private void selectSaveDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Screenshot Save Directory");
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            filePathField.setText(selectedDirectory.getAbsolutePath());
        } else {
            filePathField.setText("No directory selected");
        }
    }

    // Set up the capture area input fields grid
    private GridPane createCaptureGrid() {
        GridPane captureGrid = new GridPane();
        captureGrid.setHgap(10);
        captureGrid.setVgap(10);

        captureGrid.add(new Label("Capture X:"), 0, 0);
        captureGrid.add(captureXField, 1, 0);
        captureGrid.add(new Label("Capture Y:"), 0, 1);
        captureGrid.add(captureYField, 1, 1);
        captureGrid.add(new Label("Width:"), 2, 0);
        captureGrid.add(captureWidthField, 3, 0);
        captureGrid.add(new Label("Height:"), 2, 1);
        captureGrid.add(captureHeightField, 3, 1);

        return captureGrid;
    }

    // Utility methods to access capture settings
    public boolean isCaptureMacroEnabled() {
        return captureMacroCheckBox.isSelected();
    }

    public int getCaptureX() {
        return Integer.parseInt(captureXField.getText());
    }

    public int getCaptureY() {
        return Integer.parseInt(captureYField.getText());
    }

    public int getCaptureWidth() {
        return Integer.parseInt(captureWidthField.getText());
    }

    public int getCaptureHeight() {
        return Integer.parseInt(captureHeightField.getText());
    }

    public int getCaptureInterval() {
        return Integer.parseInt(captureIntervalField.getText());
    }

    public String getSaveDirectory() {
        return filePathField.getText();
    }
}
