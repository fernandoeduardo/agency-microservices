package br.com.microservices.order.manager.api.response;

public class CheckHealthResponse {

    private String status;

    public CheckHealthResponse() {
    }

    public CheckHealthResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
