package com.custommacro.custommacro.scheduledMacro.service;

import com.custommacro.custommacro.global.commonInterface.MacroTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class ScheduledMacroService implements MacroTask {
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    protected volatile boolean running = false;

    @Override
    public void startMacro() {
        if (!running) {
            running = true;
            execute();
        }
    }

    @Override
    public void stopMacro() {
        if (running) {
            scheduler.shutdownNow();
            running = false;
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    protected abstract void execute();
}
