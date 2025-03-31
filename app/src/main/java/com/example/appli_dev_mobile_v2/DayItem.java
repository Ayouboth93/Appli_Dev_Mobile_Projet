package com.example.appli_dev_mobile_v2;

public class DayItem {
    private String date;
    private int color;

    public DayItem(String date, int color) {
        this.date = date;
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
