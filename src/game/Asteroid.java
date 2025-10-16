package game;

import java.awt.*;
import java.awt.Color;

public class Asteroid implements gameObject{
	
	private Polygon shape;
	private final Color COLOR = Color.GRAY;
	private int xSpeed;
	private int ySpeed;
	private boolean active = true;
	private int collideCooldown = 0;
	private static final int COOLDOWN_FRAMES = 5;
	
	public Asteroid(int x, int y, int xSpeed, int ySpeed) {
		Point[] points = {new Point(0, 10), new Point(7, 0), new Point(17, 0), new Point(24, 10), new Point(24, 20), new Point(17, 30), new Point(7, 30), new Point(0, 20)};
		this.shape = new Polygon(points, new Point(x, y), 0);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
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
	
	@Override
	public Polygon getShape() {
		return shape;
	}
	
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

	@Override
    public boolean isActive() {
		return active;
	}

    @Override
    public void setActive(boolean active) {
    	this.active = active;
    }
    
    public Color getColor() {
		return this.COLOR;
	}
	
	public boolean canCollide() {
	    return collideCooldown == 0;
	}

	public void tickCooldown() {
	    if (collideCooldown > 0) collideCooldown--;
	}
	public void collideReverse()
	{
		xSpeed *= -1;
		ySpeed *= -1;
		collideCooldown = COOLDOWN_FRAMES;
	}
	public void moveAfterReverse() {
	    shape.position.x += xSpeed;
	    shape.position.y += ySpeed;
	}
}
