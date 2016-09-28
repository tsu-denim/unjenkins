package com.kodz.unjenkins.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobStats {

    @JsonProperty("_class")
    private String _class;
    private String displayName;
    private List<Build> builds = new ArrayList<Build>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName
     * The displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     *
     * @return
     * The builds
     */
    public List<Build> getBuilds() {
        return builds;
    }

    /**
     *
     * @param builds
     * The builds
     */
    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}