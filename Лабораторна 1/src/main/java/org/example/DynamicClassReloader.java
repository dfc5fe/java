package org.example;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;

public class DynamicClassReloader {

    private static final String JAVA_FILE = "src/main/java/org/example/TestModule.java";
    private static final String CLASS_NAME = "org.example.TestModule";

    public static void startWatching() throws Exception {
        Path path = Paths.get("src/main/java/org/example");
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        System.out.println("Слідкую за змінами в TestModule.java...");

        while (true) {
            WatchKey key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.context().toString().equals("TestModule.java")) {
                    System.out.println("Виявлено зміну у TestModule.java, перевантажую...");
                    loadAndPrint();
                }
            }

            if (!key.reset()) {
                break;
            }
        }
    }

    private static void loadAndPrint() throws Exception {
        // 1. Компіляція
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int res = compiler.run(null, null, null, JAVA_FILE);
        if (res != 0) {
            System.out.println("Помилка компіляції TestModule.java");
            return;
        }

        // 2. Завантаження нового класу
        File root = new File("src/main/java");
        URLClassLoader classLoader = new URLClassLoader(
                new URL[]{root.toURI().toURL()},
                null // щоб не брати старий клас із classpath
        );

        Class<?> clazz = classLoader.loadClass(CLASS_NAME);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        System.out.println(">>> Новий екземпляр: " + instance);
    }
}
