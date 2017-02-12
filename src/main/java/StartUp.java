
import domain.Pizza;
import io.PizzaFileReader;

/**
 * Google hashcode example
 *
 * @author Sklipnoty
 */
public class StartUp {

    private static String FILENAME = "resources/big.in";

    public static void main(String args[]) {
        System.out.println("Starting the pizza slicer!");
           
         Pizza pizza = PizzaFileReader.readPizzaFile(FILENAME);
         System.out.println(pizza);
        
         pizza.slice();
    }

}
