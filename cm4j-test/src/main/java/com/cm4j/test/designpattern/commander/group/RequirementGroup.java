package com.cm4j.test.designpattern.commander.group;

public class RequirementGroup extends Group {

    @Override
    public void find() {
        System.out.println("requirementGroup:find()");
    }

    @Override
    public void add() {
        System.out.println("requirementGroup:add()");
    }

    @Override
    public void delete() {
        System.out.println("requirementGroup:delete()");
    }
    
    @Override
    public void change() {
        System.out.println("requirementGroup:change()");
    }

    @Override
    public void plan() {
        System.out.println("requirementGroup:plan()");
    }

}
