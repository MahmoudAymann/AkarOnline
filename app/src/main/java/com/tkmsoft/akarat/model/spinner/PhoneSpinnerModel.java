package com.tkmsoft.akarat.model.spinner;

public class PhoneSpinnerModel {
    private String text;
    private int image;

    public PhoneSpinnerModel(String text, int image) {
        this.text = text;
        this.image = image;
    }

    public PhoneSpinnerModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
