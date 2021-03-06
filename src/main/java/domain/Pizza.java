package domain;

import domain.shape.Shape;
import domain.shape.Shaper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    private ArrayList<Slice> solution;
    private final Shaper shaper;
    private HashMap<Coordinate, List<Shape>> shapeMap;

    public Pizza(PizzaIngredient[][] pizzaIng, int cols, int rows, int maxCells, int minIng) {
        this.pizzaIng = pizzaIng;
        this.cols = cols;
        this.rows = rows;
        this.maxCells = maxCells;
        this.minIng = minIng;

        //Presetup 
        System.out.println("Pre-Setup Pizza");
        //Make a shaper with default shapes.
        this.shaper = new Shaper(maxCells);
        //Calculate all possible variations
        shapeMap = new HashMap<>();

        for (int i = 0; i < cols; i++) {
            System.out.print("\r" + i +"/" + cols  +" \b" );
            for (int j = 0; j < rows; j++) {
                shapeMap.put(new Coordinate(i, j), shaper.getPossibleShapes(i, j, cols, rows));
            }
        }

        System.out.println("Pre-Setup Done");
    }

    public void slice() {
        solution = new ArrayList<>();

        // First make possibility matrix
        List<Cell> cells = getPossibleSlices();

      //  System.out.println(cells.toString());
        while (cells.size() > 0) {
            // Sort the result
            cells.sort(null);
            // Use slice with the least amount of possibilities.
            int i = 0;
            Cell cell = cells.get(i);

            while (i < cells.size() - 1 && cells.size() > 0 && cell.getPossibilities() == 0) {
                i++;
                cell = cells.get(i);
            }

            if (cell.getSlices().isEmpty()) {
                break;
            }

            solution.add(cell.getSlices().get(0)); //TODO Sort the slices a cell on size. 
            cells = getPossibleSlices();
        }

    //    System.out.println(solution.toString());
        makeOutput(solution);

    }

    public boolean isValidSlice(Slice slice) {
        int numTomatoes = 0;
        int numMushrooms = 0;

        if (solution.contains(slice)) {
            return false;
        }

        // Loop over slice parts
        // Hier sneller uitgaan namelijk wanneer minIng bereikt is.
        for (int i = slice.coords[0].getX(); i <= slice.coords[1].getX(); i++) {
            for (int j = slice.coords[0].getY(); j <= slice.coords[1].getY(); j++) {
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

    private List<Cell> getPossibleSlices() {
        
        System.out.println("Possible Slices");
        
        ArrayList<Cell> possibleSlices = new ArrayList<>();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Cell cell = new Cell();
                cell.setCoordinate(new Coordinate(i, j));
                possibleSlices.add(cell);
            }
        }
        
        System.out.println("List of possible slices setup");

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                List<Shape> shapes = shapeMap.get(new Coordinate(i, j));

                for (Shape shape : shapes) {
                    Slice sl = new Slice();
                    sl.coords[0] = shape.coords[0];
                    sl.coords[1] = shape.coords[1];

                    if (isValidSlice(sl)) {
                        for (int si = sl.coords[0].getX(); si <= sl.coords[1].getX(); si++) {
                            for (int sj = sl.coords[0].getY(); sj <= sl.coords[1].getY(); sj++) {
                                Cell newCell = new Cell();
                                newCell.setCoordinate(new Coordinate(si, sj));
                                int index = possibleSlices.indexOf(newCell);
                                possibleSlices.get(index).getSlices().add(sl);
                                possibleSlices.get(index).setPossibilities(possibleSlices.get(index).getPossibilities() + 1);
                            }
                        }
                    }
                }
            }
        }

        return possibleSlices;
    }

    private void makeOutput(ArrayList<Slice> solution) {
        System.out.println("OUTPUT FILE :::::");

        System.out.println(solution.size());

        for (Slice slice : solution) {
            System.out.print(slice.coords[0].getY() + " " + slice.coords[0].getX());
            System.out.print(" " + slice.coords[1].getY() + " " + slice.coords[1].getX());
            System.out.println("");
        }
    }
}
