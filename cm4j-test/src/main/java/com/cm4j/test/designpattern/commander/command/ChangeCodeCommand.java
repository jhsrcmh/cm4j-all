package com.cm4j.test.designpattern.commander.command;

public class ChangeCodeCommand extends Command {

    @Override
    public void execute() {
        super.codeGroup.find();
        super.codeGroup.change();
        super.codeGroup.plan();
    }

    @Override
    public void undo() {
        System.out.println("ChangeCodeCommand undo");
    }
}
