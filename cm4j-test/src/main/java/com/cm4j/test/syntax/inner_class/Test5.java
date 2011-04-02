package com.cm4j.test.syntax.inner_class;

/**
 * <pre>
 * 嵌套类 - static的内部类就叫做嵌套类 
 * 
 * 重点关注：
 * a、创建嵌套类对象时，不需要外围类 
 * b、在嵌套类中，不能像普通内部类一样访问外围类的非static成员
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class Test5 {

    @SuppressWarnings("unused")
    private int num;

    private static int sum = 2;

    private static class StaticInnerClass {
        public int getNum() {
            // 只能访问sum，不能访问num
            return sum;
        }
    }

    public static void main(String[] args) {
        // 可以直接通过new来创建嵌套类对象
        StaticInnerClass inner = new StaticInnerClass();
        inner.getNum();
    }
}
