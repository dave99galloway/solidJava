# solidJava

A comprehensive Java playground project for learning and demonstrating SOLID principles with application code, unit tests, and Cucumber BDD tests.

## 📋 Table of Contents

- [Overview](#overview)
- [SOLID Principles](#solid-principles)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Build Commands](#build-commands)
- [Requirements](#requirements)

## 🎯 Overview

This project demonstrates all five SOLID principles with:

- ✅ **20 carefully designed classes** implementing SOLID principles
- ✅ **34 JUnit 5 tests** with AssertJ assertions, following SOLID practices
- ✅ **50 Cucumber BDD scenarios** verifying SOLID implementation
- ✅ **Detailed documentation** with code examples and explanations
- ✅ **Extensible examples** showing how SOLID enables feature addition
- ✅ **100% passing tests** (84 total: 34 unit + 50 Cucumber)

## 🏗️ SOLID Principles

This project provides comprehensive demonstrations of all five SOLID principles:

| Principle                     | Location            | Description                                                   |
| ----------------------------- | ------------------- | ------------------------------------------------------------- |
| **S** - Single Responsibility | `src/main/.../srp/` | Payment processing system with separated concerns             |
| **O** - Open/Closed           | `src/main/.../ocp/` | Extensible discount system without modifying existing code    |
| **L** - Liskov Substitution   | `src/main/.../lsp/` | Shape hierarchy properly substitutable for base type          |
| **I** - Interface Segregation | `src/main/.../isp/` | Segregated Worker/Eater interfaces for different entity types |
| **D** - Dependency Inversion  | `src/main/.../dip/` | Logger abstraction with swappable implementations             |

**→ See [SOLID_PRINCIPLES.md](SOLID_PRINCIPLES.md) for detailed documentation, examples, and explanations.**

## 📁 Project Structure

```
solidJava/
├── src/
│   ├── main/java/com/playground/solidjava/
│   │   ├── srp/          # Single Responsibility Principle
│   │   ├── ocp/          # Open/Closed Principle
│   │   ├── lsp/          # Liskov Substitution Principle
│   │   ├── isp/          # Interface Segregation Principle
│   │   └── dip/          # Dependency Inversion Principle
│   ├── test/java/        # JUnit 5 tests (34 tests)
│   ├── cucumber/
│   │   ├── java/         # Cucumber step definitions
│   │   └── resources/    # Feature files (50 scenarios)
├── build.gradle          # Gradle build configuration
├── settings.gradle       # Project settings
├── README.md            # This file
└── SOLID_PRINCIPLES.md  # Detailed SOLID documentation
```

## 🚀 Getting Started

### Prerequisites

- Java 21 (LTS)
- Gradle 8.x (or use included wrapper)

### Building the Project

```bash
./gradlew build
```

### Exploring the Code

1. **Start with SOLID documentation:**

   ```bash
   cat SOLID_PRINCIPLES.md
   ```

2. **Review principle implementations:**

   - Single Responsibility: `src/main/java/.../srp/`
   - Open/Closed: `src/main/java/.../ocp/`
   - Liskov Substitution: `src/main/java/.../lsp/`
   - Interface Segregation: `src/main/java/.../isp/`
   - Dependency Inversion: `src/main/java/.../dip/`

3. **Examine test implementations:**
   - Unit tests: `src/test/java/com/playground/solidjava/`
   - Cucumber scenarios: `src/cucumber/resources/`

## 🧪 Running Tests

### Run all tests (JUnit + Cucumber)

```bash
./gradlew test cucumber
```

### Run only JUnit tests

```bash
./gradlew test
```

### Run only Cucumber tests

```bash
./gradlew cucumber
```

### Run tests with detailed output

```bash
./gradlew test --info
```

### View Test Reports

After running tests:

- **JUnit Report:** `build/reports/tests/test/index.html`
- **Cucumber Report:** `build/reports/tests/cucumber/index.html`

## 📊 Build Commands

| Command                   | Description                           |
| ------------------------- | ------------------------------------- |
| `./gradlew build`         | Compile and build the project         |
| `./gradlew clean`         | Clean build artifacts                 |
| `./gradlew test`          | Run JUnit tests (34 tests)            |
| `./gradlew cucumber`      | Run Cucumber BDD tests (50 scenarios) |
| `./gradlew test cucumber` | Run all tests                         |
| `./gradlew check`         | Run all verification tasks            |

## 🧠 Learning Resources

### Understanding SOLID Principles

Each principle in this project includes:

- **Implementation classes** with clear separation of concerns
- **Unit tests** demonstrating correct behavior
- **Cucumber scenarios** verifying the principle in action
- **Detailed comments** explaining the principle

**Start here:** [SOLID_PRINCIPLES.md](SOLID_PRINCIPLES.md)

### Example: Adding a New Feature

The Open/Closed Principle example shows how to add a new discount type without modifying existing code:

1. Create a new class implementing `Discount` interface
2. Add it to the `PriceCalculator`
3. Existing code remains unchanged ✅

This demonstrates extensibility in action!

## 📦 Requirements

- **Java**: 21 (LTS)
- **Gradle**: 8.x or higher
- **JUnit**: 5.10.1
- **Cucumber**: 7.14.1
- **AssertJ**: 3.24.2

## 📄 License

This project is provided as-is for learning and exploration purposes.
