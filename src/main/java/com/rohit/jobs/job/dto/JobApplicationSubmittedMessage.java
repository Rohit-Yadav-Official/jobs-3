package com.rohit.jobs.job.dto;

public class JobApplicationSubmittedMessage {
    private String email;
    private String body;

    public JobApplicationSubmittedMessage() {
    }

    public JobApplicationSubmittedMessage(String email, String body) {
        this.email = email;
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
