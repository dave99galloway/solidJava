Feature: Single Responsibility Principle
  As a developer
  I want to verify that classes have a single responsibility
  So that they are focused, maintainable, and change for only one reason

  Scenario: Payment processor delegates validation to separate class
    Given a payment processor is initialized
    When I process a payment with amount 100.00 and currency USD
    Then the payment should be accepted

  Scenario: Payment processor delegates recording to separate class
    Given a payment processor is initialized
    When I process a payment with amount 50.00 and currency EUR
    Then the payment should be recorded

  Scenario: Validation class rejects invalid amounts
    Given a payment validator
    When I validate a payment with amount 0 and currency USD
    Then the validation should fail

  Scenario: Validation class rejects invalid currency
    Given a payment validator
    When I validate a payment with amount 100.00 and currency INVALID
    Then the validation should fail
