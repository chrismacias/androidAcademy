package com.example.root.academy;

/**
 * Created by root on 10/11/15.
 */
public class Workshop {
    String id = null;
    String title;
    int min;
    int max;
    String startDate;

    public Workshop(String title, int min, int max, String startDate,int id) {
        this.id = Integer.toString(id);
        this.title = title;
        this.min = min;
        this.max = max;
        this.startDate = startDate;
    }

    public Workshop(String title, int min, int max, String startDate) {
        this.title = title;
        this.min = min;
        this.max = max;
        this.startDate = startDate;
    }

    public Workshop() {

    }

    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = Integer.toString(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
