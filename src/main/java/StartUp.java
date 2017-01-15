
import domain.Pizza;
import domain.shape.Shape;
import domain.shape.Shaper;
import io.PizzaFileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Google hashcode example
 *
 * @author Sklipnoty
 */
public class StartUp {

    private static String FILENAME = "resources/small.in";

    public static void main(String args[]) {
        System.out.println("Starting the pizza slicer!");
           
         Pizza pizza = PizzaFileReader.readPizzaFile(FILENAME);
         System.out.println(pizza);
        
      //   pizza.slice();
         Shaper s = new Shaper(5);
         List<Shape> shapes = s.getValidShapes(0, 0, 6, 7);
         System.out.println(shapes);
    }

}
