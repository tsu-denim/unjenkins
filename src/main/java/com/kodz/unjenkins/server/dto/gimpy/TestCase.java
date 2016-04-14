
package com.kodz.unjenkins.server.dto.gimpy;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "case"
})
public class TestCase {

    @JsonProperty("case")
    private Case _case;

    /**
     * 
     * @return
     *     The _case
     */
    @JsonProperty("case")
    public Case getCase() {
        return _case;
    }

    /**
     * 
     * @param _case
     *     The case
     */
    @JsonProperty("case")
    public void setCase(Case _case) {
        this._case = _case;
    }

}
