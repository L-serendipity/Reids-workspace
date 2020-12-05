package com.bjtu.redis;

import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CounterResolve {

    ArrayList<CounterSpec> counterList;
    CounterSpec action;

    public CounterResolve(){
        counterList=null;
        counterList=Main.readCounter_2();
    }

    public void doCounter(String counterName){
        for (int i=0;i<counterList.size();i++){
            if(counterList.get(i).getCounterName().equals(counterName)){
                action=counterList.get(i);
            }
        }
        System.out.println("执行counter："+action.getCounterName());
        int num;
        String t,t1;
        RedisUtil jedis=new RedisUtil();
        switch (action.getCounterIndex()){
            case "1":
                System.out.println("keyFields为："+action.getKeyFields());
                System.out.println("ValueFields为："+action.getValueFields());
                num=0;


                if(jedis.get(action.getKeyFields())!=null){
                    num=Integer.parseInt(jedis.get(action.getKeyFields()));
                }
                num=num+Integer.parseInt(action.getValueFields());
                jedis.set(action.getKeyFields(),String.valueOf(num));
                break;
            case "2":
                System.out.println("keyFields为："+action.getKeyFields());
                System.out.println("ValueFields为："+action.getValueFields());
                num=0;
                if(jedis.get(action.getKeyFields())!=null){
                    num=Integer.parseInt(jedis.get(action.getKeyFields()));
                }
                num=num-Integer.parseInt(action.getValueFields());
                jedis.set(action.getKeyFields(),String.valueOf(num));
                break;
            case "3":
                System.out.println("keyFields为："+action.getKeyFields());
                num=0;
                if(jedis.get(action.getKeyFields())!=null){
                    num=Integer.parseInt(jedis.get(action.getKeyFields()));
                    System.out.println("当前num值为："+num);
                }
                else
                    System.out.println("当前num值为空");
                break;
            case "4":
                System.out.println("keyFields为："+action.getKeyFields());
                System.out.println("Fields为："+action.getFields());
                System.out.println("ValueFields为："+action.getValueFields());
                t=action.getFields();
                t1=t.substring(0,14)+"00:00";
                num=0;
                if(jedis.hget(action.getKeyFields(),t1)!=null){
                    num=Integer.parseInt(jedis.hget(action.getKeyFields(),t1));
                }
                num=num+Integer.parseInt(action.getValueFields());
                jedis.hset(action.getKeyFields(),t1,String.valueOf(num));
                break;
            case "5":
                System.out.println("keyFields为："+action.getKeyFields());
                System.out.println("Fields为："+action.getFields());
                System.out.println("ValueFields为："+action.getValueFields());
                t=action.getFields();
                t1=t.substring(0,14)+"00:00";
                num=0;
                if(jedis.hget(action.getKeyFields(),t1)!=null){
                    num=Integer.parseInt(jedis.hget(action.getKeyFields(),t1));
                }
                num=num-Integer.parseInt(action.getValueFields());
                jedis.hset(action.getKeyFields(),t1,String.valueOf(num));
                break;
            case "6":
                System.out.println("keyFields为："+action.getKeyFields());
                System.out.println("Fields为："+action.getFields());
                t=action.getFields();
                t1=t.substring(0,14)+"00:00";
                num=0;
                if(jedis.hget(action.getKeyFields(),t1)!=null){
                    num=Integer.parseInt(jedis.hget(action.getKeyFields(),t1));
                    System.out.println("在当前Fields下num为："+num);
                }
                else
                    System.out.println("在当前Fields下num为空");
                break;
        }
    }
}
