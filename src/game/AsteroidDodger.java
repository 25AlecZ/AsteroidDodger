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
	
  public AsteroidDodger() {
    super("YourGameName!",1000,1000);
    this.setFocusable(true);
	this.requestFocus();
	this.a = new Asteroid(200, 200, 16, 8);
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
            
            
            Point[] pts = shape.getPoints();
            int[] x = new int[pts.length];
            int[] y = new int[pts.length];
            for (int i = 0; i < pts.length; i++) {
                x[i] = (int) pts[i].x;
                y[i] = (int) pts[i].y;
            }
            
            a.move(1000, new Point(50, 200));
            brush.fillPolygon(x, y, pts.length);

    	
    	
    	brush.drawString("Counter is " + counter,10,10);
  }
	
	public static void showBoard(Graphics brush) {
		for(int r = 0; r < 10; r++) {
			for(int c = 0; c < 10; c++) {
				if(r % 2 == 1) {
					brush.setColor(Color.BLACK);
				} else {
					brush.setColor(Color.GRAY);
				}
		    	brush.fillRect(r*100, c*100, 100, 100);
			}
		}
	}
  
	public static void main (String[] args) {
   		AsteroidDodger a = new AsteroidDodger();
		a.repaint();
  }
}
