package domain.shape;

import domain.Coordinate;

public class Shape
{
    public final Coordinate topLeft;
    public final Coordinate bottomRight;
    
    public Shape(Coordinate topLeft, Coordinate bottomRight)
    {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }
}
