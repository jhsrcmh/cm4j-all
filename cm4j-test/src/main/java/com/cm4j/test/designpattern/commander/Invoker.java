package com.cm4j.test.designpattern.commander;

import com.cm4j.test.designpattern.commander.command.Command;

/**
 * 这个类有什么用？
 * 
 * Involer可以管理多个Command（一个Commad不需使用命令模式），并在Involer中判断Command可用否，成功否等等，
 * 并可实现记录，重做，撤销等功能，
 * 所以说正是因为有了Involer这一层才使命令的管理脱离客户端，毕竟客户端只需发布命令，而没有义务判断和管理命令。
 * 
 * @author yanghao
 *
 */
public class Invoker {
    
    private Command command;
    
    public void setCommand(Command command) {
        this.command = command;
    }
    
    public void doAction() {
        this.command.execute();
    }
    
    public void redo() {
        this.doAction();
    }
    
    public void undo() {
        this.command.undo();
    }
    
}
