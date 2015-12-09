package com.kodz.unjenkins.server.exceptions;

/**
 * Created by Kurt on 12/9/15.
 */
public class ViewNotFound extends Exception {
    public String getViewMessage(){
        return "View not found or does not contain child jobs.";
    }
}
