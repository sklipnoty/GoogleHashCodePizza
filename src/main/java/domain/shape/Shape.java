package domain.shape;

import domain.Coordinate;

/**
 *
 * @author Sklipnoty
 */
public class Shape {
    public Coordinate[] coords = new Coordinate[2];
    
    public Shape() {
        
    }

    @Override
    public String toString() {
        return "Shape{" + "coords=" + coords[0].toString() + " - "  + coords[1].toString() + '}';
    }

    
}
