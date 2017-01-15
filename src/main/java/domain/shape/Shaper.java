package domain.shape;

import domain.Coordinate;
import java.util.ArrayList;
import java.util.List;

public class Shaper {

    private List<Shape> shapeCoords;
    private int n;

    public Shaper(int n) {
        this.n = n;
        this.shapeCoords = new ArrayList<>();
        makeShapes();
    }

    private void makeShapes() {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < Math.floor(n / (i + 1)); j++) {
                Shape s = new Shape();
                s.coords[0] = new Coordinate(0, 0);
                s.coords[1] = new Coordinate(i + 1, j + 1);
                shapeCoords.add(s);
            }
        }
    }

    public List<Shape> getShapeCoords() {
        return shapeCoords;
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
