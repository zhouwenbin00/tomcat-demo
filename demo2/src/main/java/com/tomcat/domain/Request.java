package com.tomcat.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by zwb on 2019/11/12 17:24
 */
public class Request {
    private InputStream is;//输入流
    private String requestInfo;//请求字符串：请求方式，路径，参数，协议/协议版本，请求正文
    private String method;//请求方式
    private String url;//请求的url
    private Map<String, List<String>> parametermapValues;//参数
    private static final String CRLF = "\r\n";//换行
    private static final String BLANK = " ";//空格

    //构造方法，初始化属性
    public Request() {
        parametermapValues = new HashMap<String, List<String>>();
        method = "";
        url = "";
        requestInfo = "";
    }

    public Request(InputStream is) {
        this();
        this.is = is;
        try {
            byte[] buf = new byte[20480];
            int len = this.is.read(buf);
            if (len != -1){
                requestInfo = new String(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //调用本类中分解请求信息的方法
        this.parseRequestInfo();
    }

    private void parseRequestInfo() {
        String paraString = "";//用于存储请求参数
        //获取请求参数的第一行
        if (requestInfo.equals("")){
            return;
        }
        String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF)).trim();//从0开始到第一个换
        //分解出请求方式
        int index = firstLine.indexOf("/");//找出斜线的位置GET /(这里) HTTP/1.1
        this.method = firstLine.substring(0, index).trim();//trim()去掉空格
        //分解url,可能包含参数，也可能不包含参数
        String urlString = firstLine.substring(index, firstLine.indexOf("HTTP/")).trim();
        if ("get".equalsIgnoreCase(this.method)) {//GET包含请求参数
            if (urlString.contains("?")) {//包含有问号，说明有参数
                String[] urlArray = urlString.split("\\?");//以?号分割获取参数
                this.url = urlArray[0];
                paraString = urlArray[1];
            } else {
                this.url = urlString;
            }
        } else {//POST不包含请求参数,参数在请求正文
            this.url = urlString;
            //最后一个换行到结尾是请求正文
            paraString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        }
        if (paraString.equals("")) {//如果没有参数
            return;
        }
        parseParam(paraString);
    }

    private void parseParam(String prarString) {
        String[] token = prarString.split("&");
        for (int i = 0; i < token.length; i++) {
            String keyValues = token[i];
            String[] keyValue = keyValues.split("=");  //username   chb   pwd   123
            if (keyValue.length == 1) {  //username=
                keyValue = Arrays.copyOf(keyValue, 2);
                keyValue[1] = null;
            }
            //将  表单元素的name与name对应的值存储到Map集合
            String key = keyValue[0].trim();
            String value = keyValue[1] == null ? null : decode(keyValue[1].trim(), "utf-8");
            //放到集合中存储
            if (!parametermapValues.containsKey(key)) {
                parametermapValues.put(key, new ArrayList<String>());
            }
            List<String> values = parametermapValues.get(key);
            values.add(value);
        }
    }

    //根据表单元素的name获取多个值
    public String[] getParamterValues(String name) {
        //根据key获取value
        List<String> values = parametermapValues.get(name);
        if (values == null) {
            return null;
        } else {
            return values.toArray(new String[0]);
        }

    }

    public String getParameter(String name) {
        //调用本类中根据name获取多个值的方法
        String[] values = this.getParamterValues(name);
        if (values == null) {
            return null;
        } else {
            return values[0];
        }
    }

    //处理中文，因类浏览器对中文进行了编码，进行解码
    private String decode(String value, String code) {
        try {
            return URLDecoder.decode(value, code);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
