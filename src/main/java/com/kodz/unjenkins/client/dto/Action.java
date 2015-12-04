package com.kodz.unjenkins.client.dto;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Action {

    private Integer failCount ;
    private Integer skipCount ;
    private Integer totalCount ;
    private Integer passedCount ;

    /**
     *
     * @return
     * The failCount
     */
    public Integer getFailCount() {
        return failCount;
    }

    /**
     *
     * @param failCount
     * The failCount
     */
    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    /**
     *
     * @return
     * The skipCount
     */
    public Integer getSkipCount() {
        return skipCount;
    }

    /**
     *
     * @param skipCount
     * The skipCount
     */
    public void setSkipCount(Integer skipCount) {
        this.skipCount = skipCount;
    }

    /**
     *
     * @return
     * The totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     *
     * @param totalCount
     * The totalCount
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(Integer passedCount) {
        this.passedCount = passedCount;
    }
}