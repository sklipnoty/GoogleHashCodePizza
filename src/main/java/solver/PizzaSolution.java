package solver;

import domain.Pizza;
import domain.Slice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PizzaSolution {

    private final Set<Slice> slices;
    private final Pizza pizza;
    private int score;

    public PizzaSolution(Pizza pizza) {
        this.slices = new HashSet<>();
        this.pizza = pizza;
        this.score = 0;
    }

    public void addSlice(Slice slice) {
        slices.add(slice);
        score += slice.getArea();
    }

    public void removeSlice(Slice slice) {
        if (slices.remove(slice)) {
            score -= slice.getArea();
        }
    }

    public boolean isOverlap(Slice slice) {
        for (Slice s : slices) {
            if (s.overlapsWith(slice)) {
                return true;
            }
        }

        return false;
    }

    public int getScore() {
        return score;
    }

    public void makeOutput(String name) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("output.txt"));
            pw.println(slices.size());
            for (Slice slice : slices) {
                pw.write(slice.getTopLeft().getY() + " " + slice.getTopLeft().getX());
                pw.write(" " + slice.getBottomRight().getY() + " " + slice.getBottomRight().getX());
                pw.println();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PizzaSolution.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Slice s : slices) {
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
