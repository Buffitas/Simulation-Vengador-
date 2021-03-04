import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author Alvaro Moreno and Emil Cechelt
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // Whether the animal is infected or not.
    protected boolean infected;
    
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();
    //Creates a posibility of being infected.
    private Disease disease = new Disease();
    
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     * @param time An instance of Time so the animalcan acces weather.
     * @param weather An instance of Weatherso the animal can acces weather. 
     */
    abstract public void act(List<Animal> newAnimals, Time time, Weather weather);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
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
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
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
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * The animal becomes infected
     */
    public void setInfected()
    {
        infected = true;
    }
    
    /**
     * Return if animal is infected.
     * @return if animal is infected.
     */
    public boolean isInfected()
    {
        return infected;
    }
    
   /**
     * Look for animal adjacent to the current location.
     * Only the first live animal is infected.
     */
    public void infect()
    {
        List<Location> adjacent = getField().adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Animal) {
                Animal newInfected = (Animal) animal;
                newInfected.setInfected();
                }
            }
        
    }
}
