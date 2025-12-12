package com.playground.solidjava.cucumber;

import com.playground.solidjava.lsp.Circle;
import com.playground.solidjava.lsp.Rectangle;
import com.playground.solidjava.lsp.Shape;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Cucumber step definitions for Liskov Substitution Principle.
 * This demonstrates that all Shape subclasses honor the Shape contract
 * and can be used interchangeably.
 */
public class LSPSteps {
    private Shape shape;
    private List<Shape> shapes;
    private BigDecimal area;
    private BigDecimal perimeter;

    @Given("a rectangle with width {int} and height {int}")
    public void createRectangle(int width, int height) {
        shape = new Rectangle(new BigDecimal(width), new BigDecimal(height));
    }

    @Given("a circle with radius {int}")
    public void createCircle(int radius) {
        shape = new Circle(new BigDecimal(radius));
    }

    @Given("a list of different shape types")
    public void createMultipleShapes() {
        shapes = new ArrayList<>();
        shapes.add(new Rectangle(new BigDecimal("4"), new BigDecimal("5")));
        shapes.add(new Circle(new BigDecimal("3")));
        shapes.add(new Rectangle(new BigDecimal("6"), new BigDecimal("7")));
    }

    @Given("multiple shapes of different types")
    public void initializeMultipleShapes() {
        shapes = new ArrayList<>();
        shapes.add(new Rectangle(new BigDecimal("5"), new BigDecimal("3")));
        shapes.add(new Circle(new BigDecimal("4")));
    }

    @When("I use each shape through the shape interface")
    public void useShapesPolymorphically() {
        for (Shape s : shapes) {
            area = s.getArea();
            perimeter = s.getPerimeter();
            // All shapes work the same way through the interface
        }
    }

    @Then("the rectangle should have area {int}")
    public void assertRectangleArea(int expectedArea) {
        assertThat(shape.getArea()).isEqualByComparingTo(new BigDecimal(expectedArea));
    }

    @Then("the rectangle should have perimeter {int}")
    public void assertRectanglePerimeter(int expectedPerimeter) {
        assertThat(shape.getPerimeter()).isEqualByComparingTo(new BigDecimal(expectedPerimeter));
    }

    @Then("the circle should have valid area")
    public void assertCircleHasValidArea() {
        assertThat(shape.getArea()).isGreaterThan(BigDecimal.ZERO);
    }

    @Then("the circle should have valid perimeter")
    public void assertCircleHasValidPerimeter() {
        assertThat(shape.getPerimeter()).isGreaterThan(BigDecimal.ZERO);
    }

    @Then("the rectangle should be valid")
    public void assertRectangleIsValid() {
        assertThat(shape.isValid()).isTrue();
    }

    @Then("the circle should be valid")
    public void assertCircleIsValid() {
        assertThat(shape.isValid()).isTrue();
    }

    @Then("all shapes should correctly implement their contract")
    public void assertAllShapesHonorContract() {
        for (Shape s : shapes) {
            assertThat(s.getArea()).isGreaterThan(BigDecimal.ZERO);
            assertThat(s.getPerimeter()).isGreaterThan(BigDecimal.ZERO);
            assertThat(s.isValid()).isTrue();
        }
    }

    @Then("all shapes should satisfy the shape contract")
    public void assertAllShapesSatisfyContract() {
        assertThat(shapes)
                .allSatisfy(s -> assertThat(s.isValid()).isTrue())
                .allSatisfy(s -> assertThat(s.getArea()).isGreaterThan(BigDecimal.ZERO))
                .allSatisfy(s -> assertThat(s.getPerimeter()).isGreaterThan(BigDecimal.ZERO));
    }

    @Then("each shape should correctly calculate its measurements")
    public void assertShapesMeasurements() {
        for (Shape s : shapes) {
            assertThat(s.getArea()).isGreaterThan(BigDecimal.ZERO);
            assertThat(s.getPerimeter()).isGreaterThan(BigDecimal.ZERO);
        }
    }

    @When("I try to create a rectangle with zero width")
    public void tryToCreateInvalidRectangle() {
        assertThatThrownBy(() -> new Rectangle(BigDecimal.ZERO, new BigDecimal("5")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Then("I should not create a circle with negative radius")
    public void assertCannotCreateNegativeCircle() {
        assertThatThrownBy(() -> new Circle(new BigDecimal("-5")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
