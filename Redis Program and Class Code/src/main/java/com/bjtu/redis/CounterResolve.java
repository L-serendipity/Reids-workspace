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

    //简单判断日期
    public boolean dateValid(int y1,int y2,int m1,int m2,int d1,int d2,int h1,int h2,int min1,int min2,int s1,int s2){
        boolean valid=true;
        //第二个日期要大于第一个
        if(y2<y1)
            valid=false;
        else if(m2<m1)
            valid=false;
        else if(d2<d1)
            valid=false;
        else if(h2<h1)
            valid=false;
        else if(min2<min1)
            valid=false;
        else if(s2<=s1)
            valid=false;
        //都简化按31天算
        if(m1>12||m1<1||m2>12||m2<1||d1>31||d1<1||d2>31||d2<1||h1>23||h2<0||min1>60||min1<0||min2>60||min2<0||s1>60||s1<0||s2>60||s2<0)
            valid=false;
        return valid;
    }

    //计算相差几个小时
    public int[] computeHour(int y1,int y2,int m1,int m2,int d1,int d2,int h1,int h2,int min1,int min2,int s1,int s2){
        int gap[]=new int[7];
        int y,m,d,h=0;
        gap[0]=y2-y1;
        gap[1]=m2-m1;
        gap[2]=d2-d1;
        gap[3]=h2-h1;
        gap[4]=min2-min1;
        gap[5]=s2-s1;
        gap[6]=gap[3]+gap[2]*24+gap[1]*24*24+gap[0]*24*24*24;
        gap[6]=gap[5]+gap[4]*60+gap[3]*60*60+gap[2]*60*60*24+gap[1]*60*60*24*31+gap[0]*60*60*24*31*12;
        return gap;
    }

    public void doCounter(String counterName){
        for (int i=0;i<counterList.size();i++){
            if(counterList.get(i).getCounterName().equals(counterName)){
                action=counterList.get(i);
            }
        }
        System.out.println("执行counter："+action.getCounterName());
        int num;
        String t,t1,t2,date;
        //分别表示起始和终止日期的年，月，日，时
        int y1,y2,m1,m2,d1,d2,h1,h2,min1,min2,s1,s2;
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
                t1=t.substring(0,19);
                y1=Integer.parseInt(t1.substring(0,4));
                m1=Integer.parseInt(t1.substring(5,7));
                d1=Integer.parseInt(t1.substring(8,10));
                h1=Integer.parseInt(t1.substring(11,13));
                min1=Integer.parseInt(t1.substring(14,16));
                s1=Integer.parseInt(t1.substring(17,19));
                date=y1+"-"+m1+"-"+d1+" "+h1+":"+min1+":"+s1;
                num=0;
                if(jedis.hget(action.getKeyFields(),date)!=null){
                    num=Integer.parseInt(jedis.hget(action.getKeyFields(),date));
                }
                num=num+Integer.parseInt(action.getValueFields());
                jedis.hset(action.getKeyFields(),date,String.valueOf(num));
                break;
            case "5":
                System.out.println("keyFields为："+action.getKeyFields());
                System.out.println("Fields为："+action.getFields());
                System.out.println("ValueFields为："+action.getValueFields());
                t=action.getFields();
                t1=t.substring(0,19);
                y1=Integer.parseInt(t1.substring(0,4));
                m1=Integer.parseInt(t1.substring(5,7));
                d1=Integer.parseInt(t1.substring(8,10));
                h1=Integer.parseInt(t1.substring(11,13));
                min1=Integer.parseInt(t1.substring(14,16));
                s1=Integer.parseInt(t1.substring(17,19));
                date=y1+"-"+m1+"-"+d1+" "+h1+":"+min1+":"+s1;
                num=0;
                if(jedis.hget(action.getKeyFields(),date)!=null){
                    num=Integer.parseInt(jedis.hget(action.getKeyFields(),date));
                }
                num=num-Integer.parseInt(action.getValueFields());
                jedis.hset(action.getKeyFields(),date,String.valueOf(num));
                break;
            case "6":
                System.out.println("keyFields为："+action.getKeyFields());
                System.out.println("Fields为："+action.getFields());
                t=action.getFields();
                //解析日期精确到秒
                t1=t.substring(0,19);
                t2=t.substring(20,39);
                y1=Integer.parseInt(t.substring(0,4));
                y2=Integer.parseInt(t.substring(20,24));
                m1=Integer.parseInt(t.substring(5,7));
                m2=Integer.parseInt(t.substring(25,27));
                d1=Integer.parseInt(t.substring(8,10));
                d2=Integer.parseInt(t.substring(28,30));
                h1=Integer.parseInt(t.substring(11,13));
                h2=Integer.parseInt(t.substring(31,33));
                min1=Integer.parseInt(t.substring(14,16));
                min2=Integer.parseInt(t.substring(34,36));
                s1=Integer.parseInt(t.substring(17,19));
                s2=Integer.parseInt(t.substring(37,39));
                if(dateValid(y1,y2,m1,m2,d1,d2,h1,h2,min1,min2,s1,s2)==false){
                    System.out.println("日期起始格式错误");
                    break;
                }
                int gap[]=computeHour(y1,y2,m1,m2,d1,d2,h1,h2,min1,min2,s1,s2);
                num=0;
                for(int i=0;i<gap[6];i++){
                    if(s1+i>59){
                        s1-=60;
                        min1+=1;
                    }
                    if(min1>59){
                        min1=0;
                        h1+=1;
                    }
                    if(h1>23){
                        h1=0;
                        d1+=1;
                    }
                    if(d1>31){
                        d1=1;
                        m1+=1;
                    }
                    if(m1>12){
                        m1=1;
                        y1+=1;
                    }
                    date=y1+"-"+m1+"-"+d1+" "+h1+":"+min1+":"+(s1+i);
                    //System.out.println(date);
                    if(jedis.hget(action.getKeyFields(), date)!=null)
                        num+=Integer.parseInt(jedis.hget(action.getKeyFields(), date));
                }
                System.out.println("在该时间范围下num为："+num);
                break;
        }
    }
}
