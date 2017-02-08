package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Slice implements Comparable<Slice>{

    // Zero based start (0th element) and end (1th element) coordinates of the slice
    private Coordinate[] coords = new Coordinate[2];
    private List<Cell> cells = new ArrayList<>();
    private int numberOfMushrooms = 0, numberOfTomatoes = 0;

    public Slice(Coordinate coords1, Coordinate coords2, PizzaIngredient[][] pizzaIng) {
        setCoords(coords1, coords2);

        for (int i = this.coords[0].getX(); i <= this.coords[1].getX(); i++) {
            for (int j = this.coords[0].getY(); j <= this.coords[1].getY(); j++) {
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

        for (int i = coords[0].getX(); i <= coords[1].getX(); i++) {
            for (int j = coords[0].getY(); j <= coords[1].getY(); j++) {
                Cell c = new Cell();
                c.setCoordinate(new Coordinate(i, j));
                cells.add(c);
            }
        }
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
        return Integer.compare(this.cells.size(), o.cells.size());
    }
    
    

}
