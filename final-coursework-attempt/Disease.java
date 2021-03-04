import java.util.Random;

/**
 * Disease contains the probability
 * of creating an infected animal
 * 
 * @author Alvaro Moreno and Emil Cechelt
 */
public class Disease
{
    // Probability of being infected
    private double DISEASE_PROBABILITY = 0.001;

    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Generates a random number and compares too the
     * given probability.
     * 
     * @return if animal gets infected.
     */
    public boolean getDisease()
    {
        return rand.nextDouble() <= DISEASE_PROBABILITY;
    }
}