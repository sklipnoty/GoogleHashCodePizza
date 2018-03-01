package domain;

import domain.shape.Shape;
import java.util.Scanner;

public class Pizza
{
    private final int rows;
    private final int cols;
    private final int maxCells;
    private final int minIngredients;
    private final int[][] tomatoCount;
    private final int[][] tomatoCountB;

    public Pizza(Scanner in)
    {
        /*
            Process input.
        */
        
        this.rows = in.nextInt();
        this.cols = in.nextInt();
        this.minIngredients = in.nextInt();
        this.maxCells = in.nextInt();
        this.tomatoCount = new int[rows][cols];
        this.tomatoCountB = new int[rows][cols];
        
        in.nextLine(); // discard newline
        
        int row = 0;
        
        while (in.hasNextLine())
        {
            String line = in.nextLine();
            
            for (int col = 0; col < line.length(); col++)
            {
                tomatoCount[row][col] = line.charAt(col) == 'T' ? 1 : 0;
                tomatoCountB[row][col] = tomatoCount[row][col];
            }
            
            row++;
        }

        /*
            Pre-compute useful tomato counts by dynamic programming.
            This pre-processing step allows to compute the number of tomatoes
            and mushrooms within an arbitrary slice in O(1) time complexity.
        */
        
        // First col
        for (int i = 1; i < rows; i++)
        {
            tomatoCount[i][0] += tomatoCount[i - 1][0];
        }

        // First row
        for (int i = 1; i < cols; i++)
        {
            tomatoCount[0][i] += tomatoCount[0][i - 1];
        }

        for (int i = 1; i < rows; i++)
        {
            for (int j = 1; j < cols; j++)
            {
                tomatoCount[i][j] = tomatoCount[i][j]
                                  + tomatoCount[i - 1][j]
                                  + tomatoCount[i][j - 1]
                                  - tomatoCount[i - 1][j - 1];
            }
        }
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public int getMaxCells()
    {
        return maxCells;
    }
    
    public int getMinIngredients()
    {
        return minIngredients;
    }
    
    public Slice getSlice(Coordinate topLeft, Coordinate bottomRight)
    {
        int numberOfTomatoes = tomatoCount[bottomRight.getY()][bottomRight.getX()];

        // Corrective calculation step
        // Can probably be written in a more succinct way
        if (topLeft.getX() != 0 && topLeft.getY() != 0)
        {
            // [0].getX() != 0 && [0].getY() != 0
            numberOfTomatoes += tomatoCount[topLeft.getY() - 1][topLeft.getX() - 1];
            numberOfTomatoes -= tomatoCount[topLeft.getY() - 1][bottomRight.getX()];
            numberOfTomatoes -= tomatoCount[bottomRight.getY()][topLeft.getX() - 1];
        }
        else if (topLeft.getX() != 0)
        {
            // [0].getX() != 0 && [0].getY() == 0
            numberOfTomatoes -= tomatoCount[bottomRight.getY()][topLeft.getX() - 1];
        }
        else if (topLeft.getY() != 0)
        {
            // [0].getX() == 0 && [0].getY() != 0
            numberOfTomatoes -= tomatoCount[topLeft.getY() - 1][bottomRight.getX()];
        }
        else
        {
            // [0].getX() == 0 && [0].getY() == 0
            // no corrective calculation required.
        }
        
        int numTomatoes = 0;
        
        for (int i = topLeft.getY(); i < bottomRight.getY(); i++)
        {
            for (int j = topLeft.getX(); j < bottomRight.getX(); j++)
            {
                if (tomatoCount[i][j] == 1)
                {
                    numTomatoes++;
                }
            }
        }
        
        assert numTomatoes == numberOfTomatoes;
                
        
        return new Slice(topLeft, bottomRight, numberOfTomatoes);
    }
    
    public Slice getSlice(Shape shape)
    {
        return getSlice(shape.topLeft, shape.bottomRight);
    }
    
    public boolean isValidSlice(Slice slice)
    {
        return slice != null
               && isWithinBounds(slice.getTopLeft())
               && isWithinBounds(slice.getBottomRight())
               && slice.getNumberOfTomatoes() >= minIngredients
               && slice.getNumberOfMushrooms() >= minIngredients
               && slice.getArea() <= maxCells;
    }
    
    private boolean isWithinBounds(Coordinate c)
    {
        return (c.getY() >= 0 && c.getY() < rows &&
                c.getX() >= 0 && c.getX() < cols);
    }
}
