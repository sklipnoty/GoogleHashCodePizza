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
import java.util.function.Function;
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
    private PossibilityList<Cell> possibleCells;

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
    
        private void getPossibleSlices() {

        possibleCells = new PossibilityList<>(new Function<List<Cell>, Cell>(){
            @Override
            public Cell apply(List<Cell> t) {
                return Collections.min(t);
            }
        });

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

    public void slice() {
        solution = new ArrayList<>();

        // First make possibility matrix
        getPossibleSlices();
        Cell cell;
        
        //  System.out.println(cells.toString());
        while (possibleCells.hasPossibilities()) {

            cell = possibleCells.getElement();
            
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
        // decrement possibilities in neighbouring cells. 
        for (Cell cl : slice.getCells()) {
            possibleCells.removeElement(cl);
            for (Slice sl : cl.getSlices()) {
                if(sl.isValid()) {
                    for (Cell c : sl.getCells()) {
                        c.decrementPossibility();
                        if(!c.isValid()) {
                            possibleCells.removeElement(c);
                        }
                    }
                    sl.setValid(false);
                }
            }
        }
    }
}
