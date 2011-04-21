package com.cm4j.test.designpattern.iterator;

import java.util.ArrayList;

public class Project implements IProject {

    // 定义项目列表，所有项目都放这里
    private ArrayList<IProject> projectList = new ArrayList<IProject>();

    private String name = "";

    private int num = 0;

    private int cost = 0;
    
    public Project() {
    }

    public Project(String name, int num, int cost) {
        super();
        this.name = name;
        this.num = num;
        this.cost = cost;
    }

    @Override
    public String getProjectInfo() {
        return "name:" + this.name + ",num:" + this.num + ",cost:" + this.cost;
    }

    @Override
    public void add(String name, int num, int cost) {
        this.projectList.add(new Project(name, num, cost));
    }

    @Override
    public IProjectIterator<IProject> iterator() {
        return new ProjectIterator(this.projectList);
    }

}
