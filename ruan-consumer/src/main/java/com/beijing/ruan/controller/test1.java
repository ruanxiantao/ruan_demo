package com.beijing.ruan.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class test1 {
    public static boolean initFlag = false;
    public static void main(String[] args) {
        String url1 = "http://dangjian.people.com.cn/GB/78694/index.html";
        String urlRegular = "var object={ getHref:function(document){ var as= document.select('.fl ul li a');var mystr='';for(var item in as){var str=as.get(item).attr('href')+';';var substr='http';if(str.indexOf(substr)==-1){str='http://dangjian.people.com.cn'+str} mystr=mystr+str;} return mystr;}}";
        String contentRegular = "var contentObject={getTitle:function(document){return document.select('h1').text()},getContent:function(docment){var content= docment.select('.show_text').html();var regexp=/<div\\s*class=\"zdfy\\s*clearfix\">[\\s\\S]+<\\/center>/gi;if(regexp.test(content) && content!=null){content=content.replace(regexp,'');}return content;},getAbstract:function(docment){var mystr='';var ps=docment.select('.show_text p:eq(1)');mystr=ps.text(); return mystr;}}";
        try {
            Document document1 = Jsoup.connect(url1).get();
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine engineByName = scriptEngineManager.getEngineByName("js");
            try {
                engineByName.eval(urlRegular);
                Object object = engineByName.get("object");
                Invocable invocable = (Invocable) engineByName;
                String links = (String) invocable.invokeMethod(object, "getHref", document1);
                String[] hrefs = links.split(";");
                Set<String> set = new HashSet<>();
                for (String href : hrefs) {
                    set.add(href);
                }
                for (String href : set) {
                    Document document = Jsoup.connect(href).get();
                    ScriptEngineManager sem = new ScriptEngineManager();
                    ScriptEngine scriptEngine = sem.getEngineByName("js");
                    scriptEngine.eval(contentRegular);
                    Object contentObject = scriptEngine.get("contentObject");
                    Invocable invo = (Invocable) scriptEngine;
                    String title = (String) invo.invokeMethod(contentObject, "getTitle", document);
//                    System.out.println("title:"+title);
                    String summary = (String) invo.invokeMethod(contentObject, "getAbstract", document);
//                    System.out.println("summary:"+summary);
                    String content = (String) invo.invokeMethod(contentObject, "getContent", document);
                    if (content.length() < 20) {
                        System.out.println("title:"+title);
                        System.out.println("summary:"+summary);
                        System.out.println(content);
                    }
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("thread1 开始.....");
//                while(!initFlag){
//                }
//                System.out.println("initFlag == true");
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("thread2开始...");
//                change();
//            }
//        }).start();
    }

    private static void change() {
        initFlag = true;
        System.out.println("已将initFlag改为true.....");
    }
}
