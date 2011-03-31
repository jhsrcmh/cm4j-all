package com.cm4j.test.designpattern.adapter.outerimpl;

import java.util.Map;

public interface IOutUser {
    Map<String, String> getUserBaseInfo();

    Map<String, String> getUserOfficeInfo();

    Map<String, String> getUserHomeInfo();

}
