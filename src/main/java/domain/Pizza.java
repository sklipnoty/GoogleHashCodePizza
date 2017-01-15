package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Google hashcode example
 *
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

    public void slice() {
        LinkedList<Coordinate> tomatoes = new LinkedList<>();
        LinkedList<Coordinate> shrooms = new LinkedList<>();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (pizzaIng[j][i] == PizzaIngredient.M) {
                    shrooms.add(new Coordinate(j, i));
                } else {
                    tomatoes.add(new Coordinate(j, i));
                }
            }
        }

        List<Slice> slices = new ArrayList<>();
        Slice current = new Slice();

        int numberOfIngr = minIng;

        while (tomatoes.size() > 0 && shrooms.size() > 0 && numberOfIngr > 0) {
            current.coords.add(tomatoes.pop());
            current.coords.add(shrooms.pop());
            numberOfIngr--;

            if (numberOfIngr == 0) {
                slices.add(current);
                current = new Slice();
                numberOfIngr = minIng;
            }
        }

        System.out.println(slices.toString());
    }

    @Override
    public String toString() {
        return "Pizza{" + "cols=" + cols + ", rows=" + rows + ", maxCells=" + maxCells + ", minIng=" + minIng + " \n" + Arrays.deepToString(pizzaIng) + '}';
    }
}
