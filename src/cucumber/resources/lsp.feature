Feature: Liskov Substitution Principle
  As a developer
  I want to verify that shape subclasses honor the shape contract
  So that any shape can be used interchangeably

  Scenario: Rectangle correctly implements shape contract
    Given a rectangle with width 5 and height 3
    Then the rectangle should have area 15
    And the rectangle should have perimeter 16
    And the rectangle should be valid

  Scenario: Circle correctly implements shape contract
    Given a circle with radius 5
    Then the circle should have valid area
    And the circle should have valid perimeter
    And the circle should be valid

  Scenario: All shape types can be used interchangeably
    Given a list of different shape types
    When I use each shape through the shape interface
    Then all shapes should correctly implement their contract

  Scenario: Substitution works correctly with polymorphism
    Given multiple shapes of different types
    Then each shape should correctly calculate its measurements
    And all shapes should satisfy the shape contract

  Scenario: Invalid dimensions throw exceptions
    When I try to create a rectangle with zero width
    Then an exception should be thrown
    And I should not create a circle with negative radius
