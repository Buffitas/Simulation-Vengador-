
/**
 * Time Divides days in two steps, day and night.
 * It can be accesed by animals to check the time.
 *
 * @author Alvaro Moreno and Emil Cechelt
 */
public class Time
{
    //Boolean value to give time, true = day, false = night.
    private boolean isDay;
    // Used to convert the time of the day into a string.
    private String dayTime;
    
    /**
     * Depending on the step number it is day or night.
     * Begin process with day
     */
    public void setTime(int steps)
    {
        if ( steps % 2 == 0){
            isDay = true;
            dayTime = "day";
        }
        else{
            isDay = false;
            dayTime = "night";
        }
    }
    
    /**
     * Checks the time of the day.
     * 
     * @return true if it is still day and false if it is night.
     */
    public boolean isDay()
    {
        return isDay;
    }
    
    /**
     * Method used to get a sting determining the time of the day.
     * 
     * @return text defining the period of the day.
     */
    public String showTime()
    {
        return dayTime;
    }
}