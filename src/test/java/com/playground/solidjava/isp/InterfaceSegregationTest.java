package com.playground.solidjava.isp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Interface Segregation Principle.
 * This test demonstrates that clients depend only on the methods they use.
 * Employees implement both Worker and Eater, while Robots implement only Worker.
 */
@DisplayName("Interface Segregation Principle Tests")
class InterfaceSegregationTest {
    private WorkManager workManager;

    @BeforeEach
    void setUp() {
        workManager = new WorkManager();
    }

    @Test
    @DisplayName("Employee should implement Worker interface")
    void employeeShouldImplementWorker() {
        // Arrange
        Employee employee = new Employee("Alice", "Developer");

        // Act
        employee.work();

        // Assert
        assertThat(employee).isInstanceOf(Worker.class);
        assertThat(employee.getTasksCompleted()).isEqualTo(1);
    }

    @Test
    @DisplayName("Employee should implement Eater interface")
    void employeeShouldImplementEater() {
        // Arrange
        Employee employee = new Employee("Bob", "Designer");

        // Act & Assert
        assertThat(employee).isInstanceOf(Eater.class);
        assertThat(employee.getLunchBreakMinutes()).isEqualTo(30);
    }

    @Test
    @DisplayName("Robot should implement Worker interface")
    void robotShouldImplementWorker() {
        // Arrange
        Robot robot = new Robot("R2D2");

        // Act
        robot.work();

        // Assert
        assertThat(robot).isInstanceOf(Worker.class);
        assertThat(robot.getTasksCompleted()).isEqualTo(1);
    }

    @Test
    @DisplayName("Robot should NOT implement Eater interface")
    void robotShouldNotImplementEater() {
        // Arrange
        Robot robot = new Robot("C3P0");

        // Act & Assert
        assertThat(robot).isNotInstanceOf(Eater.class);
    }

    @Test
    @DisplayName("WorkManager should work with both Employee and Robot")
    void workManagerShouldHandleMultipleWorkerTypes() {
        // Arrange
        Employee employee = new Employee("Charlie", "Manager");
        Robot robot = new Robot("BB8");

        workManager.addWorker(employee);
        workManager.addWorker(robot);

        // Act
        workManager.assignWork();
        workManager.assignWork();

        // Assert
        assertThat(workManager.getTotalTasksCompleted()).isEqualTo(4); // 2 tasks each
        assertThat(workManager.getWorkers()).hasSize(2);
    }

    @Test
    @DisplayName("WorkManager depends only on Worker interface")
    void workManagerDependsOnlyOnWorkerInterface() {
        // Arrange
        Employee employee = new Employee("Diana", "Analyst");

        workManager.addWorker(employee);

        // Act
        workManager.assignWork();

        // Assert
        // WorkManager should not know about Eater interface
        assertThat(workManager.getWorkers())
                .filteredOn(worker -> worker instanceof Worker)
                .hasSize(1);
    }

    /**
     * Test helper: Validates that a worker correctly implements the Worker interface.
     * Demonstrates ISP in testing: verifies only the Worker contract.
     */
    private void assertWorkerIsValid(Worker worker) {
        assertThat(worker).isNotNull();
        assertThat(worker.getRole()).isNotBlank();
        
        // Execute work
        if (worker instanceof Employee emp) {
            int initialTasks = emp.getTasksCompleted();
            emp.work();
            assertThat(emp.getTasksCompleted()).isEqualTo(initialTasks + 1);
        } else if (worker instanceof Robot robot) {
            int initialTasks = robot.getTasksCompleted();
            robot.work();
            assertThat(robot.getTasksCompleted()).isEqualTo(initialTasks + 1);
        }
    }

    /**
     * Test helper: Validates that a worker is correctly used in a segregated manner.
     * Only depends on the Worker interface, not implementation details.
     */
    private void assertWorkerCanBeAssignedWork(Worker worker) {
        worker.work();
        assertThat(worker.getRole()).isNotEmpty();
    }
}
