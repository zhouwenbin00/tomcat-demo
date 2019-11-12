package com.tomcat.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwb on 2019/11/12 17:02
 */
public class Mapping {//映射关系  多个路径访问共享资源 servlet-name和url-pattern对应的实体类 多个资源与小名之间的关系

    private String name;//servlet-name
    private List<String> urlPattern;//url-pattern

    public Mapping(String name, List<String> urlPattern) {
        this.name = name;
        this.urlPattern = urlPattern;
    }

    public Mapping() {
        this.urlPattern = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(List<String> urlPattern) {
        this.urlPattern = urlPattern;
    }
}
