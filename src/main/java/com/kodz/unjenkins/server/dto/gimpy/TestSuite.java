
package com.kodz.unjenkins.server.dto.gimpy;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "suite"
})
public class TestSuite {

    @JsonProperty("suite")
    private Suite suite;

    /**
     * 
     * @return
     *     The suite
     */
    @JsonProperty("suite")
    public Suite getSuite() {
        return suite;
    }

    /**
     * 
     * @param suite
     *     The suite
     */
    @JsonProperty("suite")
    public void setSuite(Suite suite) {
        this.suite = suite;
    }

}
