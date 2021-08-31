package com.wt.test.actuator.javassist;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

import java.lang.reflect.Method;

/**
 * @author 一贫
 * @date 2021/8/31
 */
public class ProxyFactoryTest {
    public static void main(String[] args)throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(new Class[]{People.class});
        Class<?> proxyClass =  proxyFactory.createClass();
        People javassistProxy = (People) proxyClass.getDeclaredConstructor().newInstance();
        ((ProxyObject) javassistProxy).setHandler(new JavassistInterceptor(new GoodGuy()));
        javassistProxy.setName("qiyu");
        javassistProxy.printName();
    }

    private static class JavassistInterceptor implements MethodHandler {

        // 被代理对象
        private Object delegate;

        private JavassistInterceptor(Object delegate) {
            this.delegate = delegate;
        }

        /**
         * @param self 创建的代理对象
         * @param m 被代理方法
         * @param proceed 如果代理接口，此参数为null，如果代理类，此参数为父类的方法
         * @param args 方法参数
         */
        public Object invoke(Object self, Method m, Method proceed,
                             Object[] args) throws Throwable {
            System.out.println("javassist proxy before sing");
            Object ret = m.invoke(delegate, args);
            System.out.println("javassist proxy after sing");
            return ret;
        }
    }
}
