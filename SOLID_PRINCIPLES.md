# SOLID Principles Implementation & Tests

This project demonstrates all five SOLID principles with comprehensive application code, unit tests, and Cucumber BDD tests.

## Overview

Each SOLID principle is demonstrated with:

- **Application code** implementing the principle
- **Unit tests** using AssertJ and following SOLID practices
- **Cucumber BDD tests** that verify the principles are correctly implemented
- **Detailed comments** explaining how each principle is applied

---

## 1. Single Responsibility Principle (SRP)

**Location:** `src/main/java/com/playground/solidjava/srp/`

### Classes

- **`PaymentProcessor`** - Orchestrates payment processing (ONE responsibility: coordinate payment flow)
- **`PaymentValidator`** - Validates payments (ONE responsibility: validate payment data)
- **`PaymentRecorder`** - Records transactions (ONE responsibility: log payment records)
- **`Payment`** - Represents a payment transaction (ONE responsibility: hold payment data)

### Key Concept

Each class has a single reason to change:

- `PaymentProcessor` changes only if the orchestration logic changes
- `PaymentValidator` changes only if validation rules change
- `PaymentRecorder` changes only if recording mechanism changes

### Tests

- **Unit Tests:** [src/test/java/.../srp/PaymentProcessorTest.java](src/test/java/com/playground/solidjava/srp/PaymentProcessorTest.java)
  - Tests focus on one concern per test
  - Helper methods encapsulate specific validation logic
- **Cucumber Tests:** [src/cucumber/resources/srp.feature](src/cucumber/resources/srp.feature)
  - Verifies each class has clear responsibilities
  - Tests delegate validation to appropriate classes

---

## 2. Open/Closed Principle (OCP)

**Location:** `src/main/java/com/playground/solidjava/ocp/`

### Classes

- **`Discount`** (Interface) - Defines contract for discounts (OPEN for extension)
- **`PercentageDiscount`** - Implements percentage-based discounts (CLOSED for modification)
- **`FixedDiscount`** - Implements fixed-amount discounts (CLOSED for modification)
- **`PriceCalculator`** - Applies discounts without knowing their implementation (CLOSED for modification)

### Key Concept

Classes are:

- **CLOSED for modification:** `PriceCalculator` doesn't change when new discount types are added
- **OPEN for extension:** New discount types can be added by implementing the `Discount` interface

The calculator uses polymorphism to work with any `Discount` implementation without modification.

### Tests

- **Unit Tests:** [src/test/java/.../ocp/PriceCalculatorTest.java](src/test/java/com/playground/solidjava/ocp/PriceCalculatorTest.java)
  - Tests new discount types without modifying test code
  - Demonstrates extension through polymorphism
- **Cucumber Tests:** [src/cucumber/resources/ocp.feature](src/cucumber/resources/ocp.feature)
  - Shows calculator applying multiple discount types
  - Verifies new discount types work without modifying calculator

---

## 3. Liskov Substitution Principle (LSP)

**Location:** `src/main/java/com/playground/solidjava/lsp/`

### Classes

- **`Shape`** (Abstract) - Defines shape contract
- **`Rectangle`** - Implements shape for rectangles (honors contract exactly)
- **`Circle`** - Implements shape for circles (honors contract exactly)

### Key Concept

Subtypes must be substitutable for their base types:

- Both `Rectangle` and `Circle` can be used anywhere `Shape` is expected
- Both correctly implement `getArea()` and `getPerimeter()`
- Both satisfy the `isValid()` contract

A `Shape` variable can hold either a `Rectangle` or `Circle` and work identically.

### Tests

- **Unit Tests:** [src/test/java/.../lsp/ShapeTest.java](src/test/java/com/playground/solidjava/lsp/ShapeTest.java)
  - Tests that all shapes honor the shape contract
  - Verifies polymorphic behavior works correctly
- **Cucumber Tests:** [src/cucumber/resources/lsp.feature](src/cucumber/resources/lsp.feature)
  - Uses shapes interchangeably through the Shape interface
  - Verifies all shapes satisfy the contract

---

## 4. Interface Segregation Principle (ISP)

**Location:** `src/main/java/com/playground/solidjava/isp/`

### Classes & Interfaces

- **`Worker`** (Interface) - Methods only for working (`work()`, `getRole()`)
- **`Eater`** (Interface) - Methods only for eating (`eat()`, `getLunchBreakMinutes()`)
- **`Employee`** - Implements BOTH `Worker` and `Eater` (humans work and eat)
- **`Robot`** - Implements ONLY `Worker` (robots work but don't eat)
- **`WorkManager`** - Depends ONLY on `Worker` interface

### Key Concept

Interfaces are segregated by concern:

- Clients depend only on methods they use
- `Robot` is NOT forced to implement `Eater` (not relevant for robots)
- `Employee` implements both because it needs both
- `WorkManager` doesn't know about `Eater` and doesn't need to

### Tests

- **Unit Tests:** [src/test/java/.../isp/InterfaceSegregationTest.java](src/test/java/com/playground/solidjava/isp/InterfaceSegregationTest.java)
  - Verifies workers implement only needed interfaces
  - Tests that clients depend only on relevant methods
- **Cucumber Tests:** [src/cucumber/resources/isp.feature](src/cucumber/resources/isp.feature)
  - Shows robots implementing Worker but not Eater
  - Verifies system works with segregated interfaces

---

## 5. Dependency Inversion Principle (DIP)

**Location:** `src/main/java/com/playground/solidjava/dip/`

### Classes & Interfaces

- **`Logger`** (Interface) - Abstraction for logging (high-level concept)
- **`ConsoleLogger`** - Logs to console (low-level implementation)
- **`FileLogger`** - Logs to file (alternative low-level implementation)
- **`UserService`** - Depends on `Logger` abstraction, not concrete implementations

### Key Concept

High-level and low-level modules depend on abstractions:

- `UserService` depends on `Logger` interface
- `UserService` doesn't know about `ConsoleLogger` or `FileLogger`
- Any `Logger` implementation can be injected
- New logger types can be added without changing `UserService`

The dependency flows UPWARD to abstractions, not downward to concrete classes.

### Tests

- **Unit Tests:** [src/test/java/.../dip/DependencyInversionTest.java](src/test/java/com/playground/solidjava/dip/DependencyInversionTest.java)
  - Creates mock loggers for testing
  - Verifies service works with different logger implementations
  - Shows loggers can be swapped without changing service
- **Cucumber Tests:** [src/cucumber/resources/dip.feature](src/cucumber/resources/dip.feature)
  - Tests service with different logger implementations
  - Verifies high-level module is independent of low-level details

---

## Test Coverage

### Unit Tests (34 tests)

All unit tests use:

- **AssertJ** fluent assertions for clarity
- **SOLID principles in test code** itself:
  - SRP: Tests focus on one concern
  - OCP: Tests extensible through helper methods
  - ISP: Test dependencies are segregated
  - DIP: Tests depend on abstractions (interfaces)

### Cucumber BDD Tests (50 scenarios)

All Cucumber tests:

- **Follow BDD principles** with Given/When/Then structure
- **Verify SOLID implementation** through business-focused scenarios
- **Apply SOLID in step definitions**:
  - SRP: Each step class has one purpose
  - OCP: New step types don't modify existing steps
  - ISP: Step classes only implement needed interfaces
  - DIP: Steps depend on abstractions where applicable

---

## Running the Tests

### Run Unit Tests Only

```bash
./gradlew test
```

### Run Cucumber Tests Only

```bash
./gradlew cucumber
```

### Run All Tests

```bash
./gradlew test cucumber
```

### View Test Reports

After running tests, view the reports at:

- **Unit Tests:** `build/reports/tests/test/index.html`
- **Cucumber Tests:** `build/reports/tests/cucumber/index.html`

---

## Code Examples

### SRP Example

```java
// PaymentProcessor: Orchestrates payment flow (one responsibility)
public boolean processPayment(Payment payment) {
    if (!validator.isValid(payment)) {  // Delegates validation
        return false;
    }
    boolean success = executeTransaction(payment);
    if (success) {
        recorder.recordSuccessfulPayment(payment);  // Delegates recording
    }
    return success;
}
```

### OCP Example

```java
// PriceCalculator is CLOSED for modification but OPEN for extension
public BigDecimal calculateFinalPrice(BigDecimal basePrice) {
    BigDecimal result = basePrice;
    for (Discount discount : discounts) {  // Works with any Discount
        result = discount.apply(result);
    }
    return result;
}
// New discount types work without modifying this code!
```

### LSP Example

```java
// Both Rectangle and Circle can be used as Shape
public void validateShapes(List<Shape> shapes) {
    for (Shape shape : shapes) {  // Works with any shape
        if (shape.isValid()) {
            BigDecimal area = shape.getArea();  // Contract honored by all
        }
    }
}
```

### ISP Example

```java
// Robot only implements Worker, not Eater
public class Robot implements Worker {
    @Override
    public void work() { /* ... */ }
    // No eat() method needed!
}

// WorkManager only depends on Worker
public class WorkManager {
    private List<Worker> workers;  // ISP: depends only on what it uses
}
```

### DIP Example

```java
// UserService depends on Logger abstraction, not concrete implementations
public class UserService {
    private final Logger logger;  // Depends on interface, not implementation

    public UserService(Logger logger) {
        this.logger = logger;  // Any Logger implementation works!
    }
}
```

---

## Key Takeaways

1. **SOLID principles make code maintainable** - Each class has one reason to change
2. **SOLID enables extension** - Add features without modifying existing code
3. **SOLID improves testability** - Segregated concerns are easier to test
4. **SOLID supports dependency injection** - Abstractions enable flexible composition
5. **SOLID principles apply to tests too** - Test code should follow SOLID principles

---

## Structure

```
src/main/java/com/playground/solidjava/
├── srp/          (Single Responsibility)
│   ├── Payment.java
│   ├── PaymentProcessor.java
│   ├── PaymentValidator.java
│   └── PaymentRecorder.java
├── ocp/          (Open/Closed)
│   ├── Discount.java
│   ├── PercentageDiscount.java
│   ├── FixedDiscount.java
│   └── PriceCalculator.java
├── lsp/          (Liskov Substitution)
│   ├── Shape.java
│   ├── Rectangle.java
│   └── Circle.java
├── isp/          (Interface Segregation)
│   ├── Worker.java
│   ├── Eater.java
│   ├── Employee.java
│   ├── Robot.java
│   └── WorkManager.java
└── dip/          (Dependency Inversion)
    ├── Logger.java
    ├── ConsoleLogger.java
    ├── FileLogger.java
    └── UserService.java

src/test/java/com/playground/solidjava/
├── srp/
│   └── PaymentProcessorTest.java
├── ocp/
│   └── PriceCalculatorTest.java
├── lsp/
│   └── ShapeTest.java
├── isp/
│   └── InterfaceSegregationTest.java
└── dip/
    └── DependencyInversionTest.java

src/cucumber/java/com/playground/solidjava/cucumber/
├── SRPSteps.java
├── OCPSteps.java
├── LSPSteps.java
├── ISPSteps.java
└── DIPSteps.java

src/cucumber/resources/
├── srp.feature
├── ocp.feature
├── lsp.feature
├── isp.feature
└── dip.feature
```
