package com.custommacro.custommacro.scheduledMacro.domain;

public class KeyMacro extends ScheduledMacro {
    private int keyCode;

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
