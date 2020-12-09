package com.bjtu.redis;

import java.text.ParseException;
import java.util.ArrayList;

public class ActionResolve {

    public void doAction(ActionSpec actionSpec) throws ParseException {
        ArrayList<String> retrieveCounterNameList=actionSpec.getRetrieveCounterName();
        String retrieveCounterName = null;
        for(int i=0;i<retrieveCounterNameList.size();i++){
            retrieveCounterName=retrieveCounterNameList.get(i);
        }
        ArrayList<String> saveCounterNameList=actionSpec.getSaveCounterName();
        String saveCounterName = null;
        for(int i=0;i<saveCounterNameList.size();i++){
            saveCounterName=saveCounterNameList.get(i);
        }

        CounterResolve c=new CounterResolve();

        c.doCounter(saveCounterName);
        System.out.println();
        c.doCounter(retrieveCounterName);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
