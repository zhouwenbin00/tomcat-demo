package com.tomcat.domain;

/**
 * Created by zwb on 2019/11/12 17:54
 */
public abstract class Servlet {
    public void service(Request req, Response rep) throws Exception {
        this.doGet(req, rep);
        this.doPost(req, rep);
    }

    public abstract void doGet(Request req, Response rep) throws Exception;

    public abstract void doPost(Request req, Response rep) throws Exception;
}
