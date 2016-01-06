package com.kodz.unjenkins.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kurt on 11/23/15.
 */
public class JobStatus {
    private String name;
    private List<BuildStatus> buildStatusList = new ArrayList<BuildStatus>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("builds")
    public List<BuildStatus> getBuildStatusList() {
        return buildStatusList;
    }

    public void setBuildStatusList(List<BuildStatus> buildStatusList) {
        this.buildStatusList = buildStatusList;
    }
}
