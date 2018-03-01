package solver;

import domain.Pizza;

public class SimulatedAnnealing
{
    private static final int NUM_STEPS = 1000;
    private final PizzaSolver pizzaSolver;
    private PizzaSolution currentSolution;

    public SimulatedAnnealing(Pizza pizza)
    {
        this.pizzaSolver = new PizzaSolver(pizza);
        this.currentSolution = pizzaSolver.generateRandomSolution(); // initial arbitrary valid solution
    }
    
    public PizzaSolution getSolution()
    {
        return currentSolution;
    }
    
    public void anneal()
    {
        // https://pdfs.semanticscholar.org/e893/4a942f06ee91940ab57732953ec6a24b3f00.pdf
        // https://en.wikipedia.org/wiki/Simulated_annealing
        
        // TODO
        for (int i = 0; i < NUM_STEPS; i++)
        {
            
        }
    }
}
