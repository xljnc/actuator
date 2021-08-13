package com.wt.test.actuator.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 一贫
 * @date 2021/8/12
 */
public class OomTest {

    static class OOMObject {
        private byte[] content = new byte[1024];
    }

    public static void main(String[] args) throws Exception {
        List<OOMObject> list = new ArrayList<>();

        new Thread(() -> {
            while (true) {
                list.add(new OOMObject());
            }
        }).start();

        while (true) {
            System.out.println(Thread.currentThread().getName() + " continuing...");
            Thread.sleep(1000L);
        }
    }
}
