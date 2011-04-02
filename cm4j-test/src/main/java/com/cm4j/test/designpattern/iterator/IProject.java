package com.cm4j.test.designpattern.iterator;

public interface IProject {

    String getProjectInfo();

    void add(String name, int num, int cost);

    IProjectIterator<IProject> iterator();
}
