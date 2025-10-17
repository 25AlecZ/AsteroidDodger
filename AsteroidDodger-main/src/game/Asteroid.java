package game;

import java.awt.*;
import java.awt.Color;

/**
 * Represents the asteroids used as the obstacles in asteroid dodger that the player must 
 * maneuver around
 *
 * <p>
 * The polygon is used to store the dimensions of the asteroid. The asteroid must be active for it to appear. 
 * </p>
 * @author Alec, Vedant, and Saanvi
 */
public class Asteroid implements GameObject{
	
	private Polygon shape;
	private final Color COLOR = Color.GRAY;
	private int xSpeed;
	private int ySpeed;
	private boolean active = true;
	private int collideCooldown = 0;
	private static final int COOLDOWN_FRAMES = 5;
	
	/**
     * Create an asteroid using points in an array
     * @param x coordinate
     * @param y coordinate
     * @param x speed
     * @param y speed
     */
	public Asteroid(int x, int y, int xSpeed, int ySpeed) {
		Point[] points = {new Point(0, 10), new Point(7, 0), new Point(17, 0), new Point(24, 10), new Point(24, 20), new Point(17, 30), new Point(7, 30), new Point(0, 20)};
		this.shape = new Polygon(points, new Point(x, y), 0);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	/**
	 * Moves the asteroid based on the x and y speeds. Ensures that the position 
	 * is not outside of the screenwidth. Uses tickCoolDown to ensure a player doesn't
	 * get penalized for hitting the same asteroid repeatedly in a short amount of time
	 * 
	 * @param screenWidth
	 */
	@Override
	public void move(int screenWidth) {
        shape.position.x += xSpeed;
        shape.position.y += ySpeed;
        if (shape.position.x > screenWidth || shape.position.x < 0) {
        	xSpeed *= -1;
        } 
        
        if(shape.position.y > screenWidth || shape.position.y < 0) {
        	ySpeed *= -1;
        }
        tickCooldown();
    }
	
	/**
	 * returns the Polygon associated with the asteroid
	 * 
	 * @return Polygon
	 */
	@Override
	public Polygon getShape() {
		return shape;
	}
	
	/**
	 * Sets the color to gray and draws the polygon based on its points
	 * 
	 * @param Graphics g
	 */
	@Override
	public void draw(Graphics g) {
	    g.setColor(Color.GRAY);
	    Point[] pts = shape.getPoints();
	    int[] xs = new int[pts.length], ys = new int[pts.length];
	    for (int i = 0; i < pts.length; i++) {
	        xs[i] = (int) pts[i].x;
	        ys[i] = (int) pts[i].y;
	    }
	    g.fillPolygon(xs, ys, pts.length);
	}

	/**
	 * returns if the asteroid is active or not
	 * 
	 * @return boolean active
	 */
	@Override
    public boolean isActive() {
		return active;
	}

	/**
	 * assigns active to the parameter active
	 * 
	 * @param active
	 */
    @Override
    public void setActive(boolean active) {
    	this.active = active;
    }
    
    /**
	 * returns the Color associated with the asteroid
	 * 
	 * @return Color
	 */
    public Color getColor() {
		return this.COLOR;
	}
	
    /**
	 * returns if collideCooldown has ended; if it's 0 now
	 * 
	 * @return boolean
	 */
	public boolean canCollide() {
	    return collideCooldown == 0;
	}

	/**
	 * lowers the collideCooldown to end the cooldown 
	 * 
	 */
	public void tickCooldown() {
	    if (collideCooldown > 0) collideCooldown--;
	}
	
	/**
	 * makes the asteroid switch directions after a collision
	 * assigns the collideCooldown to a non-zero number
	 * 
	 */
	public void collideReverse()
	{
		xSpeed *= -1;
		ySpeed *= -1;
		collideCooldown = COOLDOWN_FRAMES;
	}
	
	/**
	 * 
	 * moves the asteroid 5 times the original speed
	 * 
	 */
	public void moveAfterReverse() {
	    shape.position.x += 5 * xSpeed;
	    shape.position.y += 5 * ySpeed;
	}
}
