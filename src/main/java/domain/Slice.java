package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Slice implements Comparable<Slice> {

    // Zero based start (0th element) and end (1th element) coordinates of the slice
    private Coordinate[] coords = new Coordinate[2];
    private List<Cell> cells = new ArrayList<>();
    private int numberOfMushrooms = 0, numberOfTomatoes = 0;
    private boolean valid = true;

    public Slice(Coordinate coords1, Coordinate coords2, PizzaIngredient[][] pizzaIng, Cell[][] cells) {
        setCoords(coords1, coords2);

        for (int i = this.coords[0].getX(); i <= this.coords[1].getX(); i++) {
            for (int j = this.coords[0].getY(); j <= this.coords[1].getY(); j++) {

                this.cells.add(cells[i][j]);

                if (pizzaIng[i][j] == PizzaIngredient.M) {
                    numberOfMushrooms++;
                } else {
                    numberOfTomatoes++;
                }
            }
        }
    }

    public boolean isValid(int minIng, int maxCells) {
        return (numberOfTomatoes >= minIng && numberOfMushrooms >= minIng && cells.size() <= maxCells);
    }

    public Coordinate[] getCoords() {
        return coords;
    }

    public void setCoords(Coordinate coords1, Coordinate coords2) {
        this.coords[0] = coords1;
        this.coords[1] = coords2;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public int size() {
        return cells.size();
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Slice" + Arrays.deepToString(coords) + " ";
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

        if (x1 > x4 || x3 > x2) {
            return false;
        }

        if (y1 > y4 || y3 > y2) {
            return false;
        }

        return true;
    }

    @Override
    public int compareTo(Slice o) {
        return Integer.compare(o.cells.size(), this.cells.size());
    }
    
    public boolean hasValidCells() {
        for(Cell c : cells) {
            if(c.getPossibilities() == 0)
                return false;
        }
        
        return true;
    }
}
