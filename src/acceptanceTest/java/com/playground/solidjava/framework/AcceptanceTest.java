package com.playground.solidjava.framework;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for acceptance tests providing fluent DSL syntax with
 * Given/When/Then/And.
 * 
 * Tests use the Command pattern to encapsulate step behavior and logging.
 * Commands are passed to the fluent keyword methods, avoiding string parsing
 * overhead.
 * 
 * Example:
 * 
 * <pre>
 * public class OrderAcceptanceTest extends AcceptanceTest {
 *     private OrderService orderService;
 * 
 *     &#64;Test
 *     void shouldProcessValidOrder() throws Exception {
 *         given(new GivenOrderSetup())
 *                 .and(new AndAddItem(item))
 *                 .when(new WhenProcessOrder())
 *                 .then(new ThenOrderConfirmed());
 *     }
 * 
 *     private class GivenOrderSetup implements StepCommand {
 *         &#64;Override
 *         public void execute(StepContext context) {
 *             orderService = new OrderService();
 *         }
 * 
 *         @Override
 *         public String description() {
 *             return "an order system";
 *         }
 *     }
 * }
 * </pre>
 */
public abstract class AcceptanceTest {
    protected static final Logger logger = LoggerFactory.getLogger("AcceptanceTest");
    protected final StepContext context = new StepContext();

    @BeforeEach
    void resetContext() {
        context.reset();
    }

    protected AcceptanceTest executeStepCommand(StepCommand command) {
        try {
            logKeyword(command.keyword(), command.description());
            command.execute(context);
        } catch (Exception e) {
            throw new StepExecutionException(command, e);
        }
        return this;
    }

    /**
     * Execute a GIVEN step command and log it.
     */
    public AcceptanceTest given(StepCommand command) {
        return executeStepCommand(command);
    }

    /**
     * Execute a WHEN step command and log it.
     */
    public AcceptanceTest when(StepCommand command) {
        return executeStepCommand(command);
    }

    /**
     * Execute a THEN step command and log it.
     */
    public AcceptanceTest then(StepCommand command) {
        return executeStepCommand(command);
    }

    /**
     * Execute an AND step command and log it.
     */
    public AcceptanceTest and(StepCommand command) {
        return executeStepCommand(command);
    }

    /**
     * Execute a BUT step command and log it.
     */
    public AcceptanceTest but(StepCommand command) {
        return executeStepCommand(command);
    }

    /**
     * Log a keyword step at INFO level.
     */
    private void logKeyword(StepKeyword keyword, String description) {
        logger.info("[{}] {}", keyword.name(), description);
    }
}
