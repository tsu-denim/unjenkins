package com.kodz.unjenkins.server.dto;

/**
 * Created by Kurt on 12/1/15.
 */
public class ViewQuery {
    private String name = null;
    private String folder = null;
    private String regexFilter = null;


    public ViewQuery(){

    };

    public ViewQuery(String viewName, String regexFilter){
        this.name = viewName;
        this.regexFilter = regexFilter;
    }

    public ViewQuery(String viewName, String folder, String regexFilter){
        this.name = viewName;
        this.folder = folder;
        this.regexFilter = regexFilter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegexFilter() {
        return regexFilter;
    }

    public void setRegexFilter(String regexFilter) {
        this.regexFilter = regexFilter;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public boolean isSubView(){
        if (folder == null){
            return false;
        }

        return true;
    }
}
