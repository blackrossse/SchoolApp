package com.example.school.javacode;

public class Item {

    private String selectedItem;
    private String text;

    public Item() {

    }

    public Item(String selectedItem, String text) {
        this.selectedItem = selectedItem;
        this.text = text;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
