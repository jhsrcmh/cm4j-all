package com.cm4j.test.syntax.inner_class;

import com.cm4j.test.syntax.inner_class.Test6.Contents.InnerFour;

/**
 * <pre>
 * 为什么引入内部类？ 
 * a、内部类提供了某种进入外围类的窗户。 
 * b、也是最吸引人的原因，每个内部类都能独立地继承一个接口，而无论外围类是否已经继承了某个接口。
 * 想一个类继承2个类？ ==> 用内部类
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class Test6 {

    public interface One {
        public void inOne();
    }

    public interface Two {
        public void inTwo();
    }

    // 两个接口，用普通类就可实现多重继承
    public class CommonClass implements One, Two {
        public void inOne() {
            System.out.println("CommonClass inOne() method");
        }

        public void inTwo() {
            System.out.println("CommonClass inTwo() method");
        }
    }

    public abstract class Three {
        public abstract void inThree();
    }

    public abstract class Four {
        public abstract void inFour();
    }

    // 两个抽象类，使用普通类无法实现多重继承
    // 使用内部类可以实现
    public class Contents extends Three {
        public void inThree() {
            System.out.println("In Contents inThress() method");
        }

        public class InnerFour extends Four {
            public void inFour() {
                inThree();
                System.out.println("In Contents");
            }

        }
    }
    
    public static void main(String[] args) {
        Test6 t6 = new Test6();
        Contents contents = t6.new Contents();
        InnerFour innerFour = contents.new InnerFour();
        innerFour.inFour();
    }

}
