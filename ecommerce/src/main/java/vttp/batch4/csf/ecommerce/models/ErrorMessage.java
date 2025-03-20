package vttp.batch4.csf.ecommerce.models;

import java.time.LocalDateTime;

public class ErrorMessage {
    
    private int status; //error status code
    private String message;
    private LocalDateTime timestamp;
    private String endPoint;

    
    public ErrorMessage() {
    }

    public ErrorMessage(int status, String message, LocalDateTime timestamp, String endPoint) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.endPoint = endPoint;
    }
    
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getEndPoint() {
        return endPoint;
    }
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }


    
}
