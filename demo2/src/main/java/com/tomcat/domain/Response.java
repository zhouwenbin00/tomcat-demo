package com.tomcat.domain;

import com.tomcat.utils.IOUtils;

import java.io.*;

/**
 * Created by zwb on 2019/11/12 17:35
 */
public class Response {

    private StringBuilder headInfo;//响应头
    private StringBuilder content;//响应内容
    private int length;//响应内容的长度
    //流
    private BufferedWriter bw;

    //两个常量，换行和空格
    private static final String CRLF = "\r\n";//换行
    private static final String BLANK = " ";//空格

    public Response() {
        headInfo = new StringBuilder();
        content = new StringBuilder();
    }

    public Response(OutputStream os) {
        this();//调用本类的无参构造方法
        try {
            bw = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //构造正文部分
    public Response print(String info) {
        content.append(info);
        try {
            length += info.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Response println(String info){
        content.append(info).append(CRLF);
        try {
            length += info.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    //构造响应头
    private void createHeadInfo(int code){
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code) {
            case 200:
                headInfo.append("OK");
                break;
            case 500:
                headInfo.append("SERVER ERROR");
                break;
            default:
                headInfo.append("NOT FOUND");
                break;
        }
        headInfo.append(CRLF);
        headInfo.append("Content-Type:text/html;charset=utf-8").append(CRLF);
        headInfo.append("Content-Length:"+length).append(CRLF);
        headInfo.append(CRLF);
    }

    public void pushToClient(int code){
        if (headInfo==null) {
            code=500;
        }
        try {
            //调用本类中的构造响应头
            this.createHeadInfo(code);
            bw.write(headInfo.toString());
            bw.write(content.toString());
            bw.flush();
            this.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void close(){
        IOUtils.closeAll(bw);
    }
}
