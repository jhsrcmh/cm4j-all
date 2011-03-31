package com.cm4j.test.designpattern.iterator;

/**
 * <pre>
 * 迭代器
 * 
 * 实质：对集合内的元素进行循环读取
 * jdk的 ArrayList的迭代器 是将 迭代器 作为内部类在ArrayList中，这样迭代器可以调用ArrayList的方法进行操作
 * 
 * 本例中是在IProject中维护了一个ArrayList，迭代器就是对这个ArrayList进行操作
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class Client {
    public static void main(String[] args) {
        IProject project = new Project();
        project.add("Pa", 10, 10000);
        project.add("Pb", 20, 20000);
        project.add("Pc", 30, 30000);
        project.add("Pd", 40, 40000);

        IProjectIterator<IProject> projectIterator = project.iterator();
        while (projectIterator.hasNext()) {
            IProject iProject = (IProject) projectIterator.next();
            System.out.println(iProject.getProjectInfo());
        }
    }
}
