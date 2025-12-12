# solidJava

A Java playground project for exploring and learning various Java concepts and patterns.

## 📋 Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Build Commands](#build-commands)
- [Requirements](#requirements)

## 🎯 Overview

This project features:

- ✅ Clean, well-documented Java code
- ✅ Comprehensive JUnit 5 tests with AssertJ assertions
- ✅ Cucumber BDD tests for behavior verification
- ✅ Separate test suites that can be run independently

## 📁 Project Structure

```
solidJava/
├── src/
│   ├── main/java/           # Main source code
│   ├── test/java/           # JUnit tests
│   ├── cucumber/java/       # Cucumber step definitions
│   └── cucumber/resources/  # Feature files
├── build.gradle             # Gradle build configuration
└── settings.gradle          # Project settings
```

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Gradle 8.x (or use included wrapper)

### Building the Project

```bash
./gradlew build
```

## 🧪 Running Tests

### Run all tests (JUnit + Cucumber)
```bash
./gradlew check
```

### Run only JUnit tests
```bash
./gradlew unitTest
```

### Run only Cucumber tests
```bash
./gradlew cucumber
```

### Run tests with detailed output
```bash
./gradlew test --info
```

## 📊 Build Commands

| Command | Description |
|---------|-------------|
| `./gradlew build` | Compile and build the project |
| `./gradlew clean` | Clean build artifacts |
| `./gradlew test` | Run all tests |
| `./gradlew unitTest` | Run JUnit tests only |
| `./gradlew cucumber` | Run Cucumber tests only |
| `./gradlew check` | Run all verification tasks |

## 📦 Requirements

- **Java**: 21 (LTS)
- **Gradle**: 8.x or higher
- **JUnit**: 5.10.1
- **Cucumber**: 7.14.1
- **AssertJ**: 3.24.2

## 📄 License

This project is provided as-is for learning and exploration purposes.
