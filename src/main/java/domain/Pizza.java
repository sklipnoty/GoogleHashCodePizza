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
    private HashMap<Coordinate, List<Slice>> sliceMap = new HashMap<>();
    private final ArrayList<Cell> possibleSlices;
    private final ArrayList<Coordinate> coordinates;

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

        // Keep a mapping of coordinate , possible shapes.
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                shapeMap.put(new Coordinate(i, j), shaper.getPossibleShapes(i, j, cols, rows));
            }
        }

        System.out.println("Mapping of coord / possible slices");
        //Keep a mapping of coordinate , possible slices. (this should replace the above mapping)
        possibleSlices = new ArrayList<>();
        coordinates = new ArrayList<>();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Cell cell = new Cell();
                Coordinate c = new Coordinate(i, j);
                coordinates.add(c);
                cell.setCoordinate(c);
                possibleSlices.add(cell);

                List<Shape> shapes = shapeMap.get(new Coordinate(i, j));
                List<Slice> slices = new ArrayList<>();

                for (Shape shape : shapes) {
                    Slice sl = new Slice(shape.coords[0], shape.coords[1], this.pizzaIng);
                    slices.add(sl);
                }

                sliceMap.put(c, slices);
            }
        }

        System.out.println("Fiding not valid slices and remove them");
        // check if slice are potential valid
        Map<Coordinate, List<Slice>> badSlices = new HashMap<>();

        for (Coordinate coord : coordinates) {
            for (Slice slice : sliceMap.get(coord)) {
                {
                    if (!slice.isValid(minIng, maxCells)) {

                        if (badSlices.get(coord) == null) {
                            badSlices.put(coord, new ArrayList<Slice>());
                        }

                        badSlices.get(coord).add(slice);
                    }
                }
            }
        }

        for (Coordinate coord : badSlices.keySet()) {
            sliceMap.get(coord).removeAll(badSlices.get(coord));
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

    @Override
    public String toString() {
        return "Pizza{" + "cols=" + cols + ", rows=" + rows + ", maxCells=" + maxCells + ", minIng=" + minIng + " \n" + Arrays.deepToString(pizzaIng) + '}';
    }

    private List<Cell> getPossibleSlices() {

        System.out.println("Possible Slices");

        for (Cell cl : possibleSlices) {
            cl.setPossibilities(0);
            cl.setSlices(new ArrayList<Slice>());
        }

        System.out.println("List of possible slices setup");

        for (Coordinate coord : coordinates) {
            for (Slice sl : sliceMap.get(coord)) {

                if (solution.contains(sl)) {
                    continue;
                }

                for (Cell cell : sl.getCells()) {
                    int index = possibleSlices.indexOf(cell);
                    possibleSlices.get(index).getSlices().add(sl);
                    possibleSlices.get(index).setPossibilities(possibleSlices.get(index).getPossibilities() + 1);
                }
            }
        }

        System.out.println(possibleSlices.toString());

        return possibleSlices;
    }

    private void makeOutput(ArrayList<Slice> solution) {
        System.out.println("OUTPUT FILE");

        System.out.println(solution.size());

        for (Slice slice : solution) {
            System.out.print(slice.getCoords()[0].getY() + " " + slice.getCoords()[0].getX());
            System.out.print(" " + slice.getCoords()[1].getY() + " " + slice.getCoords()[1].getX());
            System.out.println("");
        }
    }
}
