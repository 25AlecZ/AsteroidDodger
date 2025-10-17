package game;
import java.awt.*;

/**
 * Identifies the methods needed for various game objects such as asteroid, player, 
 * etc.
 * 
 */
public interface GameObject {
	
	/**
	 * How object moves around the screen
	 * 
	 * @param screenWidth dimensions of screen (will always be square)
	 */
    public void move(int screenWidth);
    
    
    /**
     * gets the shape
     * @return shape of object
     */
    public Polygon getShape();
    
    /**
     * draws object on screen
     * @param g graphics object
     */
    public void draw(Graphics g);
    
    /**
     * Checks if object is active
     * @return true if active, else false
     */
    public boolean isActive();
    
    /**
     * setter for if object is active
     * @param active true if object is active, otherwise false
     */
    public void setActive(boolean active);
}