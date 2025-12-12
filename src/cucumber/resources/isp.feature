Feature: Interface Segregation Principle
  As a developer
  I want to verify that clients depend only on interfaces they use
  So that workers of different types can be used without implementing unnecessary methods

  Scenario: Employee implements both Worker and Eater interfaces
    Given an employee with role Developer
    Then the employee should implement the Worker interface
    And the employee should implement the Eater interface

  Scenario: Robot implements only Worker interface
    Given a robot with model ID R2D2
    Then the robot should implement the Worker interface
    And the robot should NOT implement the Eater interface

  Scenario: WorkManager works with both employee and robot
    Given a work manager with different worker types
    When I add an employee and a robot
    And I assign work to all workers
    Then all workers should complete their assigned tasks
    And the total completed tasks should be counted correctly

  Scenario: Clients depend only on methods they use
    Given a work manager
    When I add workers of different types
    Then the work manager should use only the Worker interface
    And workers should not be forced to implement unnecessary methods

  Scenario: Interface segregation enables flexibility
    Given a work system with segregated interfaces
    When I add new worker types
    Then they should only implement the interfaces they need
    And the system should handle them without modification
