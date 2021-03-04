import java.util.Random;

/**
 * Weather can change during the time.
 * There are three states of weather.
 * All objects must have access to the weather.
 * It influences animal's behaviour
 * 
 * @author Alvaro Moreno and Emil Cechelt
 */
public class Weather
{
    // Probability of being a rainy day.
    private double RAIN_CHANGE_PROBABILITY = 0.1;
    // Probability of being a foggy day.
    private double FOG_CHANGE_PROBABILITY = 0.1;

    private boolean sunnyDay;
    private boolean rainyDay;
    private boolean foggyDay;
    
    // Used to convert the weather into string
    private String weatherString;
    
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Generates a random weather.
     * It can be foggy, rainy or sunny.
     * Sets true the weather condition and false the other two.
     */
    public void setWeather()
    {
            //initially should be sunny.
            sunnyDay = true;
            rainyDay = false;
            foggyDay = false;
            weatherString = "Sunny";
            // Day becomes rainy.
            if (rand.nextDouble() <= RAIN_CHANGE_PROBABILITY){
                rainyDay = true;
                sunnyDay = false;
                weatherString = "Rainy";
            }
            // Day becomes foggy.
            else if(rand.nextDouble() <= FOG_CHANGE_PROBABILITY){
                foggyDay = true;
                sunnyDay = false;
                weatherString = "Foggy";
            }
    }
    
    /**
     * Method used to get a srting determining the weather type.
     * 
     * @return text defining the weather.
     */
    public String showWeather()
    {
        return weatherString;
    }
    
    /**
     * Checks if it is a sunny day.
     * 
     * @return true if it is a sunny day.
     */
    public boolean isSunny()
    {
        return sunnyDay;
    }
    
    /**
     * Checks if it is a foggy day.
     * 
     * @return true if it is a foggy day.
     */
    public boolean isFoggy()
    {
        return foggyDay;
    }
    
    /**
     * Checks if it is a foggy day.
     * 
     * @return true if it is a foggy day.
     */
    public boolean isRainy()
    {
        return rainyDay;
    }
}