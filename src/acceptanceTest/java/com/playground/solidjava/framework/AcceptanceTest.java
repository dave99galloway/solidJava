package com.playground.solidjava.framework;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for acceptance tests providing fluent DSL syntax with
 * Given/When/Then/And/But.
 * 
 * Tests use the Command pattern to encapsulate step behavior and logging.
 * Step actions are passed to the fluent keyword methods, avoiding string
 * parsing
 * overhead. The keyword is implicit from which method is called.
 * 
 * Example:
 * 
 * <pre>
 * public class OrderAcceptanceTest extends AcceptanceTest {
 *     private OrderService orderService;
 * 
 *     &#64;Test
 *     void shouldProcessValidOrder() throws Exception {
 *         given(new StepAction() {
 *             &#64;Override
 *             public void execute(StepContext context) {
 *                 orderService = new OrderService();
 *             }
 * 
 *             &#64;Override
 *             public String description() {
 *                 return "an order system";
 *             }
 *         })
 *                 .when(new StepAction() {
 *                     &#64;Override
 *                     public void execute(StepContext context) {
 *                         orderService.process();
 *                     }
 * 
 *                     &#64;Override
 *                     public String description() {
 *                         return "we process the order";
 *                     }
 *                 })
 *                 .then(new StepAction() {
 *                     &#64;Override
 *                     public void execute(StepContext context) {
 *                         assertThat(orderService.isProcessed()).isTrue();
 *                     }
 * 
 *                     &#64;Override
 *                     public String description() {
 *                         return "the order is confirmed";
 *                     }
 *                 });
 *     }
 * }
 * </pre>
 */
public abstract class AcceptanceTest {
    protected static final Logger logger = LoggerFactory.getLogger(AcceptanceTest.class.getName());
    protected final StepContext context = new StepContext();

    @BeforeEach
    void resetContext() {
        context.reset();
    }

    protected AcceptanceTest executeStep(StepKeyword keyword, StepAction action) {
        try {
            logKeyword(keyword, action.description());
            action.execute(context);
        } catch (Exception e) {
            throw new StepExecutionException(keyword, action, e);
        }
        return this;
    }

    /**
     * Execute a GIVEN step action and log it.
     */
    public AcceptanceTest given(StepAction action) {
        return executeStep(StepKeyword.GIVEN, action);
    }

    /**
     * Execute a GIVEN step with inline logic.
     */
    public AcceptanceTest given(Step step, String description) {
        return executeStep(StepKeyword.GIVEN, wrapStep(step, description));
    }

    /**
     * Execute a WHEN step action and log it.
     */
    public AcceptanceTest when(StepAction action) {
        return executeStep(StepKeyword.WHEN, action);
    }

    /**
     * Execute a WHEN step with inline logic.
     */
    public AcceptanceTest when(Step step, String description) {
        return executeStep(StepKeyword.WHEN, wrapStep(step, description));
    }

    /**
     * Execute a THEN step action and log it.
     */
    public AcceptanceTest then(StepAction action) {
        return executeStep(StepKeyword.THEN, action);
    }

    /**
     * Execute a THEN step with inline logic.
     */
    public AcceptanceTest then(Step step, String description) {
        return executeStep(StepKeyword.THEN, wrapStep(step, description));
    }

    /**
     * Execute an AND step action and log it.
     */
    public AcceptanceTest and(StepAction action) {
        return executeStep(StepKeyword.AND, action);
    }

    /**
     * Execute an AND step with inline logic.
     */
    public AcceptanceTest and(Step step, String description) {
        return executeStep(StepKeyword.AND, wrapStep(step, description));
    }

    /**
     * Execute a BUT step action and log it.
     */
    public AcceptanceTest but(StepAction action) {
        return executeStep(StepKeyword.BUT, action);
    }

    /**
     * Execute a BUT step with inline logic.
     */
    public AcceptanceTest but(Step step, String description) {
        return executeStep(StepKeyword.BUT, wrapStep(step, description));
    }

    /**
     * Log a keyword step at INFO level.
     */
    private void logKeyword(StepKeyword keyword, String description) {
        logger.info("{} {}", keyword.getLabel(), description);
    }

    /**
     * Wrap a Step functional interface and description into a StepAction.
     */
    private static StepAction wrapStep(Step step, String description) {
        return new StepAction() {
            @Override
            public void execute(StepContext context) throws Exception {
                step.execute(context);
            }

            @Override
            public String description() {
                return description;
            }
        };
    }
}
