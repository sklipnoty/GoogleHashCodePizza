package solver;

import domain.Coordinate;
import domain.Pizza;
import domain.Slice;
import domain.shape.Shape;
import domain.shape.Shaper;
import java.util.List;
import java.util.Random;

public class PizzaSolver
{
    private static final int OVERLAP_ABORT_TRESHOLD = 1000000;
    private final Pizza pizza;
    private final Shaper shaper;
    private final Random rng;
    
    public PizzaSolver(Pizza pizza)
    {
        this.pizza = pizza;
        this.shaper = new Shaper(pizza.getMaxCells()); // Make a shaper with default shapes.
        this.rng = new Random(54120); // the magic number !
    }
    
    public PizzaSolution generateRandomSolution()
    {
        PizzaSolution solution = new PizzaSolution(pizza);
        
        int numOverlaps = 0;
        while (numOverlaps < OVERLAP_ABORT_TRESHOLD)
        {
            Slice slice = getValidRandomSlice();
            
            if (!solution.isOverlap(slice))
            {
                solution.addSlice(slice);
            }
            else
            {
                numOverlaps++;
            }
        }

        return solution;
    }
    
    private Shape getRandomShape()
    {
        int x = rng.nextInt(pizza.getCols());
        int y = rng.nextInt(pizza.getRows());
        
        List<Shape> shapes = shaper.getPossibleShapes(x, y, pizza.getCols(), pizza.getRows());

        return !shapes.isEmpty() ? shapes.get(rng.nextInt(shapes.size())) : null;
    }
    
    private Slice getRandomSlice()
    {
        Shape randomShape = getRandomShape();
        return randomShape != null ? pizza.getSlice(randomShape) : null;
//        int x1 = rng.nextInt(pizza.getCols());
//        int y1 = rng.nextInt(pizza.getRows());
//        int x2 = rng.nextInt(pizza.getCols());
//        int y2 = rng.nextInt(pizza.getRows());
//        
//        Coordinate topLeft = new Coordinate(Math.min(x1, x2), Math.min(y1, y2));
//        Coordinate bottomRight = new Coordinate(Math.max(x1, x2), Math.max(y1, y2));
//        
//        return pizza.getSlice(topLeft, bottomRight);
    }
    
    private Slice getValidRandomSlice()
    {
        Slice slice = getRandomSlice();
        
        while (!pizza.isValidSlice(slice))
        {
            slice = getRandomSlice();
        }
        
        return slice;
    }
}
