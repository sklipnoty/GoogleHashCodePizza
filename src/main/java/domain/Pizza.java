package domain;

import domain.shape.Shape;
import domain.shape.Shaper;
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
 * @author Sklipnoty, Nvhaver
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
        // First make possibility matrix
        int[][] possibilities = makePossibilityMatrix();

        System.out.println(Arrays.deepToString(possibilities));

    }

    public boolean isValidSlice(Slice slice) {
        int numTomatoes = 0;
        int numMushrooms = 0;

        // Loop over slice parts
        for (int i = slice.coords[0].getX(); i < slice.coords[1].getX(); i++) {
            for (int j = slice.coords[0].getY(); j < slice.coords[1].getY(); j++) {
                if (pizzaIng[i][j] == PizzaIngredient.M) {
                    numMushrooms++;
                } else {
                    numTomatoes++;
                }
            }
        }

        return (numTomatoes >= minIng && numMushrooms >= minIng && slice.size() <= maxCells);
    }

    @Override
    public String toString() {
        return "Pizza{" + "cols=" + cols + ", rows=" + rows + ", maxCells=" + maxCells + ", minIng=" + minIng + " \n" + Arrays.deepToString(pizzaIng) + '}';
    }

    private int[][] makePossibilityMatrix() {
        int[][] possibilities = new int[cols][rows];
        Shaper shaper = new Shaper(maxCells);

        for (int i = 0; i < cols; i++) {

            for (int j = 0; j < rows; j++) {
                List<Slice> slices = new ArrayList<>();
                List<Shape> shapes = shaper.getPossibleShapes(i, j, cols, rows);

                for (Shape shape : shapes) {
                    Slice sl = new Slice();
                    sl.coords[0] = shape.coords[0];
                    sl.coords[1] = shape.coords[1];

                    if (isValidSlice(sl)) {
                        slices.add(sl);
                    }
                }
                
                possibilities[i][j] = slices.size();
            }
        }

        return possibilities;
    }
}
