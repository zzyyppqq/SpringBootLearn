package com.zyp.springboot.learn.classloader;

public class ClassPathTest {
    public static void main(String[] args) {
        // JDK Extendsions目录
        System.out.println(System.getProperty("java.ext.dirs"));
        // 类路径，默认为当前工作目录，可以通过"-classpath"或"-cp"变量修改
        System.out.println(System.getProperty("java.class.path"));
        System.out.println("---");
        // sun.misc.Launcher$AppClassLoader@18b4aac2
        // sun.misc.Launcher$ExtClassLoader@33833882
        printClassLoaderTree(new ClassPathTest());
        // 因为Object是BootstrapClassLoader加载，所以不会打印
        printClassLoaderTree(new Object());

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader platformClassLoader = ClassLoader.getPlatformClassLoader();

        // java层不能直接获取到 Bootstrap ClassLoader的实例对象，在 Java 代码中它通常表现为 null
        //  获取其父加载器 (在JDK 8是ExtClassLoader, 在JDK 9+是PlatformClassLoader)
        System.out.println("systemClassLoader: " + systemClassLoader);
        System.out.println("platformClassLoader: " + platformClassLoader);


        // 验证核心类由谁加载
        System.out.println("String.class ClassLoader: " + String.class.getClassLoader());
        System.out.println("ArrayList.class ClassLoader: " + java.util.ArrayList.class.getClassLoader());

        System.out.println("ClassPathTest.class classLoader: " + ClassPathTest.class.getClassLoader());
        System.out.println("new ClassPathTest classLoader: " + new ClassPathTest().getClass().getClassLoader());
    }

    private static void printClassLoaderTree(Object target) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println("target: " + target + ", classLoader: " + classLoader + ", threadClassLoader: " + threadClassLoader);
        while (classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }
    }
}