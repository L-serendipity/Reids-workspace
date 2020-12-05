package com.bjtu.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class JsonUtil {
    public static String readJsonFile(String fileName){
        String jsonstr="";
        try{
            File jsonFile=new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader=new InputStreamReader(new FileInputStream(jsonFile),"utf-8");

            int ch=0;
            StringBuffer sb=new StringBuffer();
            while((ch=reader.read())!=-1){
                sb.append((char)ch);
            }
            fileReader.close();
            reader.close();
            jsonstr=sb.toString();
            return jsonstr;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }


}
