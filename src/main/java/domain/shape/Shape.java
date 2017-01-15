/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        return "Shape{" + "coords=" + coords + '}';
    }

    
}
