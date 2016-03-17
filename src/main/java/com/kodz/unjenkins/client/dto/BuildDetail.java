package com.kodz.unjenkins.client.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class BuildDetail {

    private List<Action> actions = new ArrayList<Action>();
    private Boolean building;
    private Integer number;
    private String result;
    private String url;

    /**
     *
     * @return
     * The actions
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     *
     * @param actions
     * The actions
     */
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    /**
     *
     * @return
     * The building
     */
    public Boolean getBuilding() {
        return building;
    }

    /**
     *
     * @param building
     * The building
     */
    public void setBuilding(Boolean building) {
        this.building = building;
    }

    /**
     *
     * @return
     * The number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     *
     * @param number
     * The number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     *
     * @return
     * The result
     */
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public Action getTestResults(){
        //TODO: Add support for multiple configuration jobs
        Action action = new Action();
        ArrayList<Action> actionList = new ArrayList<Action>();
        actionList = actions.stream().filter(t -> (t.getFailCount() != null && t.getSkipCount() != null && t.getTotalCount() != null))
                .limit(1L)
                .collect(Collectors.toCollection(ArrayList::new));

        if (actionList.size()==1){
            action = actionList.get(0);
            action.setPassedCount(action.getTotalCount() - (action.getFailCount() + action.getSkipCount()));
        }

        else {
            if ((this.getResult() == null)){
                    this.setResult("CORRUPT");
            }

            action.setPassedCount(0);
            action.setFailCount(0);
            action.setSkipCount(0);
            action.setTotalCount(0);
        }

        return action;
    }

}


