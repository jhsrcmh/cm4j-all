package com.cm4j.test.designpattern.commander.command;

public class RollbackCodeCommand extends Command{

    @Override
    public void execute() {
        super.codeGroup.rollback();
    }

    @Override
    public void undo() {
        System.out.println("RollbackCodeCommand undo");
    }
}
