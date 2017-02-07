package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Slice {

    // Zero based start (0th element) and end (1th element) coordinates of the slice
    public Coordinate[] coords = new Coordinate[2];
    private List<Cell> cells = new ArrayList<>();
    
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Slice other = (Slice) obj;

        int x1 = this.coords[0].getX();
        int x2 = this.coords[1].getX();
        int y1 = this.coords[0].getY();
        int y2 = this.coords[1].getY();
        
        int x3 = other.coords[0].getX();
        int x4 = other.coords[1].getX();
        int y3 = other.coords[0].getY();
        int y4 = other.coords[1].getY();
        
        if( x1 > x4 || x3 > x2 ) 
            return false;
        
        if( y1 > y4 || y3 > y2 )
            return false;
        
        return true;
    }
    
    
}
