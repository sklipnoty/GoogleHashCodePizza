package domain;

import domain.shape.Shape;
import domain.shape.Shaper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private HashMap<Coordinate, List<Slice>> sliceMap = new HashMap<>();

    private Cell[][] cells;
    private List<Cell> possibleCells;

    private int firstNotZero = 0;

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

        makeMapping();

        System.out.println("Pre-Setup Done");
    }

    private void makeMapping() {
        // Keep a mapping of coordinate , possible shapes.
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                Cell cell = new Cell();
                cells[i][j] = cell;
            }
        }
        
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                        Coordinate c = new Coordinate(i, j);
                cells[i][j].setCoordinate(c);

                List<Shape> shapes = shaper.getPossibleShapes(i, j, cols, rows);
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

        System.out.println("Mapping of coord / possible slices");
    }

    public void slice() {
        solution = new ArrayList<>();

        // First make possibility matrix
        getPossibleSlices();
        Cell cell;
        // first place all zero possibilities to the left side
        firstNotZero = 0;
        if(possibleCells.size() > 0) {
            do {
                cell = Collections.min(possibleCells.subList(firstNotZero, possibleCells.size()));
                if(cell.getPossibilities() == 0) {
                    this.swapToFront(cell);
                }
            }while(cell.getPossibilities() == 0);
        }
        
        //  System.out.println(cells.toString());
        while (possibleCells.size() > 0) {
            // possible bug if all zeros.
            if (firstNotZero == possibleCells.size()) {
                break;
            }

            cell = Collections.min(possibleCells.subList(firstNotZero, possibleCells.size()));


            cell.getSlices().sort(null);

            int i = 0;
            Slice sl = cell.getSlices().get(i);
            while(!sl.isValid()) {
                i++;
                sl = cell.getSlices().get(i);
            }

            solution.add(sl); //TODO Sort the slices a cell on size. 
            //System.out.println(solution.size());
            adjustPossibleSlices(sl);
        }

        //    System.out.println(solution.toString());
        makeOutput(solution);

    }

    @Override
    public String toString() {
        return "Pizza{" + "cols=" + cols + ", rows=" + rows + ", maxCells=" + maxCells + ", minIng=" + minIng + " \n" + Arrays.deepToString(pizzaIng) + '}';
    }

    private void getPossibleSlices() {

        possibleCells = new ArrayList<>();

        for ( List<Slice> slices : sliceMap.values()) {
            for(Slice sl: slices) { 
                for (Cell cell : sl.getCells()) {
                    cell.incrementPossibility();
                    cell.getSlices().add(sl);
                }
            }
        }

        for (int i = 0; i < cols; i++) { 
            for (int j = 0; j < rows; j++) {
                possibleCells.add(cells[i][j]);
            }
        }
    }

    private void makeOutput(ArrayList<Slice> solution) {
        PrintWriter pw = null;
        try {
            System.out.println("OUTPUT FILE");
            pw = new PrintWriter(new File("output.txt"));
            System.out.println(solution.size());
            pw.println(solution.size());
            for (Slice slice : solution) {
                System.out.print(slice.getCoords()[0].getY() + " " + slice.getCoords()[0].getX());
                pw.write(slice.getCoords()[0].getY() + " " + slice.getCoords()[0].getX());
                System.out.print(" " + slice.getCoords()[1].getY() + " " + slice.getCoords()[1].getX());
                pw.write(" " + slice.getCoords()[1].getY() + " " + slice.getCoords()[1].getX());
                System.out.println("");
                pw.println();
            }

            // Score::
            int score = 0;

            for (Slice sl : solution) {
                score += sl.getCells().size();
            }

            System.out.println("Score : " + score);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pizza.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
    }

    private void adjustPossibleSlices(Slice slice) {
        // Possible Cells
        //possibleCells.removeAll(slice.getCells());

        // decrement possibilities in neighbouring cells. 
        for (Cell cl : slice.getCells()) {
            swapToFront(cl);
            for (Slice sl : cl.getSlices()) {
                if(sl.isValid()) {
                    for (Cell c : sl.getCells()) {
                        c.decrementPossibility();
                        if(c.getPossibilities() == 0) {
                            swapToFront(c);
                        }
                    }
                    sl.setValid(false);
                }
            }
        }

        // remove all slices that have common cells
    }

    private void swapToFront(Cell cl) {
        int firstIndex = possibleCells.subList(firstNotZero, possibleCells.size()).indexOf(cl);
        if(firstIndex != -1) {
            firstIndex += firstNotZero;
            Collections.swap(possibleCells,  firstIndex , firstNotZero);
            firstNotZero++;
        }
    }
}
