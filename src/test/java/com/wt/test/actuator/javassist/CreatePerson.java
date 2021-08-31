package com.wt.test.actuator.javassist;

import javassist.*;

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
        // 4. 添加无参的构造函数
        CtConstructor cons = new CtConstructor(new CtClass[]{}, cPerson);
        cPerson.addConstructor(cons);
        // 5. 添加有参的构造函数
        cons = new CtConstructor(new CtClass[]{pool.get("java.lang.String")}, cPerson);
        // $0=this / $1,$2,$3... 代表方法参数
        cons.setBody("{$0.name = $1;}");
        cPerson.addConstructor(cons);
        // 6. 创建一个名为printName方法，无参数，无返回值，输出name值
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, cPerson);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(name);}");
        cPerson.addMethod(ctMethod);

        //这里会将这个创建的类对象编译为.class文件
        cPerson.writeFile(System.getProperty("user.dir") + "/target/test-classes/");
    }
}
