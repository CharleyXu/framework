package com.xu.hotswap;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author CharleyXu Created on 2018/8/30.
 *
 * 热部署测试
 */
public class HotSwapTest {

    public static void main(String[] args)
            throws Exception {
        loadClass();
        // 回收资源,释放HelloWorld.class文件，使之可以被替换
        System.gc();
        Thread.sleep(2000);// 等待资源被回收

        File fileV1 = new File(
                "E:\\OpenProjects\\framework\\proxy\\target\\classes\\com\\xu\\proxy\\jdk\\People.class");
        fileV1.delete(); //删除V1版本
        File fileV2 = new File("E:\\OpenProjects\\People1.class");

        fileV2.renameTo(fileV1); //移动V2版本到V1位置
        System.out.println("Update success!");
        loadClass();
    }

    /**
     * 自定义类加载
     */
    public static void loadClass() throws Exception {
        String classpath = "E:\\OpenProjects\\framework\\proxy\\target\\classes";
        String classname = "com.xu.proxy.hotswap.People";
        //自定义类加载器的加载路径
        MyClassLoader myClassLoader = new MyClassLoader(classpath);
        //包名+类名
        Class c = myClassLoader.loadClass(classname);
        if (c != null) {
            Object obj = c.newInstance();
            Method method = c.getMethod("say", null);
            method.invoke(obj, null);
            System.out.println(c.getClassLoader().toString());
        }
    }
}
