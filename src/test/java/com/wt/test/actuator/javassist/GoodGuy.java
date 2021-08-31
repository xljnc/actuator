package com.wt.test.actuator.javassist;

/**
 * @author 一贫
 * @date 2021/8/31
 */
public class GoodGuy implements People {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void printName() {
        System.out.println(name);
    }
}
