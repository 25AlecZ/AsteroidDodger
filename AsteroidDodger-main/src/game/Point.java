package game;

/*
CLASS: Point
DESCRIPTION: Ah, if only real-life classes were this straight-forward. We'll
             use 'Point' throughout the program to store and access 
             coordinates.
*/

public class Point implements Cloneable {
  public double x,y;
  
  /**
   * Create a point at (x,y)
   * @param x coordinate
   * @param y coordinate
   */
  public Point(double inX, double inY) { x = inX; y = inY; }
  
  //added sjp
  
  /**
   * return's point's x coordinate
   * @return x coordinate
   */
  public double getX(){ return x;}
  
  /**
   * return's point's y coordinate
   * @return y coordinate
   */
  public double getY(){ return y;}
  
  /**
   * sets point's x coordinate
   * 
   * @param x coordinate
   */
  public void setX(double x){ this.x = x;}
  
  /**
   * sets point's y coordinate
   * 
   * @param y coordinate
   */
  public void setY(double y){ this.y = y;}
  
  /**
   * create's a new point at the same location
   * 
   */
  public Point clone() {
	  return new Point(x, y);
  }
}