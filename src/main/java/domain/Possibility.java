package domain;

/**
 *
 * @author Bart Kuenen <bartkuenen@gmail.com>
 */
public abstract class Possibility {
    private int index = 0;
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public abstract boolean isValid();
}
