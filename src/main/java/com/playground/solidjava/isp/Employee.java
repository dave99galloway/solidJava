package com.playground.solidjava.isp;

/**
 * Interface Segregation Principle: Employee implements both Worker and Eater.
 * It only depends on the methods it actually uses.
 */
public class Employee implements Worker, Eater {
    private final String name;
    private final String role;
    private int tasksCompleted = 0;

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    @Override
    public void work() {
        tasksCompleted++;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void eat() {
        // Employee takes a lunch break
    }

    @Override
    public int getLunchBreakMinutes() {
        return 30;
    }

    public String getName() {
        return name;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }
}
