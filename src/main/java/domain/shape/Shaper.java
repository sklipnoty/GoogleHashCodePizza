package domain.shape;

import domain.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class Shaper {

    private List<Shape> shapeCoords;
    private int maxSize;
    private int minSize;

    public Shaper(int maxSize) {
        this(maxSize, 1);
    }
    
    public Shaper(int maxSize, int minOfEachIngredient) {
        this.maxSize = maxSize;
        this.minSize = minOfEachIngredient * 2; // always 2 ingredients: Tomato and mushroom
        this.shapeCoords = new ArrayList<>();
        makeShapes();
    }

    private void makeShapes() {
        for (int i = 0; i < maxSize; i++) {
            for (int j = 0; j < Math.floor(maxSize / (i + 1)); j++) {
                // check if area of rectangle shape is greater or equeal to min size
                if((i+1)*(j+1) >= minSize) {
                    Shape s = new Shape();
                    s.coords[0] = new Coordinate(0, 0);
                    s.coords[1] = new Coordinate(i, j);
                    shapeCoords.add(s);
                }
            }
        }
    }

    public List<Shape> getShapeCoords() {
        return shapeCoords;
    }

    public List<Shape> getValidShapes(int x, int y, int maxX, int maxY) {
        List<Shape> shapes = new ArrayList<>();

        for (Shape s : shapeCoords) {
            Shape validShape = new Shape();
            validShape.coords[0] = new Coordinate(s.coords[0].getX() + x, s.coords[0].getY() + y);

            if (s.coords[1].getY() + y > maxY || s.coords[1].getX() + x > maxX) {
                continue;
            }

            validShape.coords[1] = new Coordinate(s.coords[1].getX() + x, s.coords[1].getY() + y);
            shapes.add(validShape);
        }

        return shapes;
    }

}

/*
 // Vierkantjes
 int square = (int) Math.sqrt(n);

 for (int i = 0; i < square; i++) {
 for (int j = 0; j < square; j++) {
 System.out.println(i + " " + j);
 }
 }

 // Rechthoeken
 for (int i = 0; i < n; i++) {
 System.out.println(i + " " + 0);
 System.out.println(0 + " " + i);
 }
 */
