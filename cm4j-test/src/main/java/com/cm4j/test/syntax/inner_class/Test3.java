package com.cm4j.test.syntax.inner_class;

/**
 * private 内部类能屏蔽细节，阻止外部不期望的访问
 * 
 * @author yanghao
 * 
 */
public class Test3 {

    // 内部类是private的，除了它的外围类Test3以外，没人能访问
    private class InnerShape implements Shape {
        public void paint() {
            System.out.println("painter paint() method");
        }
    }

    public Shape getShape() {
        return new InnerShape();
    }

    public interface Shape {
        public void paint();
    }

    public static void main(String[] args) {
        // 只有通过Test3才能访问到private内部类
        // 通过这种方式可以完全阻止任何依赖于类型的编码，并完全隐藏实现的细节。
        Test3 t3 = new Test3();
        Shape shape = t3.getShape();
        shape.paint();
    }
}
