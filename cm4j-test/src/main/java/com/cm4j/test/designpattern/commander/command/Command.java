package com.cm4j.test.designpattern.commander.command;

import com.cm4j.test.designpattern.commander.group.CodeGroup;
import com.cm4j.test.designpattern.commander.group.Group;
import com.cm4j.test.designpattern.commander.group.RequirementGroup;

public abstract class Command {

    protected Group requirementGroup = new RequirementGroup();

    protected Group codeGroup = new CodeGroup();

    public abstract void execute();
    
    public abstract void undo();
}
