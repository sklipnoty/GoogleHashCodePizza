package solver;

import domain.Pizza;

public class RandomSampling
{
    private final PizzaSolver pizzaSolver;
    private final int numIter;
    private PizzaSolution currentSolution;

    public RandomSampling(Pizza pizza, int numIter)
    {
        this.pizzaSolver = new PizzaSolver(pizza);
        this.numIter = numIter;
        this.currentSolution = pizzaSolver.generateRandomSolution(); // initial arbitrary valid solution
    }
    
    public PizzaSolution getSolution()
    {
        return currentSolution;
    }
    
    public void sample()
    {
        for (int i = 0; i < numIter; i++)
        {
            PizzaSolution newSolution = pizzaSolver.generateRandomSolution();
            
            // update best solution seen so far if a better one is found
            if (newSolution.getScore() > currentSolution.getScore())
            {
                currentSolution = newSolution;
            }
            
            System.out.println("[" + i + "] Best: " +  currentSolution.getScore() + " Current : " + newSolution.getScore());
        }
    }
}
