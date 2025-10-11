package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;

class AsteroidDodger extends Game {
	static int counter = 0;
	static int rotation = 0;
	//keep asteroid instances outside of paint method for rotation
	private Asteroid a;
	private Player p;
	
  public AsteroidDodger() {
    super("YourGameName!",1000,1000);
    this.setFocusable(true);
	this.requestFocus();
	this.a = new Asteroid(200, 200, 16, 8);
	this.p = new Player(500,500,5,15);
	this.addKeyListener(this.p);
  }
  
	public void paint(Graphics brush) {
    	brush.setColor(Color.black);
    	brush.fillRect(0,0,width,height);
    	
    	// sample code for printing message for debugging
    	// counter is incremented and this message printed
    	// each time the canvas is repainted
    	counter++;
    	brush.setColor(Color.white);
    	

            // Apply starting position and rotation
    		
            Polygon shape = a.getShape();
            brush.setColor(a.getColor());
            
            shape.rotation += 5;
            
            
            draw(shape.getPoints(), brush);
            draw(p.getShape().getPoints(), brush);
            a.move(1000, new Point(50, 200));
            

    	
    	
        
    	brush.drawString("Counter is " + counter,10,10);
  }
	
	public static void draw(Point[] pts, Graphics brush) {
		 int[] x = new int[pts.length];
            int[] y = new int[pts.length];
            for (int i = 0; i < pts.length; i++) {
                x[i] = (int) pts[i].x;
                y[i] = (int) pts[i].y;
            }
            
            brush.fillPolygon(x, y, pts.length);
	}
  
	public static void main (String[] args) {
   		AsteroidDodger a = new AsteroidDodger();
		a.repaint();
  }
}