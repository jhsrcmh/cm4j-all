package com.cm4j.test.syntax.inner_class;

/**
 * .this 和 new 对象的区别
 * @author yanghao
 *
 */
public class Test2 {

    private int num;

    public Test2() {
    }

    public Test2(int num) {
        this.num = num;
    }

    private class Inner {
        public Test2 getTest2() {
            // .this后，得到时创建该内部类时使用的外围类对象的引用，
            return Test2.this;
        }

        public Test2 newTest2() {
            // new则是创建了一个新的引用
            return new Test2();
        }
    }

    public static void main(String[] args) {
        Test2 test = new Test2(5);
        // 直接创建一个内部类对象，而不是通过外围类对象的方法来得到，可以使用.new关键字
        Test2.Inner inner = test.new Inner();
        Test2 test2 = inner.getTest2();
        Test2 test3 = inner.newTest2();
        System.out.println(test2.num);
        System.out.println(test3.num);
    }

}
