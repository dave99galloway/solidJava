package com.playground.solidjava.isp;

/**
 * Interface Segregation Principle: Robot implements only Worker.
 * It does NOT implement Eater, because robots don't eat.
 * This demonstrates that interfaces are segregated by concern.
 */
public class Robot implements Worker {
    private final String modelId;
    private int tasksCompleted = 0;

    public Robot(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public void work() {
        tasksCompleted++;
    }

    @Override
    public String getRole() {
        return "Automated Worker";
    }

    public String getModelId() {
        return modelId;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }
}
