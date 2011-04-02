package com.cm4j.test.designpattern.commander.command;

public class AddRequirementCommandExtend extends Command {

    @Override
    public void execute() {
        super.requirementGroup.find();
        super.requirementGroup.add();
        // 多模块协调调用
        super.codeGroup.add();
        super.requirementGroup.plan();
    }

    @Override
    public void undo() {
        System.out.println("AddRequirementCommandExtend undo");
    }
}
