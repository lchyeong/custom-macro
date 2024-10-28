package com.custommacro.custommacro.scheduledMacro.service;

import com.custommacro.custommacro.global.exception.CustomException;
import com.custommacro.custommacro.global.exception.ErrorMessage;
import com.custommacro.custommacro.scheduledMacro.domain.CaptureMacro;
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

    public CaptureMacroService() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new CustomException(ErrorMessage.ROBOT_INIT_FAILURE);
        }
    }

    public void startMacro(int x, int y, int width, int height, int interval) {
        this.captureMacro = new CaptureMacro();
        this.captureMacro.setCaptureArea(x, y, width, height);
        this.captureMacro.setInterval(interval);
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
            if (running && captureMacro != null) {
                captureScreen();
            }
        }, 0, captureMacro.getInterval(), TimeUnit.MILLISECONDS);
    }

    private void captureScreen() {
        try {
            Rectangle captureArea = new Rectangle(captureMacro.getX(), captureMacro.getY(), captureMacro.getWidth(),
                    captureMacro.getHeight());
            BufferedImage screenCapture = robot.createScreenCapture(captureArea);

            File outputfile = new File("capture_" + System.currentTimeMillis() + ".png");
            ImageIO.write(screenCapture, "png", outputfile);

            System.out.println("Captured screen area: " + captureArea);
        } catch (IOException e) {
            throw new CustomException(ErrorMessage.CAPTURE_SAVE_FAILURE, e);
        }
    }
}
