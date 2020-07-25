package com.android.custom_dialog;

public class MultiChoiceModel {

    String text;
    int color;

    public MultiChoiceModel(String text, int color) {
        this.text = text;
        this.color = color;
    }
    public MultiChoiceModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
