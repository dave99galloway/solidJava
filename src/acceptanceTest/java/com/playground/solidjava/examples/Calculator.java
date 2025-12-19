package com.playground.solidjava.examples;

// Simple Calculator implementation for demo
public class Calculator {
    private int firstNumber;
    private int secondNumber;
    private int result;

    public void enter(int number) {
        if (firstNumber == 0) {
            firstNumber = number;
        } else {
            secondNumber = number;
        }
    }

    public int add() {
        result = firstNumber + secondNumber;
        return result;
    }

    public int subtract() {
        result = firstNumber - secondNumber;
        return result;
    }

    public int getResult() {
        return result;
    }

    public int getDisplay() {
        return firstNumber;
    }

    public void reset() {
        firstNumber = 0;
        secondNumber = 0;
        result = 0;
    }
}