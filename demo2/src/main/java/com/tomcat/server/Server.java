package com.tomcat.server;

import com.tomcat.utils.IOUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zwb on 2019/11/12 17:20
 */
public class Server {

    private ServerSocket server;
    private boolean isShutDown = false;//默认没有出错

    public static void main(String[] args) {
        //创建服务器对象
        Server server = new Server();
        server.start();
    }

    public void start() {
        this.start(8888);
    }

    public void start(int port) {
        try {
            server = new ServerSocket(port);
            //调用接收请求信息的方法
            this.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive(){
        try {
            while (!isShutDown){
                //(1)监听
                Socket client = server.accept();
                //创建线程类的对象
                Dispatcher dis=new Dispatcher(client);
                //创建线程的代理类，并启动线程
                new Thread(dis).start();
            }
        }catch (IOException e){
            e.printStackTrace();
            //关闭服务器
            this.stop();
        }
    }

    public void stop(){
        isShutDown=true;
        IOUtils.closeAll(server);
    }


}
