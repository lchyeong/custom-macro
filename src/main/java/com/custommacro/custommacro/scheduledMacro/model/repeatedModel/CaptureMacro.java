package com.custommacro.custommacro.scheduledMacro.model.repeatedModel;

import com.custommacro.custommacro.scheduledMacro.model.ScheduledMacro;

public class CaptureMacro extends ScheduledMacro {

    private int x;
    private int y;
    private int width;
    private int height;

    public void setCaptureArea(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
