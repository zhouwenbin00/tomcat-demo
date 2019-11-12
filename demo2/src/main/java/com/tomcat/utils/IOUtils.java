package com.tomcat.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by zwb on 2019/11/12 16:55
 */
public class IOUtils {

    public static void closeAll(Closeable... close) {
        for (Closeable closeable : close) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
