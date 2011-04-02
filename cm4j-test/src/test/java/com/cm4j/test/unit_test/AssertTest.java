package com.cm4j.test.unit_test;

/**
 * 在运行时添加VM参数-ea，则在不对的地方显示异常
 * @author yanghao
 *
 */
public class AssertTest {

    public void assertor() {
        int x = 1;
        assert x == 1 ? true : false : "无错误";
        System.out.println("print yes");
        
        assert x == 2 ? true : false : "有错误！"; // 抛异常
        System.out.println("no print");
    }

    public static void main(String[] args) {
        AssertTest assertTest = new AssertTest();
        assertTest.assertor();
    }
}
