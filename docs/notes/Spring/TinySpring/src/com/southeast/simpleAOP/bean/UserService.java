package com.southeast.simpleAOP.bean;

/**
 * Created by 18351 on 2019/1/16.
 */
public class UserService implements IUserService{

    @Override
    public void sayHello() {
        System.out.println("Hello");
    }
}
