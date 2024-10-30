package com.custommacro.custommacro.scheduledMacro.model;

import com.custommacro.custommacro.scheduledMacro.exception.CustomException;
import com.custommacro.custommacro.scheduledMacro.exception.ErrorMessage;

public abstract class ScheduledMacro {
    protected int interval;

    public void setInterval(int interval) {
        if (interval <= 0) {
            throw new CustomException(ErrorMessage.INVALID_INTERVAL);
        }
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }
}
