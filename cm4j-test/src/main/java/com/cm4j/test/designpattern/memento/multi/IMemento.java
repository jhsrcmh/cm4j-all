package com.cm4j.test.designpattern.memento.multi;

/**
 * 考虑对象的安全问题 - 双接口设计
 * 
 * 一个是业务的正常接口，实现必要的业务逻辑，叫做宽接口；<br />
 * 一个是空接口，什么方法也没有，目的是提供给子系统外的模块访问，叫做窄接口。
 * 
 * 本示例中：Memento对象的方法只在Boy对象内进行调用，而careTaker只是做保存恢复作用，因此用IMemento对象代表下即可
 * 
 * @author yang.hao
 * @since 2011-5-20 上午10:35:10
 * 
 */
public interface IMemento {

}
