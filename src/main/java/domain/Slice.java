package domain;

import java.util.ArrayList;
import java.util.List;

public class Slice {

    public List<Coordinate> coords = new ArrayList<>();
    
    public Slice() {
        
    }

    @Override
    public String toString() {
        return "Slice{" + "coords=" + coords + '}';
    }
    
    
    
}
