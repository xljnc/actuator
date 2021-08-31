package com.wt.test.actuator.javassist;

import javassist.*;

import java.lang.reflect.Method;

/**
 * @author 一贫
 * @date 2021/8/31
 */
public class CreatePerson {

    public static void main(String[] args) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        // 1. 创建一个空类Person
        CtClass cPerson = pool.makeClass("com.wt.test.actuator.javassist.Person");
        // 2. 新增一个字段 private String name;
        // 字段名为name
        CtField fName = new CtField(pool.get("java.lang.String"), "name", cPerson);
        // 访问级别是 private
        fName.setModifiers(Modifier.PRIVATE);
        // 初始值是 "xiaoming"
        cPerson.addField(fName, CtField.Initializer.constant("xiaoming"));
        // 3. 生成 getter、setter 方法
        cPerson.addMethod(CtNewMethod.setter("setName", fName));
        cPerson.addMethod(CtNewMethod.getter("getName", fName));
//        // 4. 添加无参的构造函数
//        CtConstructor cons = new CtConstructor(new CtClass[]{}, cPerson);
//        cPerson.addConstructor(cons);
//        // 5. 添加有参的构造函数
//        CtConstructor conWithArgs = new CtConstructor(new CtClass[]{pool.get("java.lang.String")}, cPerson);
//        // $0=this / $1,$2,$3... 代表方法参数
//        conWithArgs.setBody("{$0.name = $1;}");
//        cPerson.addConstructor(conWithArgs);
        // 6. 创建一个名为printName方法，无参数，无返回值，输出name值
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, cPerson);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(name);}");
        cPerson.addMethod(ctMethod);

//        //这里会将这个创建的类对象编译为.class文件
//        cPerson.writeFile(System.getProperty("user.dir") + "/target/test-classes/");

        //反射调用
        Class<?> personClazz = cPerson.toClass();
        Object obj = personClazz.newInstance();
        // 设置值
        Method setName = personClazz.getMethod("setName", String.class);
        setName.invoke(obj, "cunhua");
        // 输出值
        Method execute = personClazz.getMethod("printName");
        execute.invoke(obj);
//        // 设置类路径
//        pool.appendClassPath(System.getProperty("user.dir") + "/target/test-classes/");
//        CtClass ctClass = pool.get("com.wt.test.actuator.javassist.Person");
//        Object person = ctClass.toClass().newInstance();
//        // 设置值
//        Method setName = person.getClass().getMethod("setName", String.class);
//        setName.invoke(person, "cunhua");
//        // 输出值
//        Method execute = person.getClass().getMethod("printName");
//        execute.invoke(person);
    }
}
