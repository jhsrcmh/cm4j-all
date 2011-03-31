package com.cm4j.test.designpattern.observer.eventlistener;

import java.util.EventObject;

/**
 * <pre>
 * 自定义事件(主要作用：标识、存放事件源和源对象无法传递的参数) 
 * 
 * Event构造函数需传入Source对象，这样EventListener调用时可获取到Source对象
 * 源对象无法传递的参数，也可以通过selfDefinedArgs传递
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class DemoEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    private Object[] selfDefinedArgs;

    /**
     * Event构造函数需传入Source对象，这样EventListener调用时可获取到Source对象
     * 
     * @param source
     */
    public DemoEvent(Object source) {
        super(source);
    }

    /**
     * 源对象无法传递的参数，也可以通过selfDefinedArgs传递
     * 
     * @param source
     * @param selfDefinedArgs
     */
    public DemoEvent(Object source, Object[] selfDefinedArgs) {
        this(source);
        this.selfDefinedArgs = selfDefinedArgs;
    }

    public Object[] getSelfDefinedArgs() {
        return selfDefinedArgs;
    }
}
