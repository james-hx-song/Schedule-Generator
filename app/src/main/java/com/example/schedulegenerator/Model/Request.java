package com.example.schedulegenerator.Model;

public class Request {
    private String requesterID;
    private String requesterName;
    private String projectID;
    private String requestMsg;

    public Request()
    {

    }

    public String getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(String requesterID) {
        this.requesterID = requesterID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public Request(String requesterID, String projectID, String requestMsg, String requesterName) {
        this.requesterID = requesterID;
        this.projectID = projectID;
        this.requestMsg = requestMsg;
        this.requesterName = requesterName;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
}
