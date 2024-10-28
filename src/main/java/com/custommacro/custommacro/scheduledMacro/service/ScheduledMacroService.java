package com.custommacro.custommacro.scheduledMacro.service;

import com.custommacro.custommacro.global.commonInterface.MacroTask;
import com.custommacro.custommacro.global.exception.CustomException;
import com.custommacro.custommacro.global.exception.ErrorMessage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class ScheduledMacroService implements MacroTask {
    protected final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    protected volatile boolean running = false;

    @Override
    public void startMacro() {
        if (!running) {
            running = true;
            try {
                execute();
            } catch (Exception e) {
                running = false;
                throw new CustomException(ErrorMessage.MACRO_EXECUTION_FAILED, e);
            }
        }
        throw new CustomException(ErrorMessage.MACRO_ALREADY_RUNNING);
    }

    @Override
    public void stopMacro() {
        if (running) {
            try {
                scheduler.shutdownNow();
            } catch (Exception e) {
                throw new CustomException(ErrorMessage.MACRO_STOP_FAILED, e);
            } finally {
                running = false;
            }
        }
        throw new CustomException(ErrorMessage.MACRO_NOT_RUNNING);
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    protected abstract void execute();
}
