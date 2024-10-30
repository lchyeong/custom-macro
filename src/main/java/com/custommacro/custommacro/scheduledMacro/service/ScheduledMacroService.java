package com.custommacro.custommacro.scheduledMacro.service;

import com.custommacro.custommacro.scheduledMacro.common.Macro;
import com.custommacro.custommacro.scheduledMacro.exception.CustomException;
import com.custommacro.custommacro.scheduledMacro.exception.ErrorMessage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ScheduledMacroService implements Macro {
    protected ScheduledExecutorService scheduler;
    protected final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Override
    public synchronized void startMacro() {
        if (isRunning.get()) {
            throw new CustomException(ErrorMessage.MACRO_ALREADY_RUNNING);
        }

        isRunning.set(true);

        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newScheduledThreadPool(1);
        }

        try {
            execute();
        } catch (Exception e) {
            isRunning.set(false); // 실행 중 예외 발생 시 상태 재설정
            throw new CustomException(ErrorMessage.MACRO_EXECUTION_FAILED, e);
        }
    }

    @Override
    public synchronized void stopMacro() {
        //동기 비동기 처리...
        if (!isRunning.compareAndSet(true, false)) {
            System.err.println("Macro is not currently running.");
            return;
        }

        if (!isRunning.get()) {
            throw new CustomException(ErrorMessage.MACRO_NOT_RUNNING);
        }

        isRunning.set(false); // 중단 신호 설정

        try {
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdownNow();
            }
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.MACRO_STOP_FAILED, e);
        } finally {
            scheduler = null;
        }
    }


    @Override
    public boolean isRunning() {
        return isRunning.get();
    }

    protected abstract void execute();
}
