package domain;

import java.util.Arrays;

/**
 * Google hashcode example
 * @author Sklipnoty
 */
public class Pizza {
    private PizzaIngredient pizzaIng[][];
    private int cols;
    private int rows;
    private int maxCells;
    private int minIng;

    public Pizza(PizzaIngredient[][] pizzaIng, int cols, int rows, int maxCells, int minIng) {
        this.pizzaIng = pizzaIng;
        this.cols = cols;
        this.rows = rows;
        this.maxCells = maxCells;
        this.minIng = minIng;
    }

    @Override
    public String toString() {
        return "Pizza{" + "cols=" + cols + ", rows=" + rows + ", maxCells=" + maxCells + ", minIng=" + minIng + " \n"+ Arrays.deepToString(pizzaIng)+ '}';
    }
}
