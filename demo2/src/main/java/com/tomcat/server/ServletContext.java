package com.tomcat.server;

import java.util.HashMap;
import java.util.Map;

/**
 * //上下文 Entity与Mapping的映射关系 实体与映射关系类
 * Created by zwb on 2019/11/12 17:13
 */
public class ServletContext {
    private Map<String, String> servlet;//key是servlet-name,值是servlet-class
    private Map<String, String> mapping;//hashmap键不能重复，值却可以，key是url-pattern, 值是servlet-name

    public ServletContext() {
        servlet= new HashMap<String, String>();
        mapping= new HashMap<String, String>();
    }

    public ServletContext(Map<String, String> servlet, Map<String, String> mapping) {
        this.servlet = servlet;
        this.mapping = mapping;
    }

    public Map<String, String> getServlet() {
        return servlet;
    }

    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }
}
