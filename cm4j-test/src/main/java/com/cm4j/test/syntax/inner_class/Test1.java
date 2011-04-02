package com.cm4j.test.syntax.inner_class;

/**
 * <pre>
 * 内部类能访问外围类的所有成员，不需任何特殊条件
 * 
 * 原理：
 * 用外围类创建内部类对象时，此内部类对象会秘密的捕获一个指向外围类的引用，于是，可以通过这个引用来访问外围类的成员。
 * 正是因为如此，我们创建内部类对象时，必须与外围类对象相关联。
 * 
 * <pre>
 * 
 * @author yanghao
 * 
 */
public class Test1 {

    private String str = "ABC";

    public class Contents {
        public void getStr() {
            System.out.println("Test1.str=" + str);
        }
    }

    public static void main(String[] args) {
        Test1 t1 = new Test1();
        t1.new Contents().getStr();
    }

}
