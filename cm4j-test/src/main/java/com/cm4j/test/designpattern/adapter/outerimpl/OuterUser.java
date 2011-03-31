package com.cm4j.test.designpattern.adapter.outerimpl;

import java.util.HashMap;
import java.util.Map;

public class OuterUser implements IOutUser {

    @Override
    public Map<String, String> getUserBaseInfo() {
        HashMap<String, String> userBaseInfo = new HashMap<String, String>();
        userBaseInfo.put("userName", "outerUserName");
        userBaseInfo.put("mobileNumber", "outer:1234567");
        return userBaseInfo;
    }

    @Override
    public Map<String, String> getUserOfficeInfo() {
        HashMap<String, String> officeInfo = new HashMap<String, String>();
        officeInfo.put("officeNumber", "outer:8888888");
        officeInfo.put("jobPosition", "BOSS");
        return officeInfo;
    }

    @Override
    public Map<String, String> getUserHomeInfo() {
        HashMap<String, String> homeInfo = new HashMap<String, String>();
        homeInfo.put("homeTelNumber", "outer:66666666");
        homeInfo.put("homeAddress", "shanghai");
        return homeInfo;
    }

}
