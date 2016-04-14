
package com.kodz.unjenkins.server.dto.gimpy;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "plan"
})
public class TestPlan {

    @JsonProperty("plan")
    private Plan plan;

    /**
     * 
     * @return
     *     The plan
     */
    @JsonProperty("plan")
    public Plan getPlan() {
        return plan;
    }

    /**
     * 
     * @param plan
     *     The plan
     */
    @JsonProperty("plan")
    public void setPlan(Plan plan) {
        this.plan = plan;
    }

}
