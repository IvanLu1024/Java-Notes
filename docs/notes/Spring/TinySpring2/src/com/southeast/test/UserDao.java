package com.southeast.test;

/**
 * Created by 18351 on 2019/1/18.
 */
public class UserDao implements IUserDao{
    @Override
    public void add() {
        System.out.println("添加功能");
    }

    @Override
    public void delete() {
        System.out.println("删除功能");
    }

    @Override
    public void update() {
        System.out.println("更新功能");
    }

    @Override
    public void search() {
        System.out.println("查找功能");
    }
}
