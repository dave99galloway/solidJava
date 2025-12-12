package com.playground.solidjava.cucumber;

import com.playground.solidjava.ocp.Discount;
import com.playground.solidjava.ocp.FixedDiscount;
import com.playground.solidjava.ocp.PercentageDiscount;
import com.playground.solidjava.ocp.PriceCalculator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Cucumber step definitions for Open/Closed Principle.
 * This class demonstrates that new discount scenarios can be added without modifying
 * the existing PriceCalculator class - it's closed for modification, open for extension.
 */
public class OCPSteps {
    private PriceCalculator calculator;
    private BigDecimal finalPrice;

    @Given("a price calculator")
    public void initializePriceCalculator() {
        calculator = new PriceCalculator();
    }

    @Given("a price calculator with extensible design")
    public void initializeExtensiblePriceCalculator() {
        calculator = new PriceCalculator();
    }

    @When("I add a {int} percent discount")
    public void addPercentageDiscount(int percentage) {
        calculator.addDiscount(new PercentageDiscount(new BigDecimal(percentage)));
    }

    @When("I add a {double} fixed discount")
    public void addFixedDiscount(double amount) {
        calculator.addDiscount(new FixedDiscount(new BigDecimal(String.valueOf(amount))));
    }

    @When("I calculate the final price for {double}")
    public void calculateFinalPrice(double basePrice) {
        finalPrice = calculator.calculateFinalPrice(new BigDecimal(String.valueOf(basePrice)));
    }

    @When("I add a new custom discount type")
    public void addCustomDiscountType() {
        // Demonstrates OCP: new discount type without modifying existing code
        Discount customDiscount = new Discount() {
            @Override
            public BigDecimal apply(BigDecimal amount) {
                // Custom logic: buy one get one 20% off
                return amount.multiply(new BigDecimal("0.8"));
            }

            @Override
            public String getDescription() {
                return "20% loyalty discount";
            }
        };
        calculator.addDiscount(customDiscount);
    }

    @Then("the final price should be {double}")
    public void assertFinalPrice(double expectedPrice) {
        assertThat(finalPrice).isEqualByComparingTo(new BigDecimal(String.valueOf(expectedPrice)));
    }

    @Then("the calculator should support the new discount without modification")
    public void assertCalculatorSupportsNewDiscount() {
        // The calculator worked with our new discount type without any changes
        assertThat(finalPrice).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        assertThat(calculator.getAppliedDiscounts()).contains("loyalty discount");
    }
}
