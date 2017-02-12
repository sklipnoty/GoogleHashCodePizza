package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Sklipnoty
 */
public class Cell extends Possibility implements Comparable<Cell>{
    private List<Slice> slices = new ArrayList<>();
    private Coordinate coordinate;
    private int possibilities = 0;

    public Cell() {
    }

    public int getPossibilities() {
        return possibilities;
    }

    public void setPossibilities(int possibilities) {
        this.possibilities = possibilities;
    }
    
    public List<Slice> getSlices() {
        return slices;
    }

    public void addSlice(Slice slice) {
        slices.add(slice);
    }
    
    public void setSlices(List<Slice> slices) {
        this.slices = slices;
    }

    @Override
    public int compareTo(Cell o) {
        return Integer.compare(possibilities, o.possibilities);
    }

    @Override
    public String toString() {
        return coordinate.toString();
    }

    @Override
    public int hashCode() {
        return coordinate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cell other = (Cell) obj;
        if (!Objects.equals(this.coordinate, other.coordinate)) {
            return false;
        }
        return true;
    }
    
    
    public void incrementPossibility() {
        this.possibilities++;
    }
    
    public void reset() {
        possibilities = 0;
        slices.clear();
    }

    public void decrementPossibility() {
        if(possibilities > 0)
            possibilities--;
    }

    @Override
    public boolean isValid() {
        return this.getPossibilities() > 0;
    }
}
