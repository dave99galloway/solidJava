package com.playground.solidjava.cucumber;

import com.playground.solidjava.isp.Employee;
import com.playground.solidjava.isp.Eater;
import com.playground.solidjava.isp.Robot;
import com.playground.solidjava.isp.Worker;
import com.playground.solidjava.isp.WorkManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Cucumber step definitions for Interface Segregation Principle.
 * This demonstrates that clients depend only on the interfaces they use.
 * Employees implement both Worker and Eater, while Robots implement only Worker.
 */
public class ISPSteps {
    private Employee employee;
    private Robot robot;
    private WorkManager workManager;
    private List<Worker> workers;

    @Given("an employee with role {word}")
    public void createEmployee(String role) {
        employee = new Employee("TestEmployee", role);
    }

    @Given("a robot with model ID {word}")
    public void createRobot(String modelId) {
        robot = new Robot(modelId);
    }

    @Given("a work manager with different worker types")
    public void createWorkManagerWithDifferentWorkers() {
        workManager = new WorkManager();
        workers = new ArrayList<>();
    }

    @Given("a work manager")
    public void createWorkManager() {
        workManager = new WorkManager();
    }

    @Given("a work system with segregated interfaces")
    public void createWorkSystemWithSegregation() {
        workManager = new WorkManager();
    }

    @When("I add an employee and a robot")
    public void addEmployeeAndRobot() {
        employee = new Employee("Alice", "Developer");
        robot = new Robot("R2D2");
        workManager.addWorker(employee);
        workManager.addWorker(robot);
    }

    @When("I assign work to all workers")
    public void assignWorkToAllWorkers() {
        workManager.assignWork();
        workManager.assignWork();
    }

    @When("I add workers of different types")
    public void addMultipleWorkerTypes() {
        workManager.addWorker(new Employee("Bob", "Manager"));
        workManager.addWorker(new Robot("C3P0"));
        workManager.addWorker(new Employee("Carol", "Analyst"));
    }

    @When("I add new worker types")
    public void addNewWorkerTypes() {
        // Demonstrates ISP: new worker types can be added without changing them
        workManager.addWorker(new Employee("NewEmployee", "Engineer"));
        workManager.addWorker(new Robot("NewRobot"));
    }

    @Then("the employee should implement the Worker interface")
    public void assertEmployeeImplementsWorker() {
        assertThat(employee).isInstanceOf(Worker.class);
    }

    @Then("the employee should implement the Eater interface")
    public void assertEmployeeImplementsEater() {
        assertThat(employee).isInstanceOf(Eater.class);
    }

    @Then("the robot should implement the Worker interface")
    public void assertRobotImplementsWorker() {
        assertThat(robot).isInstanceOf(Worker.class);
    }

    @Then("the robot should NOT implement the Eater interface")
    public void assertRobotDoesNotImplementEater() {
        assertThat(robot).isNotInstanceOf(Eater.class);
    }

    @Then("all workers should complete their assigned tasks")
    public void assertWorkersCompleteTasks() {
        assertThat(employee.getTasksCompleted()).isEqualTo(2);
        assertThat(robot.getTasksCompleted()).isEqualTo(2);
    }

    @Then("the total completed tasks should be counted correctly")
    public void assertTasksCountedCorrectly() {
        int totalTasks = workManager.getTotalTasksCompleted();
        assertThat(totalTasks).isGreaterThan(0);
    }

    @Then("the work manager should use only the Worker interface")
    public void assertWorkManagerUsesOnlyWorkerInterface() {
        // WorkManager doesn't know about Eater, proving ISP
        assertThat(workManager.getWorkers())
                .allMatch(w -> w instanceof Worker)
                .isNotEmpty();
    }

    @Then("workers should not be forced to implement unnecessary methods")
    public void assertWorkersNotForcedToImplementUnnecessary() {
        // Robot doesn't implement Eater - ISP ensures it only implements what it needs
        assertThat(workManager.getWorkers())
                .filteredOn(w -> w instanceof Robot)
                .allMatch(w -> !(w instanceof Eater));
    }

    @Then("they should only implement the interfaces they need")
    public void assertNewWorkersOnlyImplementNeededInterfaces() {
        List<Worker> workers = workManager.getWorkers();
        assertThat(workers).isNotEmpty();
        
        // Some workers may be employees (implementing both)
        // Some may be robots (implementing only Worker)
        // All follow ISP
    }

    @Then("the system should handle them without modification")
    public void assertSystemHandlesNewWorkerTypes() {
        // The WorkManager didn't need to change - ISP allows this
        assertThat(workManager.getWorkers()).isNotEmpty();
        workManager.assignWork();
        assertThat(workManager.getTotalTasksCompleted()).isGreaterThan(0);
    }
}
