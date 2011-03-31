package com.cm4j.test.designpattern.commander.command;

public class AddRequirementCommand extends Command {

    @Override
    public void execute() {
        super.requirementGroup.find();
        super.requirementGroup.add();
        super.requirementGroup.plan();
    }

    @Override
    public void undo() {
        System.out.println("AddRequirementCommand undo");
    }
}
