package com.wt.test.actuator.oom;

import sun.misc.Signal;

import java.util.concurrent.TimeUnit;

/**
 * MacOS下得用 kill -31 PID
 * @author 一贫
 * @date 2021/8/13
 */
public class ShutDownTest {

    public static void main(String[] args) throws Exception {
        Signal signal = new Signal(getOSSignalType());
        Signal.handle(signal, (sig) -> {
            Thread hook = new Thread(() -> {
                System.out.println("ShutdownHook execute start...");
                try {
                    TimeUnit.SECONDS.sleep(2);//模拟应用进程退出前的处理操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ShutdownHook execute end...");
            }, "ShutDown Hook");
            Runtime.getRuntime().addShutdownHook(hook);
            Runtime.getRuntime().exit(0);
        });
        while (true) {
            System.out.println("Program run");
            TimeUnit.SECONDS.sleep(2);
        }
    }

    private static String getOSSignalType() {
        return System.getProperty("os.name").toLowerCase().startsWith("win") ? "INT" : "USR2";
    }
}
