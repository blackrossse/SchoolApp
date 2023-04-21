package com.example.school.javacode;

public class News {

    private String userName;
    private String timeAndDate;
    private String text;

    public News() {

    }

    public News(String userName, String timeAndDate, String text) {
        this.userName = userName;
        this.timeAndDate = timeAndDate;
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
