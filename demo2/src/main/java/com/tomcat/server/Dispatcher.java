package com.tomcat.server;

import com.tomcat.domain.Request;
import com.tomcat.domain.Response;
import com.tomcat.domain.Servlet;
import com.tomcat.utils.IOUtils;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by zwb on 2019/11/12 17:56
 */
public class Dispatcher implements Runnable {
    private Request req;
    private Response rep;
    private Socket client;
    private int code = 200;//状态码

    public Dispatcher(Socket client) {
        this.client = client;
        try {
            req = new Request(this.client.getInputStream());
            rep = new Response(this.client.getOutputStream());
        } catch (IOException e) {
            code = 500;
            return;
        }
    }

    @Override
    public void run() {
        //根据不同的url创建指定的Servlet对象
        Servlet servlet = WebApp.getServlet(req.getUrl());
        if (servlet == null) {
            this.code = 404;
        } else {
            //调用相应的Servlet中的service方法
            try {
                servlet.service(req, rep);
            } catch (Exception e) {
                this.code = 500;
            }
        }
        //将响应结果推送到客户机的浏览器
        rep.pushToClient(code);
        IOUtils.closeAll(client);

    }
}
