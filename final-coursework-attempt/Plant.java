import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of Plants.
 * 
 * @author Alvaro Moreno and Emil Cechelt
 */
public class Plant
{
    // Whether the plant is alive or not.
    private boolean alive;
    // The plant's field.
    private Field field;
    // The plant's position in the field.
    private Location location;
    
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 400;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.5;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    
    // The plant's age.
    private int age;

    /**
     * Create a new Plant at location in field.
     * 
     * @param randomAge If true, the plant will have random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plant(boolean randomAge, Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        age = 0;
        if (randomAge){
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    /**
     * Make this Plant act.
     * The plants grow old and reproduce.
     * @param newPlants A list to receive newly born Plants.
     * @param time An instance of Time so the animalcan acces weather.
     */
     public void act(List<Plant> newPlants, Time time)
    {
        //plants grow during the night at any weather.
        if (!time.isDay()){
            incrementAge();
            if(isAlive()) {
                reproduce(newPlants);
            }
        }
    }

    /**
     * Check whether the Plant is alive or not.
     * @return true if the Plant is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the Plant is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        
        if(age >= MAX_AGE) {
            setDead();
        }
        
    }
    
    /**
     * Return the Plant's location.
     * @return The Plant's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the Plant at the new location in the given field.
     * @param newLocation The Plant's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the Plant's field.
     * @return The Plant's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Check whether or not this plant is to give birth at this step.
     * New births will be grow into free adjacent locations.
     * @param newPlants A list to return newly born plants.
     */
    private void reproduce(List<Plant> newPlants)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Plant young = new Plant(true, field, loc);
            newPlants.add(young);
        }
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int seeds = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            seeds = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return seeds;
    }
    
    /**
     * A plant can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}