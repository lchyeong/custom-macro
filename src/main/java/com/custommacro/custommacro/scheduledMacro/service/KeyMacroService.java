package com.custommacro.custommacro.scheduledMacro.service;

import com.custommacro.custommacro.global.exception.CustomException;
import com.custommacro.custommacro.global.exception.ErrorMessage;
import com.custommacro.custommacro.scheduledMacro.domain.KeyMacro;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.TimeUnit;

public class KeyMacroService extends ScheduledMacroService {
    private final Robot robot;
    private KeyMacro keyMacro;

    public KeyMacroService() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new CustomException(ErrorMessage.ROBOT_INIT_FAILURE);
        }
    }

    public void startMacro(int keyCode, int interval) {
        this.keyMacro = new KeyMacro();
        this.keyMacro.setKeyCode(keyCode);
        this.keyMacro.setInterval(interval);
        super.startMacro();
    }

    @Override
    public void stopMacro() {
        super.stopMacro();
        this.keyMacro = null;
    }

    @Override
    protected void execute() {
        scheduler.scheduleAtFixedRate(() -> {
            if (running && keyMacro != null) {
                performKeyPress(keyMacro.getKeyCode());
            }
        }, 0, keyMacro.getInterval(), TimeUnit.MILLISECONDS);
    }


    protected void performKeyPress(int keyCode) {
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
    }
}
