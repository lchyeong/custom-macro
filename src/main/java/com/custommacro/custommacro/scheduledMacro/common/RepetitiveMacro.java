package com.custommacro.custommacro.scheduledMacro.common;

public interface RepetitiveMacro extends Macro {
    void setInterval(int interval);

    int getInterval();

//    void setRepetitions(int count);
//    void getRepetitions();
}
