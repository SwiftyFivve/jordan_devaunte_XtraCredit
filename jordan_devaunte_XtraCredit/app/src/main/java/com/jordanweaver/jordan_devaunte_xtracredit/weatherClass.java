package com.jordanweaver.jordan_devaunte_xtracredit;

/**
 * Created by devaunteledee on 2/23/15.
 */
public class weatherClass {
    String High;
    String Low;
    String time;
    String condition;

    public weatherClass(String high, String low, String time, String condition) {
        High = high;
        Low = low;
        this.time = time;
        this.condition = condition;
    }

    public String getHigh() {
        return High + "/" + getLow();
    }

    public void setHigh(String high) {
        High = high;
    }

    public String getLow() {
        return Low;
    }

    public void setLow(String low) {
        Low = low;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
