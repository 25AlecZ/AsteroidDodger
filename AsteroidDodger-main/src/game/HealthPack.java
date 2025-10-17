package game;

import java.awt.*;

/**
 * Works as a power-up to restore health points to the player character. Is depicted 
 * using a polygon on the canvas. 
 * 
 *  @author Alec, Vedant, and Saanvi
 */
public class HealthPack implements GameObject {

    private Polygon shape;   
    private int size;
    private boolean active = true;

    /**
     * Create a health pack centered at (x,y)
     * @param x coordinate
     * @param y coordinate
     * @param size drawing size
     */
    public HealthPack(int x, int y, int size) {
        this.size = size;
        int h = size/2;

        Point[] box = {
            new Point(-h, -h), new Point(h, -h),
            new Point(h,  h),  new Point(-h,  h),
            new Point(-h, -h)
        };
        this.shape = new Polygon(box, new Point(x, y), 0);
    }

    /**
	 * Draws the healthpack power-up on the canvas, checking if it's active first, 
	 * making it red, and filling the rectangle. 
	 * 
	 * @param Graphics g
	 */
    @Override
    public void draw(Graphics g) {
        if (!active) return;

        int x1 = (int) shape.position.x;
        int y1 = (int) shape.position.y;

        int bar = Math.max(6, size / 3);
        int half = size/2;

        g.setColor(Color.RED);
        g.fillRect(x1 - bar / 2, y1 - half, bar, size);
        g.fillRect(x1 - half, y1 - bar / 2, size, bar);
    }
    /**
     * Health Pack doesn't move
     */
    @Override
    public void move(int screenWidth) {
    }

    /**
	 * returns the shape
	 * 
	 * @return  Polygon
	 */
    @Override
    public Polygon getShape() {
        return shape;
    }

    /**
	 * returns if the HealthPack is active or not
	 * @return boolean
	 */
    @Override
    public boolean isActive() {
    	return active;
    }

    /**
     * setter for if object is active
     * @param active true if object is active, otherwise false
     */
    @Override
    public void setActive(boolean active) {
    	this.active = active;
    }
    
}