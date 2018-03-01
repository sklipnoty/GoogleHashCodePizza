package solver;

import domain.Pizza;
import domain.Slice;
import java.util.HashSet;
import java.util.Set;

public class PizzaSolution
{
    private final Set<Slice> slices;
    private final Pizza pizza;
    private int score;

    public PizzaSolution(Pizza pizza)
    {
        this.slices = new HashSet<>();
        this.pizza = pizza;
        this.score = 0;
    }
    
    public void addSlice(Slice slice)
    {
        slices.add(slice);
        score += slice.getArea();
    }
    
    public void removeSlice(Slice slice)
    {
        if (slices.remove(slice))
        {
            score -= slice.getArea();
        }
    }
    
    public boolean isOverlap(Slice slice)
    {
        for (Slice s : slices)
        {
            if (s.overlapsWith(slice))
            {
                return true;
            }
        }
        
        return false;
    }

    public int getScore()
    {
        return score;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        for (Slice s : slices)
        {
            sb.append(s.getTopLeft().getY())
              .append(' ')
              .append(s.getTopLeft().getX())
              .append(' ')
              .append(s.getBottomRight().getY())
              .append(' ')
              .append(s.getBottomRight().getX())
              .append('\n');
        }
        
        return sb.toString();
    }
}
