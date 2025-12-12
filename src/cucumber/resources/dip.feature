Feature: Dependency Inversion Principle
  As a developer
  I want to verify that high-level modules depend on abstractions
  So that different implementations can be swapped without changing high-level code

  Scenario: UserService depends on Logger abstraction
    Given a user service with a mock logger
    When I register a user named john_doe
    Then the user should be registered
    And the logger should record the registration

  Scenario: UserService works with ConsoleLogger implementation
    Given a user service with console logger
    When I register a user named alice_smith
    Then the user registration should succeed

  Scenario: Different logger implementations can be swapped
    Given a user service with console logger
    And another user service with mock logger
    When I register users in both services
    Then both services should work correctly
    And loggers should handle messages appropriately

  Scenario: UserService handles invalid input gracefully
    Given a user service with a mock logger
    When I try to register a user with null name
    Then an exception should be thrown
    And the error should be logged

  Scenario: High-level module is independent of low-level implementations
    Given a user service
    When I swap different logger implementations
    Then the service should work with all implementations
    And the service logic should remain unchanged
