package com.tomcat.domain;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用于解析XML
 * Created by zwb on 2019/11/12 16:56
 */
public class WebDom4j {
    //用于存储N多Entity,而每一个Entity都是servlet-name与servlet-class
    private List<Entitty> entitiesList;
    //用于存储N多Mapping，而每一个Mapping都是一个servlet-name与多个url-pattern
    private List<Mapping> mappingList;

    //构造方法
    public WebDom4j(List<Entitty> entitiesList, List<Mapping> mappingList) {
        this.entitiesList = entitiesList;
        this.mappingList = mappingList;
    }

    public WebDom4j() {
        this.entitiesList = new ArrayList<Entitty>();
        this.mappingList = new ArrayList<Mapping>();
    }

    //获取Document对象的方法
    public Document getDocument() {
        try {
            SAXReader reader = new SAXReader();
            return reader.read(new File("E:\\IdeaProjects\\other\\demo1\\demo2\\src\\main\\resources\\web.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    //把获取到的Document对象解析
    public void parse(Document doc) {
        //(1)获取根元素
        Element root = doc.getRootElement();
        //(2)解析servlet
        Iterator<Element> ite = root.elementIterator("servlet");
        while (ite.hasNext()) {
            Element subElement = ite.next();//得到每一个servlet
            //创建一个实体类
            Entitty ent = new Entitty();//用于存储servlet-name与servlet-class
            Iterator<Element> subite = subElement.elementIterator();
            while (subite.hasNext()) {
                Element ele = subite.next();
                if ("servlet-name".equals(ele.getName())) {
                    ent.setName(ele.getText());//给实体类中的name赋值
                } else if ("servlet-class".equals(ele.getName())) {
                    ent.setClazz(ele.getText());
                }
            }
            //经过上面的循环后Entity有值了，把Entity添加到集合中
            entitiesList.add(ent);
        }
        //解析servlet-mapping
        ite = root.elementIterator("servlet-mapping");
        while (ite.hasNext()) {
            Element subEle = ite.next();//得到每一个servlet-mapping
            //创建一个mapping类对象
            Mapping map = new Mapping();
            //解析servlet-mapping下的子元素
            Iterator<Element> subite = subEle.elementIterator();
            while (subite.hasNext()) {
                Element ele = subite.next();//可能是servlet-name，也可能是url-pattern
                if ("servlet-name".equals(ele.getName())) {
                    map.setName(ele.getText());//给实体类中的name赋值
                } else if ("url-pattern".equals(ele.getName())) {
                    map.getUrlPattern().add(ele.getText());
                }
            }
            //mapping添加到集合中
            mappingList.add(map);
        }
    }


    public List<Entitty> getEntitiesList() {
        return entitiesList;
    }

    public void setEntitiesList(List<Entitty> entitiesList) {
        this.entitiesList = entitiesList;
    }

    public List<Mapping> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<Mapping> mappingList) {
        this.mappingList = mappingList;
    }
}
