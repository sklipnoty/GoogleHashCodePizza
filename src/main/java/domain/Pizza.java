package domain;

import domain.shape.Shape;
import domain.shape.Shaper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Google hashcode example
 *
 * @author Sklipnoty, Nvhaver
 */
public class Pizza
{
    private PizzaIngredient pizzaIng[][];
    private int cols;
    private int rows;
    private int maxCells;
    private int minIng;
    private List<Slice> solution;
    private final Shaper shaper;

    private Cell[][] cells;
    private int[][] tomatoCount;
    private PossibilityList<Cell> possibleCells;

    public Pizza(PizzaIngredient[][] pizzaIng, int cols, int rows, int maxCells, int minIng)
    {
        this.pizzaIng = pizzaIng;
        this.cols = cols;
        this.rows = rows;
        this.maxCells = maxCells;
        this.minIng = minIng;
        this.cells = new Cell[cols][rows];
        this.tomatoCount = new int[cols][rows];
        this.solution = new ArrayList<>();
        this.possibleCells = new PossibilityList<>(new Function<List<Cell>, Cell>()
        {
            @Override
            public Cell apply(List<Cell> t)
            {
                return Collections.min(t);
            }
        });

        // Pre-setup 
        System.out.println("Pre-Setup Pizza");

        // Make a shaper with default shapes.
        this.shaper = new Shaper(maxCells);

        makeMapping();

        System.out.println("Pre-Setup Done");
    }

    private void makeMapping()
    {
        cells[0][0] = new Cell();

        if (pizzaIng[0][0] == PizzaIngredient.T)
        {
            tomatoCount[0][0] = 1;
        }

        // First row
        for (int i = 1; i < cols; i++)
        {
            cells[i][0] = new Cell();

            tomatoCount[i][0] = tomatoCount[i - 1][0];

            if (pizzaIng[i][0] == PizzaIngredient.T)
            {
                tomatoCount[i][0]++;
            }
        }

        // First column
        for (int i = 1; i < rows; i++)
        {
            cells[0][i] = new Cell();

            tomatoCount[0][i] = tomatoCount[0][i - 1];

            if (pizzaIng[0][i] == PizzaIngredient.T)
            {
                tomatoCount[0][i]++;
            }
        }

        for (int i = 1; i < cols; i++)
        {
            for (int j = 1; j < rows; j++)
            {
                cells[i][j] = new Cell();

                tomatoCount[i][j] = tomatoCount[i - 1][j]
                                  + tomatoCount[i][j - 1]
                                  - tomatoCount[i - 1][j - 1];

                if (pizzaIng[i][j] == PizzaIngredient.T)
                {
                    tomatoCount[i][j]++;
                }
            }
        }

        // Create slices and initialize cell possibility values
        for (int i = 0; i < cols; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                //possibleCells.add(cells[i][j]);
                
                List<Shape> shapes = shaper.getPossibleShapes(i, j, cols, rows);

                for (Shape shape : shapes)
                {
                    Slice sl = new Slice(shape.coords[0], shape.coords[1], this.pizzaIng, cells, tomatoCount);

                    if (sl.isValid(minIng, maxCells))
                    {
                        for (Cell cell : sl.getCells())
                        {
                            cell.incrementPossibility();
                            cell.addSlice(sl);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < cols; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                possibleCells.add(cells[i][j]);
            }
        }
    }

    public void slice()
    {
        while (possibleCells.hasPossibilities())
        {
            Cell cell = possibleCells.getElement();
            cell.getSlices().sort(null);

            int i = 0;
            Slice sl = cell.getSlices().get(i);
            while (!sl.isValid())
            {
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

    private void adjustPossibleSlices(Slice slice)
    {
        // decrement possibilities in neighbouring cells. 
        for (Cell cl : slice.getCells())
        {
            possibleCells.removeElement(cl);
            for (Slice sl : cl.getSlices())
            {
                if (sl.isValid())
                {
                    for (Cell c : sl.getCells())
                    {
                        c.decrementPossibility();
                        if (!c.isValid())
                        {
                            possibleCells.removeElement(c);
                        }
                    }
                    sl.setValid(false);
                }
            }
        }
    }

    @Override
    public String toString()
    {
        return "Pizza{" + "cols=" + cols + ", rows=" + rows + ", maxCells=" + maxCells + ", minIng=" + minIng + " \n" + Arrays.deepToString(pizzaIng) + '}';
    }

    private void makeOutput(List<Slice> solution)
    {
        PrintWriter pw = null;
        try
        {
            System.out.println("OUTPUT FILE");
            pw = new PrintWriter(new File("output.txt"));
            System.out.println(solution.size());
            pw.println(solution.size());
            for (Slice slice : solution)
            {
                System.out.print(slice.getCoords()[0].getY() + " " + slice.getCoords()[0].getX());
                pw.write(slice.getCoords()[0].getY() + " " + slice.getCoords()[0].getX());
                System.out.print(" " + slice.getCoords()[1].getY() + " " + slice.getCoords()[1].getX());
                pw.write(" " + slice.getCoords()[1].getY() + " " + slice.getCoords()[1].getX());
                System.out.println("");
                pw.println();
            }

            // Score::
            int score = 0;

            for (Slice sl : solution)
            {
                score += sl.getCells().size();
            }

            System.out.println("Score : " + score);

        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(Pizza.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            pw.close();
        }
    }
}
