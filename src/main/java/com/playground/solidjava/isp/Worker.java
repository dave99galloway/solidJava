package com.playground.solidjava.isp;

/**
 * Interface Segregation Principle: Worker interface contains only work-related methods.
 * No client should be forced to depend on methods it doesn't use.
 */
public interface Worker {
    void work();

    String getRole();
}
