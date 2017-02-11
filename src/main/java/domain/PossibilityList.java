/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author Bart Kuenen <bartkuenen@gmail.com>
 * @param <E>
 */
public class PossibilityList<E extends Possibility> implements Iterable<E>{
    private int firstValidElement = 0;
    private final Function<List<E>, E> elementFetcher;
    private final List<E> list;

    public PossibilityList(Function<List<E>, E> elementFetcher) {
        this.list =  new ArrayList<>();
        this.elementFetcher = elementFetcher;
    }
    
    public E getElement()
    {
        return elementFetcher.apply(list.subList(firstValidElement, list.size()));
    }
    
    public void removeElement(E element) {
        int index = element.getIndex();
        if(index >= firstValidElement) {
            Collections.swap(list, index, firstValidElement);
            element.setIndex(firstValidElement);
            list.get(index).setIndex(index);
            firstValidElement++;
        }
    }
    
    public int size() {
        return this.list.size();
    }
    
    public boolean add(E e) {
        int index = this.list.size();
        if(this.list.add(e)) {
            e.setIndex(index);
            if(!e.isValid()) {
                this.removeElement(e);
            }
            return true;
        }
        return false;
    }
    
    public boolean hasPossibilities() {
        return this.firstValidElement < this.list.size();
    }

    @Override
    public Iterator<E> iterator() {
        return this.list.subList(firstValidElement, list.size()).iterator();
    }
}
