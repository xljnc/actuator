package com.wt.test.actuator.javassist;

import javassist.*;

import java.lang.reflect.Field;

/**
 * @author 一贫
 * @date 2021/8/31
 */
public class PeopleProxyTest {
    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass peopleClazz = pool.get("com.wt.test.actuator.javassist.People");
        CtClass goodGuyProxy = pool.makeClass("com.wt.test.actuator.javassist.GoodGuy" + "Proxy");
        goodGuyProxy.addInterface(peopleClazz);
        goodGuyProxy.addConstructor(CtNewConstructor.defaultConstructor(goodGuyProxy));
        CtField cfDelegate = new CtField(peopleClazz, "delegate", goodGuyProxy);
        goodGuyProxy.addField(cfDelegate);
        String setNameMethodSrc = "    public void setName(String name){\n" +
                "        System.out.println(\"javassist bytecode proxy before setName\");\n" +
                "        delegate.setName(name);\n" +
                "        System.out.println(\"javassist bytecode proxy after setName\");\n" +
                "    }";
        CtMethod setNameMethod = CtMethod.make(setNameMethodSrc, goodGuyProxy);
        goodGuyProxy.addMethod(setNameMethod);
        CtMethod printNameMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, goodGuyProxy);
        printNameMethod.setBody("{System.out.println(delegate.getName());}");
        printNameMethod.insertBefore(" System.out.println(\"javassist bytecode proxy before printName\");\n");
        printNameMethod.insertAfter(" System.out.println(\"javassist bytecode proxy after printName\");\n");
        goodGuyProxy.addMethod(printNameMethod);
        People goodguy = (People) goodGuyProxy.toClass().newInstance();
        Field deleteF = goodguy.getClass().getDeclaredField("delegate");
        deleteF.setAccessible(true);
        deleteF.set(goodguy, new GoodGuy());
        goodguy.setName("qiyu");
        goodguy.printName();
    }

}
