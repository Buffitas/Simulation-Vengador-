import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a deer.
 * Deers age, move, breed, eat and die.
 * 
 * @author Alvaro Moreno and Emil Cechelt
 */
public class Deer extends Animal
{
    // Characteristics shared by all deers (class variables).

    // The age at which a deer can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a deer can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a deer breeding.
    private static final double BREEDING_PROBABILITY = 0.43;
    //The probability of being a male
    private static final double MALE_PROBABILITY = 0.5;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single plant. In effect, this is the
    // number of steps a deer can go before it has to eat again.
    private static final int PLANT_FOOD_VALUE = 10;
    
    
    // Individual characteristics (instance fields).

    // The deer's age.
    private int age;
    // The deer's gender. 
    private boolean isMale;
    // The deer's food level.
    private int foodLevel;
    
    /**
     * Create a new deer. A deer may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the deer will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param randomGender If the dear's gender is random.
     */
    public Deer(boolean randomAge, Field field, Location location, boolean randomGender)
    {
        super(field, location);
        // Presets the gender
        isMale = true;
        // Presets the food level
        foodLevel = 10;
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        if(randomGender) {
            if (rand.nextDouble() <= MALE_PROBABILITY){
                isMale = false;
            }
        }
    }

    /**
     * This is what the deer does most of the time: it checks if
     * the conditions are optimal to act, it looks for food (plants).
     * In the process, it might breed, die of hunger,
     * die of old age or infect other animals.
     * @param newDeers A list to return newly born deers.
     * @param time An instance of Time so the animal can acces weather.
     * @param weather An instance of Weather so the animal can acces weather.
     */
    public void act(List<Animal> newDeers, Time time, Weather weather)
    {
        //Deers act during the day despite it is sunny or foggy.
        if (time.isDay() && (weather.isSunny() || weather.isFoggy())){
            incrementAge();
            // They infect in case they are infected
            if(isAlive() && isInfected()) infect();
            if(isAlive()) {
                giveBirth(newDeers);            
                // Move towards a source of food if found.
                Location newLocation = findFood();
                if(newLocation == null) { 
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                // See if it was possible to move.
                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
            }
        }
    }

    /**
     * Increase the age.
     * This could result in the deer's death.
     */
    private void incrementAge()
    {   age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Look for plants adjacent to the current location.
     * Only the first live plant is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Plant) {
                Plant plant = (Plant) animal;
                if(plant.isAlive()) { 
                    plant.setDead();
                    foodLevel = PLANT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this deer is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newDeers A list to return newly born deer.
     */
    private void giveBirth(List<Animal> newDeers)
    {
        // New deer are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Deer young = new Deer(false, field, loc, true);
            newDeers.add(young);
            if (infected) {
                young.setInfected();
            }
        }
    }

    /**
     * Look for deers adjacent to the current location.
     * Only the first live deer is taken into consideration.
     * @return If the deer has an adjacent deer with different gender.
     */
    private boolean findPartner()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Deer) {
                Deer deer = (Deer) animal;
                if(isMale != deer.getGender() && rand.nextDouble() <= BREEDING_PROBABILITY) { 
                    return true;
                }
                else { 
                    return false; 
                }
            }
        }
        return false;
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && findPartner()) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A deer can breed if it has reached the breeding age.
     * @return true if the deer can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE && isMale == false;
    }

    /**
     * A deer can breed if it has reached the breeding age.
     * @return true if the deer is male.
     */
    public boolean getGender()
    {
        return isMale;
    }
}
