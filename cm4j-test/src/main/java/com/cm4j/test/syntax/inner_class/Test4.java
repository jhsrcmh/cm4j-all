package com.cm4j.test.syntax.inner_class;

/**
 * 匿名内部类
 * 
 * @author yanghao
 * 
 */
public class Test4 {

    private String size = "10m";

    /**
     * <pre>
     * 1.匿名内部类后面的分号不可缺少 
     * 2.方法体内的匿名内部类(即new 接口、抽象类)使用<b>外部参数</b>必须是final的(否则编译器报错)，
     * 但可直接使用外围类的成员变量
     * 3.抽象类是有构造器的，但接口没有，想模仿构造器，可以采用实例初始化{}
     * </pre>
     * 
     * @param num
     * @return
     */
    public Shape getShape(final int num) {
        return new Shape() {
            private String str ;
            {
                str = "java.eye";
            }
            public void paint() {
                System.out.println("painter paint() method,num:" + num + ",size:" + size);
                System.out.println("str:" + str);
            }
        };
    }

    /**
     * 必须final
     * 
     * @param num
     * @return
     */
    public Shape getShape2(final int num) {
        Shape shape = new Shape() {
            public void paint() {
                System.out.println("painter paint() method,num:" + num + ",size:" + size);
            }
        };
        return shape;
    }

    /**
     * 必须final
     * 
     * @param num
     * @return
     */
    public Shape getShape3(final int num) {
        class ShapImpl implements Shape {
            @Override
            public void paint() {
                System.out.println("painter paint() method,num:" + num + ",size:" + size);
            }
        }
        return new ShapImpl();
    }

    public interface Shape {
        public void paint();
    }

    public static void main(String[] args) {
        Test4 painter = new Test4();
        Shape shape = painter.getShape(3);
        shape.paint();
        Shape shape2 = painter.getShape2(4);
        shape2.paint();
        Shape shape3 = painter.getShape3(5);
        shape3.paint();
    }
}
