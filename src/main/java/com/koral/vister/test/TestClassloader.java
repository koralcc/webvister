package com.koral.vister.test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description:
 * @Author: chejianyun
 * @Date: 2024/5/25
 */

public class TestClassloader {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//        Class<TestClass> testClassClass = TestClass.class;
//        TestClass testClass = new TestClass();
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    // getResourceAsStream() 加载当前工程下的类文件，如果路径是在源文件之外的其他文件夹时，不能用此方法
                    // 1. /com/koral/vister/test/TestClassloader.class ：/代表的是当前项目根目录，相当于我们的webvisitercount
                    // 2. TestClassloader.class ： 当前类目录下找
                    byte[] b;
                    try (InputStream is = getClass().getResourceAsStream(fileName)) {
                        if (is == null) {
                            return super.loadClass(name);
                        }
                        b = new byte[is.available()];
                        int read = is.read(b);
                    }
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj = myLoader.loadClass("com.koral.vister.test.TestClassloader").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof com.koral.vister.test.TestClassloader);
    }

}
