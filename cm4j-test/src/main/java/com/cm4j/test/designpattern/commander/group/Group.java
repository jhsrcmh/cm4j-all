package com.cm4j.test.designpattern.commander.group;

public abstract class Group {

    public abstract void find();

    public abstract void add();

    public abstract void delete();
    
    public abstract void change();

    public abstract void plan();
    
    public void rollback () {
        System.out.println("根据日志对事物进行回滚");
    }
}
