package com.bjtu.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.bjtu.redis.JsonUtil.readJsonFile;


public class Main {

    private static FileAlterationMonitor monitor;
    public static AtomicBoolean lock = new AtomicBoolean(false);

    static ArrayList<CounterSpec> counterList;
    static ArrayList<ActionSpec> actionList;

    public static void jsonFileConfig(){
        readCounter();
        readAction();
    }

    public static void readCounter(){
        counterList=new ArrayList<CounterSpec>();
        counterList.clear();
        //String path=Main.class.getClassLoader().getResource("counter.json").getPath();
        String path="json/counter.json";
        String s=readJsonFile(path);
        JSONObject jobj= JSON.parseObject(s);
        JSONArray counters=jobj.getJSONArray("counter");
        for(int i=0;i<counters.size();i++){
            JSONObject key=(JSONObject)counters.get(i);
            String counterName=(String)key.get("counterName");
            String counterIndex=(String) key.get("counterIndex");
            String type=(String)key.get("type");
            String keyFields=(String)key.get("keyFields");
            String fields=(String)key.get("fields");
            String valueFields=(String)key.get("valueFields");
            CounterSpec c=new CounterSpec(counterName,counterIndex,type,keyFields,fields,valueFields);
            counterList.add(c);
        }
    }

    public static ArrayList<CounterSpec> readCounter_2(){
        ArrayList<CounterSpec> counterList_2=new ArrayList<CounterSpec>();
        counterList_2.clear();
        //String path=Main.class.getClassLoader().getResource("counter.json").getPath();
        String path="json/counter.json";
        String s=readJsonFile(path);
        JSONObject jobj= JSON.parseObject(s);
        JSONArray counters=jobj.getJSONArray("counter");
        for(int i=0;i<counters.size();i++){
            JSONObject key=(JSONObject)counters.get(i);
            String counterName=(String)key.get("counterName");
            String counterIndex=(String) key.get("counterIndex");
            String type=(String)key.get("type");
            String keyFields=(String)key.get("keyFields");
            String fields=(String)key.get("fields");
            String valueFields=(String)key.get("valueFields");
            CounterSpec c=new CounterSpec(counterName,counterIndex,type,keyFields,fields,valueFields);
            counterList_2.add(c);
        }
        return counterList_2;
    }

    public static void readAction(){
        actionList=new ArrayList<ActionSpec>();
        actionList.clear();
        //String path=Main.class.getClassLoader().getResource("json/action.json").getPath();
        String path="json/action.json";
        String s=readJsonFile(path);
        JSONObject jobj= JSON.parseObject(s);
        JSONArray actions=jobj.getJSONArray("action");
        for(int i=0;i<actions.size();i++){
            ArrayList<String> retrieveCounterName=new ArrayList<String>();
            ArrayList<String> saveCounterName=new ArrayList<String>();

            JSONObject key=(JSONObject)actions.get(i);

            String actionName=(String)key.get("actionName");

            JSONArray retrieve=(JSONArray)key.get("retrieve");

            for(int j=0;j<retrieve.size();j++){
                JSONObject key2 = (JSONObject) retrieve.get(j);
                retrieveCounterName.add((String)key2.get("counterName"));
            }

            JSONArray save=(JSONArray)key.get("save");
            for(int j=0;j<save.size();j++) {
                JSONObject key2 = (JSONObject) save.get(j);
                saveCounterName.add((String) key2.get("counterName"));
            }

            ActionSpec a=new ActionSpec(actionName,retrieveCounterName,saveCounterName);
            actionList.add(a);
        }
    }

    private static void startObserver() throws Exception {
        String monitorDir = "json";
        long interval = TimeUnit.SECONDS.toMillis(1);
        FileAlterationObserver observer = new FileAlterationObserver(monitorDir);
        observer.addListener(new FileListenUtil());
        // 创建文件变化监听器
        monitor = new FileAlterationMonitor(interval, observer);
        // 开始监听
        monitor.start();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("赖伟江_18231642");
        startObserver();
        jsonFileConfig();
        Scanner sc=new Scanner(System.in);
        String str;
        do{
            System.out.println("您当前所有的的action如下：");
            for(int i=0;i<actionList.size();i++) {
                System.out.println((i+1)+". "+actionList.get(i).getActionName());
            }
            System.out.println("请输入要执行的action序号（0退出）：");
            str=sc.nextLine();
            if(str.equals("1")||str.equals("2")||str.equals("3")||str.equals("4")){
                ActionResolve a=new ActionResolve();
                a.doAction(actionList.get(Integer.parseInt(str)-1));

            }
            else if(str.equals("0")){
                monitor.stop();
                System.exit(1);
            }
            else {
                System.out.println("输入的指令有误，请重新输入");
            }
        }
        while(!str.equals("0"));
    }
}
