package com.bjtu.redis;

public class CounterSpec {
    private String counterName;
    private String counterIndex;
    private String type;
    private String keyFields;
    private String fields;
    private String valueFields;


    public CounterSpec(String counterName,String counterIndex,String type,String keyFields,String fields,String valueFields) {
        this.counterName = counterName;
        this.counterIndex = counterIndex;
        this.type = type;
        this.keyFields = keyFields;
        this.fields = fields;
        this.valueFields = valueFields;
    }

    public String getCounterName(){
        return this.counterName;
    }

    public String getCounterIndex() {
        return counterIndex;
    }

    public String getType() {
        return type;
    }

    public String getFields() {
        return fields;
    }

    public String getKeyFields() {
        return keyFields;
    }

    public String getValueFields() {
        return valueFields;
    }
}
