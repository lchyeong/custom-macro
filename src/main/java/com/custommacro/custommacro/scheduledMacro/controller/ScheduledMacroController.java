package com.custommacro.custommacro.scheduledMacro.controller;

import com.custommacro.custommacro.scheduledMacro.service.CaptureMacroService;
import com.custommacro.custommacro.scheduledMacro.service.KeyMacroService;

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

    public void startCaptureMacro(int x, int y, int width, int height, int interval) {
        captureMacroService.startMacro(x, y, width, height, interval);
    }

    public void stopCaptureMacro() {
        captureMacroService.stopMacro();
    }

    public boolean isCaptureMacroRunning() {
        return captureMacroService.isRunning();
    }
}
