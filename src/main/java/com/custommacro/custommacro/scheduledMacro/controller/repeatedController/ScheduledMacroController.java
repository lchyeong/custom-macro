package com.custommacro.custommacro.scheduledMacro.controller.repeatedController;

import com.custommacro.custommacro.scheduledMacro.service.repeatedMacros.CaptureMacroService;
import com.custommacro.custommacro.scheduledMacro.service.repeatedMacros.KeyMacroService;

public class ScheduledMacroController {

    private final KeyMacroService keyMacroService;
    private final CaptureMacroService captureMacroService;

    public ScheduledMacroController(KeyMacroService keyMacroService, CaptureMacroService captureMacroService) {
        this.keyMacroService = keyMacroService;
        this.captureMacroService = captureMacroService;
    }

    public void startKeyMacro(int keyCode, int interval) {
        keyMacroService.startMacro(keyCode, interval);
    }

    public void stopKeyMacro() {
        keyMacroService.stopMacro();
    }

    public boolean isKeyMacroRunning() {
        return keyMacroService.isRunning();
    }

    public void startCaptureMacro(int x, int y, int width, int height, int interval, String saveDirectory) {
        captureMacroService.startMacro(x, y, width, height, interval, saveDirectory);
    }

    public void stopCaptureMacro() {
        captureMacroService.stopMacro();
    }

    public boolean isCaptureMacroRunning() {
        return captureMacroService.isRunning();
    }
}
