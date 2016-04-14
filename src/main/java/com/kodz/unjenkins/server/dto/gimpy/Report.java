
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
    "Results"
})
public class Report {

    @JsonProperty("Results")
    private List<Result> Results = new ArrayList<Result>();

    /**
     * 
     * @return
     *     The Results
     */
    @JsonProperty("Results")
    public List<Result> getResults() {
        return Results;
    }

    /**
     * 
     * @param Results
     *     The Results
     */
    @JsonProperty("Results")
    public void setResults(List<Result> Results) {
        this.Results = Results;
    }

}
