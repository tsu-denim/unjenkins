package com.kodz.unjenkins.server.dto.gimpy;

/**
 * Created by Kurt on 4/14/16.
 */
public class GimpyQueryResponse {
    private String url;
    private String requestId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
