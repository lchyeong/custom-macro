package com.custommacro.custommacro.scheduledMacro.common;

public interface Macro {
    void startMacro();

    void stopMacro();

    boolean isRunning();
}