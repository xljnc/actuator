package com.wt.test.actuator.javassist;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.IntegerMemberValue;

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
        cfDelegate.setModifiers(Modifier.PRIVATE);
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
        printNameMethod.insertBefore(" System.out.println(\"javassist bytecode proxy before printName\");");
        printNameMethod.insertAfter(" System.out.println(\"javassist bytecode proxy after printName\");");
        goodGuyProxy.addMethod(printNameMethod);


        ConstPool constpool  = printNameMethod.getMethodInfo().getConstPool();
        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation(Override.class.getName(), constpool);
        annotationsAttribute.addAnnotation(annotation);
        printNameMethod.getMethodInfo().addAttribute(annotationsAttribute);

        People goodguy = (People) goodGuyProxy.toClass().newInstance();
        Field deleteF = goodguy.getClass().getDeclaredField("delegate");
        deleteF.setAccessible(true);
        deleteF.set(goodguy, new GoodGuy());
        goodguy.setName("qiyu");
        goodguy.printName();

        goodGuyProxy.writeFile(System.getProperty("user.dir") + "/target/test-classes/");

    }

}
