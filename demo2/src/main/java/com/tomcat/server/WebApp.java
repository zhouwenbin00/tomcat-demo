package com.tomcat.server;

import com.tomcat.domain.Entitty;
import com.tomcat.domain.Mapping;
import com.tomcat.domain.Servlet;
import com.tomcat.domain.WebDom4j;

import java.util.List;
import java.util.Map;

/**
 * Created by zwb on 2019/11/12 17:13
 */
public class WebApp {
    private static ServletContext context;

    static {//静态初始化代码块
        context = new ServletContext();
        //分别获取对应关系的Map集合
        Map<String, String> servlet = context.getServlet();
        Map<String, String> mapping = context.getMapping();
        //解析XML文件对象
        WebDom4j web = new WebDom4j();
        //解析XML并把数据放到了entityList和mappingList当中
        web.parse(web.getDocument());
        //获取解析XML之后的List集合
        List<Mapping> mappingList = web.getMappingList();
        List<Entitty> entitiesList = web.getEntitiesList();
        //将List集合中的数据存储到Map集合
        for (Entitty entitty : entitiesList) {
            servlet.put(entitty.getName(), entitty.getClazz());
        }
        for (Mapping map : mappingList) {
            for (String s : map.getUrlPattern()) {
                mapping.put(s, map.getName());
            }
        }

    }


    public static Servlet getServlet(String url) {
        if (url == null || url.trim().equals("")) {
            return null;
        }
        try {
            //如果url正确
            String servletName = context.getMapping().get(url);
            if (servletName == null) {
                return null;
            }
            //根据servlet-name得到对应的servlet-class
            String servletClass = context.getServlet().get(servletName);//等到的是一个完整的包名+类名字符串
            if (servletClass == null) {
                return null;
            }
            //使用反射创建servlet对象
            Class<?> clazz = Class.forName(servletClass);
            //调用无参构造方法创建servlet对象
            Servlet servlet = (Servlet) clazz.newInstance();
            return servlet;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
