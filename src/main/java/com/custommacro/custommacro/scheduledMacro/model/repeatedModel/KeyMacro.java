package com.custommacro.custommacro.scheduledMacro.model.repeatedModel;

import com.custommacro.custommacro.scheduledMacro.model.ScheduledMacro;

public class KeyMacro extends ScheduledMacro {
    private int keyCode;

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
