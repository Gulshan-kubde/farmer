package cropulse.io.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
@Data
public class ErrorDetails {
    private HttpStatus status;
    private String message;
    private String details;

    // Constructor
    public ErrorDetails(HttpStatus status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }

    // Getters and setters
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
