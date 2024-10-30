package com.custommacro.custommacro.scheduledMacro.service.repeatedMacros;

import com.custommacro.custommacro.scheduledMacro.exception.CustomException;
import com.custommacro.custommacro.scheduledMacro.exception.ErrorMessage;
import com.custommacro.custommacro.scheduledMacro.model.repeatedModel.CaptureMacro;
import com.custommacro.custommacro.scheduledMacro.service.ScheduledMacroService;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class CaptureMacroService extends ScheduledMacroService {
    private final Robot robot;
    private CaptureMacro captureMacro;
    private String saveDirectory = "";

    public CaptureMacroService() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new CustomException(ErrorMessage.ROBOT_INITIALIZATION_FAILED);
        }
    }

    public void startMacro(int x, int y, int width, int height, int interval, String saveDirectory) {

        this.captureMacro = new CaptureMacro();
        this.captureMacro.setCaptureArea(x, y, width, height);
        this.captureMacro.setInterval(interval);
        this.saveDirectory = saveDirectory;
        System.out.println("간격 정보 : " + interval);
        super.startMacro();
    }

    @Override
    public void stopMacro() {
        super.stopMacro();
        this.captureMacro = null;
    }

    @Override
    protected void execute() {
        scheduler.scheduleAtFixedRate(() -> {
            if (isRunning.get() && captureMacro != null) {
                captureScreen();
            }
        }, 0, captureMacro.getInterval(), TimeUnit.MILLISECONDS);
    }

    private void captureScreen() {
        try {
            // saveDirectory 경로 검증
            File directory = new File(saveDirectory);
            if (!directory.exists() || !directory.isDirectory() || !directory.canWrite()) {
                System.err.println("The specified directory is invalid or not writable: " + saveDirectory);
                return;
            }

            Rectangle captureArea = new Rectangle(captureMacro.getX(), captureMacro.getY(),
                    captureMacro.getWidth(), captureMacro.getHeight());
            BufferedImage screenCapture = robot.createScreenCapture(captureArea);

            // 파일 생성 및 저장
            File outputfile = new File(directory, "Linux_" + System.currentTimeMillis() + ".png");
            if (ImageIO.write(screenCapture, "png", outputfile)) {
                System.out.println(
                        "Captured screen area: " + captureArea + " and saved to: " + outputfile.getAbsolutePath());
            } else {
                System.err.println("Failed to save screenshot. Check file path or permissions.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while saving the screenshot:");
            e.printStackTrace();
            throw new CustomException(ErrorMessage.CAPTURE_SAVE_FAILED, e);
        }
    }
}
