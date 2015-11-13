package com.example.root.academy;

/**
 * Created by root on 10/11/15.
 */
public class Workshop {
    int id;
    String title;
    int min;
    int max;
    String startDate;
    String student;

    public Workshop(String title, int min, int max, String startDate) {
        this.id = id;
        this.title = title;
        this.min = min;
        this.max = max;
        this.startDate = startDate;
        this.student = student;
    }

    public Workshop() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
