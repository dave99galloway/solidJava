package com.playground.solidjava.framework;

/**
 * BDD step keywords for acceptance tests.
 */
public enum StepKeyword {
    GIVEN("[GIVEN]"),
    WHEN("[WHEN]"),
    THEN("[THEN]"),
    AND("[AND]"),
    BUT("[BUT]");

    private final String label;

    StepKeyword(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
