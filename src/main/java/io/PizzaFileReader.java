package io;

import domain.Pizza;
import domain.PizzaIngredient;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Google hashcode example
 * @author Sklipnoty
 */
public class PizzaFileReader {

    public static Pizza readPizzaFile(String name) {
        try {
            Path path = Paths.get(name);
            Path absolutePath = path.toAbsolutePath();
            List<String> lines = Files.readAllLines(absolutePath);

            String dimensions = lines.get(0);
            lines.remove(0);
            
            String[] dim = dimensions.split(" ");
            int row = Integer.valueOf(dim[0]);
            int col = Integer.valueOf(dim[1]);
            
            PizzaIngredient[][] pizzaIng = new PizzaIngredient[col][row];
            
            for (int i = 0; i < lines.size(); i++) {
                for(int j = 0; j < lines.get(i).length(); j++) {
                    pizzaIng[j][i] = PizzaIngredient.valueOf("" +lines.get(i).charAt(j));
                }
            }
            
            return new Pizza(pizzaIng, col, row, Integer.valueOf(dim[3]), Integer.valueOf(dim[2]));
            
        } catch (IOException ex) {
            Logger.getLogger(PizzaFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
