package domain;

import java.util.ArrayList;
import java.util.List;

public class Slice {

    // Zero based start (0th element) and end (1th element) coordinates of the slice
    public Coordinate[] coords = new Coordinate[2];
    
    public Slice() {
        
    }

    public int size(){
        // +1 due to 0 based coordinates
        int rows = coords[1].getX() - coords[0].getX() +1;
        int columns = coords[1].getY() - coords[0].getY() +1;
        return rows * columns;
    }
    
    @Override
    public String toString() {
        return "Slice{" + "coords=" + coords + '}';
    }
}
