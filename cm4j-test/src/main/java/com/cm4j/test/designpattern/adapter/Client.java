package com.cm4j.test.designpattern.adapter;

import com.cm4j.test.designpattern.adapter.innerimpl.IUserInfo;
import com.cm4j.test.designpattern.adapter.innerimpl.UserInfo;

public class Client {

    public static void main(String[] args) {
        // 调用内部
        IUserInfo userInfo = new UserInfo();
        // 调用外部
        // IUserInfo userInfo = new OuterUserInfo();
        System.out.println(userInfo.getHomeAddress());
        System.out.println(userInfo.getUserName());
    }
}
