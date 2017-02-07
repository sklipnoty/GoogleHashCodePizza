/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Sklipnoty
 */
public class Cell implements Comparable<Cell>{
    private List<Slice> slices = new ArrayList<>();
    private Coordinate coordinate;
    private int possibilities;

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

    public void setSlices(List<Slice> slices) {
        this.slices = slices;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public int compareTo(Cell o) {
        return Integer.compare(possibilities, o.possibilities);
    }

    @Override
    public String toString() {
        return possibilities + " ";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
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
    
    
    
}
