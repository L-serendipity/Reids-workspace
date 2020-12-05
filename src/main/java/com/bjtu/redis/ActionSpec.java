package com.bjtu.redis;

import java.util.ArrayList;

public class ActionSpec {
    private String actionName;
    private ArrayList<String> retrieveCounterName;
    private ArrayList<String> saveCounterName;

    public ActionSpec(String actionName,ArrayList<String>retrieveCounterName,ArrayList<String> saveCounterName){
        this.actionName=actionName;
        this.retrieveCounterName=retrieveCounterName;
        this.saveCounterName=saveCounterName;
    }
    public String getActionName(){
        return actionName;
    }

    public ArrayList<String> getRetrieveCounterName(){
        return retrieveCounterName;
    }

    public ArrayList<String> getSaveCounterName(){
        return saveCounterName;
    }

}
