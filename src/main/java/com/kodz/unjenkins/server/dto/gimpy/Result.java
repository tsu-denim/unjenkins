
package com.kodz.unjenkins.server.dto.gimpy;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "Priority",
    "Passed",
    "Failed",
    "Automated",
    "NotExecuted"
})
public class Result {

    @JsonProperty("Priority")
    private String Priority;
    @JsonProperty("Passed")
    private Integer Passed;
    @JsonProperty("Failed")
    private Integer Failed;
    @JsonProperty("Automated")
    private Integer Automated;
    @JsonProperty("NotExecuted")
    private Integer NotExecuted;

    /**
     * 
     * @return
     *     The Priority
     */
    @JsonProperty("Priority")
    public String getPriority() {
        return Priority;
    }

    /**
     * 
     * @param Priority
     *     The Priority
     */
    @JsonProperty("Priority")
    public void setPriority(String Priority) {
        this.Priority = Priority;
    }

    /**
     * 
     * @return
     *     The Passed
     */
    @JsonProperty("Passed")
    public Integer getPassed() {
        return Passed;
    }

    /**
     * 
     * @param Passed
     *     The Passed
     */
    @JsonProperty("Passed")
    public void setPassed(Integer Passed) {
        this.Passed = Passed;
    }

    /**
     * 
     * @return
     *     The Failed
     */
    @JsonProperty("Failed")
    public Integer getFailed() {
        return Failed;
    }

    /**
     * 
     * @param Failed
     *     The Failed
     */
    @JsonProperty("Failed")
    public void setFailed(Integer Failed) {
        this.Failed = Failed;
    }

    /**
     * 
     * @return
     *     The Automated
     */
    @JsonProperty("Automated")
    public Integer getAutomated() {
        return Automated;
    }

    /**
     * 
     * @param Automated
     *     The Automated
     */
    @JsonProperty("Automated")
    public void setAutomated(Integer Automated) {
        this.Automated = Automated;
    }

    /**
     * 
     * @return
     *     The NotExecuted
     */
    @JsonProperty("NotExecuted")
    public Integer getNotExecuted() {
        return NotExecuted;
    }

    /**
     * 
     * @param NotExecuted
     *     The NotExecuted
     */
    @JsonProperty("NotExecuted")
    public void setNotExecuted(Integer NotExecuted) {
        this.NotExecuted = NotExecuted;
    }

}
