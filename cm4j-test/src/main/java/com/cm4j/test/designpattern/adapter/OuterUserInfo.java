package com.cm4j.test.designpattern.adapter;

import java.util.Map;

import com.cm4j.test.designpattern.adapter.innerimpl.IUserInfo;
import com.cm4j.test.designpattern.adapter.outerimpl.OuterUser;

/**
 * <pre>
 * 适配器模式 - 将IOuterUser适配到IUserInfo
 * 实质：继承(或引用)外部IOuterUser的实现类OuterUser，来实现本地接口IUserInfo
 * 适用范围：让两个没有关系的类在一起运行；
 *      在本地IUserInfo接口不能变的情况下，需要用到外部实现，尤其适用已经在使用的接口，比如系统扩展
 *      因此在设计时尽量不要使用！它主要解决的是正在使用的项目问题，而不是开发阶段的问题
 * </pre>
 * 
 * @author yanghao
 *
 */
public class OuterUserInfo extends OuterUser implements IUserInfo {

    private Map<String, String> baseInfo = super.getUserBaseInfo();

    private Map<String, String> homeInfo = super.getUserHomeInfo();

    private Map<String, String> officeInfo = super.getUserOfficeInfo();

    @Override
    public String getUserName() {
        String userName = baseInfo.get("userName");
        System.out.println(userName);
        return userName;
    }

    @Override
    public String getHomeAddress() {
        return homeInfo.get("homeAddress");
    }

    @Override
    public String getMobileNumber() {
        return baseInfo.get("mobileNumber");
    }

    @Override
    public String getOfficeTelNumber() {
        return officeInfo.get("officeNumber");
    }

}
