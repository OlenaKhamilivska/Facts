package com.example.numbersapi.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class NumbersPojo {
    @SerializedName("text")
    @Expose
    String text;
    @SerializedName("number")
    @Expose
    int number;
    @SerializedName("found")
    @Expose
    boolean found;
    @SerializedName("type")
    @Expose
    String type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
