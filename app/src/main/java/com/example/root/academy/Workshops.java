package com.example.root.academy;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 12/11/15.
 */
public class Workshops {

    @SerializedName(value="workshops")
    public Workshop[] workshops;

    public void setWorkshops(Workshop[] workshops) {
        this.workshops = workshops;

    }
}
