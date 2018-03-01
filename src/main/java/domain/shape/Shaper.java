package domain.shape;

import domain.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class Shaper
{
    private List<Shape> shapeCoords;
    private int maxSize;
    private int minSize;

    public Shaper(int maxSize)
    {
        this(maxSize, 1);
    }

    public Shaper(int maxSize, int minOfEachIngredient)
    {
        this.maxSize = maxSize;
        this.minSize = minOfEachIngredient * 2; // always 2 ingredients: tomato and mushroom
        this.shapeCoords = new ArrayList<>();
        makeShapes();
    }

    /*
        Using maxcells, we make all the default possible shapes.
    */
    private void makeShapes()
    {
        for (int i = 0; i < maxSize; i++)
        {
            for (int j = 0; j < Math.floor(maxSize / (i + 1)); j++)
            {
                // check if area of rectangle shape is greater or equal to min size
                if ((i + 1) * (j + 1) >= minSize)
                {
                    Coordinate topLeft = new Coordinate(0, 0);
                    Coordinate bottomRight = new Coordinate(i, j);
                    Shape s = new Shape(topLeft, bottomRight);
                    shapeCoords.add(s);
                }
            }
        }
    }

    public List<Shape> getShapeCoords()
    {
        return shapeCoords;
    }

    /*
        For a given x and y, we return all possible from that location with correct coordinates.
        We use the default shapes made above and transform these coordinates to the given x and y.
    */
    public List<Shape> getPossibleShapes(int x, int y, int maxX, int maxY)
    {
        List<Shape> shapes = new ArrayList<>();

        for (Shape s : shapeCoords)
        {

            if (s.bottomRight.getY() + y >= maxY || s.bottomRight.getX() + x >= maxX)
            {
                continue;
            }
            
            Coordinate topLeft = new Coordinate(s.topLeft.getX() + x, s.topLeft.getY() + y);
            Coordinate bottomRight = new Coordinate(s.bottomRight.getX() + x, s.bottomRight.getY() + y);
            Shape validShape = new Shape(topLeft, bottomRight);

            shapes.add(validShape);
        }

        return shapes;
    }
}
