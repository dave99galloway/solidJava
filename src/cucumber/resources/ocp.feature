Feature: Open/Closed Principle
  As a developer
  I want to verify that classes are open for extension but closed for modification
  So that new discount types can be added without changing existing code

  Scenario: Calculator applies percentage discount
    Given a price calculator
    When I add a 10 percent discount
    And I calculate the final price for 100.00
    Then the final price should be 90.00

  Scenario: Calculator applies fixed discount
    Given a price calculator
    When I add a 15.00 fixed discount
    And I calculate the final price for 100.00
    Then the final price should be 85.00

  Scenario: Calculator applies multiple discounts in sequence
    Given a price calculator
    When I add a 10 percent discount
    And I add a 5.00 fixed discount
    And I calculate the final price for 100.00
    Then the final price should be 85.00

  Scenario: Fixed discount prevents negative price
    Given a price calculator
    When I add a 150.00 fixed discount
    And I calculate the final price for 100.00
    Then the final price should be 0.00

  Scenario: New discount type can be added without modifying calculator
    Given a price calculator with extensible design
    When I add a new custom discount type
    And I calculate the final price for 100.00
    Then the calculator should support the new discount without modification
