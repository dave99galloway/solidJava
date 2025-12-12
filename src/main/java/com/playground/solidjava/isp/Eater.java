package com.playground.solidjava.isp;

/**
 * Interface Segregation Principle: Separate interface for eating.
 * Not all workers need to eat at work (e.g., robots), so this is segregated.
 */
public interface Eater {
    void eat();

    int getLunchBreakMinutes();
}
