package com.example.covid19;

public class FeedBackModel {

    public FeedBackModel() {

    }

    public FeedBackModel(String name, String email, String feedbackMessage, String feedbackType) {
        this.name = name;
        this.email = email;
        this.feedbackMessage = feedbackMessage;
        this.feedbackType = feedbackType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    String name;
    String email;
    String feedbackMessage;
    String feedbackType;
}
