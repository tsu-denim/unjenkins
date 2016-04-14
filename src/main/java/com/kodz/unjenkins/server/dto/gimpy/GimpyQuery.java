
package com.kodz.unjenkins.server.dto.gimpy;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "testCaseQuery"
})
public class GimpyQuery {
    @JsonIgnore
    public String reportId = UUID.randomUUID().toString();
    private TestCaseQuery testCaseQuery;

    /**
     * 
     * @return
     *     The testCaseQuery
     */
    @JsonProperty("testCaseQuery")
    public TestCaseQuery getTestCaseQuery() {
        return testCaseQuery;
    }

    /**
     * 
     * @param testCaseQuery
     *     The testCaseQuery
     */
    @JsonProperty("testCaseQuery")
    public void setTestCaseQuery(TestCaseQuery testCaseQuery) {
        this.testCaseQuery = testCaseQuery;
    }

}
