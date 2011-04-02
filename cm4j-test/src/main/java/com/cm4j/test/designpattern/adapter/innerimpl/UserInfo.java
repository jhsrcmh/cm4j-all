package com.cm4j.test.designpattern.adapter.innerimpl;

public class UserInfo implements IUserInfo {

    @Override
    public String getUserName() {
        return "yanghao";
    }

    @Override
    public String getHomeAddress() {
        return "Jiangsu suzhou";
    }

    @Override
    public String getMobileNumber() {
        return "88888888";
    }

    @Override
    public String getOfficeTelNumber() {
        return "66666666";
    }

}
