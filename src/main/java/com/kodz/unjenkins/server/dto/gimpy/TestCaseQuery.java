
package com.kodz.unjenkins.server.dto.gimpy;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "endpoint",
    "testPlans",
    "testSuites",
    "testCases"
})
public class TestCaseQuery {

    @JsonProperty("endpoint")
    private String endpoint;
    @JsonProperty("testPlans")
    private List<TestPlan> testPlans = new ArrayList<TestPlan>();
    @JsonProperty("testSuites")
    private List<TestSuite> testSuites = new ArrayList<TestSuite>();
    @JsonProperty("testCases")
    private List<TestCase> testCases = new ArrayList<TestCase>();

    /**
     * 
     * @return
     *     The endpoint
     */
    @JsonProperty("endpoint")
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * 
     * @param endpoint
     *     The endpoint
     */
    @JsonProperty("endpoint")
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 
     * @return
     *     The testPlans
     */
    @JsonProperty("testPlans")
    public List<TestPlan> getTestPlans() {
        return testPlans;
    }

    /**
     * 
     * @param testPlans
     *     The testPlans
     */
    @JsonProperty("testPlans")
    public void setTestPlans(List<TestPlan> testPlans) {
        this.testPlans = testPlans;
    }

    /**
     * 
     * @return
     *     The testSuites
     */
    @JsonProperty("testSuites")
    public List<TestSuite> getTestSuites() {
        return testSuites;
    }

    /**
     * 
     * @param testSuites
     *     The testSuites
     */
    @JsonProperty("testSuites")
    public void setTestSuites(List<TestSuite> testSuites) {
        this.testSuites = testSuites;
    }

    /**
     * 
     * @return
     *     The testCases
     */
    @JsonProperty("testCases")
    public List<TestCase> getTestCases() {
        return testCases;
    }

    /**
     * 
     * @param testCases
     *     The testCases
     */
    @JsonProperty("testCases")
    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

}
