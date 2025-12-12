package com.playground.solidjava.isp;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface Segregation Principle: WorkManager depends only on the Worker interface.
 * It doesn't need to know about Eater or any other concerns.
 */
public class WorkManager {
    private final List<Worker> workers = new ArrayList<>();

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public void assignWork() {
        for (Worker worker : workers) {
            worker.work();
        }
    }

    public int getTotalTasksCompleted() {
        int total = 0;
        for (Worker worker : workers) {
            if (worker instanceof Employee emp) {
                total += emp.getTasksCompleted();
            } else if (worker instanceof Robot robot) {
                total += robot.getTasksCompleted();
            }
        }
        return total;
    }

    public List<Worker> getWorkers() {
        return new ArrayList<>(workers);
    }
}
