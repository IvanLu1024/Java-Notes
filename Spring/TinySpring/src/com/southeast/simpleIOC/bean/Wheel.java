package com.southeast.simpleIOC.bean;

/**
 * Created by 18351 on 2019/1/16.
 */
public class Wheel {
    private String brand;
    private String price ;

    //...

    @Override
    public String toString() {
        return "Wheel{" +
                "brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
