package domain;

import domain.shape.Shape;
import domain.shape.Shaper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private ArrayList<Coordinate> coordinates;

    private Cell[][] cells;

    public Pizza(PizzaIngredient[][] pizzaIng, int cols, int rows, int maxCells, int minIng) {
        this.pizzaIng = pizzaIng;
        this.cols = cols;
        this.rows = rows;
        this.maxCells = maxCells;
        this.minIng = minIng;
        this.cells = new Cell[cols][rows];

        //Presetup 
        System.out.println("Pre-Setup Pizza");

        //Make a shaper with default shapes.
        this.shaper = new Shaper(maxCells);
        //Calculate all possible variations
        shapeMap = new HashMap<>();
        coordinates = new ArrayList<>();

        makeMapping();

        System.out.println("Pre-Setup Done");
    }

    private void makeMapping() {
        // Keep a mapping of coordinate , possible shapes.
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                Cell cell = new Cell();
                cells[i][j] = cell;
                
                shapeMap.put(new Coordinate(i, j), shaper.getPossibleShapes(i, j, cols, rows));
            }
        }

        System.out.println("Mapping of coord / possible slices");
        //Keep a mapping of coordinate , possible slices. (this should replace the above mapping)

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                Coordinate c = new Coordinate(i, j);
                coordinates.add(c);
                cells[i][j].setCoordinate(c);

                List<Shape> shapes = shapeMap.get(c);
                List<Slice> slices = new ArrayList<>();

                for (Shape shape : shapes) {
                    Slice sl = new Slice(shape.coords[0], shape.coords[1], this.pizzaIng, cells);

                    if (sl.isValid(minIng, maxCells)) {
                        slices.add(sl);
                    }
                }

                sliceMap.put(c, slices);
            }
        }
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

            cell.getSlices().sort(null);
            solution.add(cell.getSlices().get(cell.getSlices().size()-1)); //TODO Sort the slices a cell on size. 
            
            System.out.println(solution.size());
            
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

        ArrayList<Cell> possibleSlices = new ArrayList<>();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j].reset();
            }
        }

        for (Coordinate coord : coordinates) {
            for (Slice sl : sliceMap.get(coord)) {

                if (solution.contains(sl)) {
                    continue;
                }

                for (Cell cell : sl.getCells()) {
                    cell.incrementPossibility();
                    cell.getSlices().add(sl);
                }
            }
        }

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                possibleSlices.add(cells[i][j]);
            }
        }
        
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
