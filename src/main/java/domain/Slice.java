package domain;

public class Slice
{
    private final Coordinate topLeft;
    private final Coordinate bottomRight;
    private final int sliceArea;
    private int numberOfMushrooms = 0;
    private int numberOfTomatoes = 0;

    public Slice(Coordinate topLeft, Coordinate bottomRight, int numberOfTomatoes)
    {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.sliceArea = (this.bottomRight.getX() - this.topLeft.getX() + 1)
                       * (this.bottomRight.getY() - this.topLeft.getY() + 1);
        this.numberOfTomatoes = numberOfTomatoes;
        this.numberOfMushrooms = sliceArea - numberOfTomatoes;
    }

    public Coordinate getTopLeft()
    {
        return topLeft;
    }

    public Coordinate getBottomRight()
    {
        return bottomRight;
    }
    
    public int getArea()
    {
        return sliceArea;
    }

    public int getNumberOfMushrooms()
    {
        return numberOfMushrooms;
    }

    public int getNumberOfTomatoes()
    {
        return numberOfTomatoes;
    }

    public boolean overlapsWith(Slice slice)
    {
        // https://silentmatt.com/rectangle-intersection/
        return (this.topLeft.getX() <= slice.bottomRight.getX()
                && this.bottomRight.getX() >= slice.topLeft.getX()
                && this.topLeft.getY() <= slice.bottomRight.getY()
                && this.bottomRight.getY() >= slice.topLeft.getY());
    }
}
