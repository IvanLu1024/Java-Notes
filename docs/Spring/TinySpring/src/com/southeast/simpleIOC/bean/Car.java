package com.southeast.simpleIOC.bean;

/**
 * Created by 18351 on 2019/1/16.
 */
public class Car {
    private String name;
    private String length;
    private String width;
    private String height;
    private Wheel wheel;

    //...

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", wheel=" + wheel +
                '}';
    }
}
