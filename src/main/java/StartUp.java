
import domain.Pizza;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import solver.RandomSampling;

public class StartUp
{
    private static final String PIZZA_FILE = "resources/big.in";

    public static void main(String[] args) throws IOException
    {
        Scanner pizzaInput = new Scanner(Paths.get(PIZZA_FILE).toAbsolutePath());
        Pizza pizza = new Pizza(pizzaInput);
        
//        SimulatedAnnealing solver = new SimulatedAnnealing(pizza);
//        solver.anneal();

        RandomSampling solver = new RandomSampling(pizza, 2);
        solver.sample();
        
        System.out.printf("Solution (score = %d):\n", solver.getSolution().getScore());
        System.out.println(solver.getSolution());
        
        System.out.println("test");
    }
}
