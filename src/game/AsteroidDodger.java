package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class AsteroidDodger extends Game {
	static int counter = 0;
	static int rotation = 0;
	//keep asteroid instances outside of paint method for rotation
	private Asteroid[] asteroids;
	private Player p;
	
	
  public AsteroidDodger() {
    super("YourGameName!",1000,1000);
    this.setFocusable(true);
	this.requestFocus();
	this.asteroids = new Asteroid[] {new Asteroid(100, 150, 5, 7), new Asteroid(300, 400, 7, 7),
		  /*  new Asteroid(500, 250, 23, 18),
		    new Asteroid(800, 600, 28, 16),
		    new Asteroid(200, 700, 17, 21),
		    new Asteroid(900, 300, 30, 15),
		    new Asteroid(400, 850, 25, 19),
		    new Asteroid(600, 500, 29, 17),
		    new Asteroid(750, 200, 32, 14), 
		    new Asteroid(100, 900, 26, 18) */
		    };
	
	this.p = new Player(500,500,100,15);
	
	
	this.addKeyListener(this.p);
  }
  
  
  	public void paint(Graphics brush) {
  		brush.setColor(Color.black);
    	brush.fillRect(0,0,width,height);
  		if(!p.isGameOver()) {
  			
  			if(collisionCheck()) {
  				p.decrementHealth();
  			} else {
  				moveAsteroids();
  				
  			}

  	        // Apply damage only if player is not invincible
  	        
  			
  			if(p.getHealth() <= 0) {
  				p.setGameOver();
  			}
  			
  			
  			
  			p.move();
  			
  			brush.setColor(Color.GRAY);
  			for(Asteroid a : asteroids) {
  				a.getShape().rotation+=5;
  				draw(a.getShape().getPoints(), brush);
  			}
  			
  			brush.setColor(Color.GRAY);
            draw(p.getShape().getPoints(), brush);
            
  			if(counter%60 == 0) {
            	p.incrementTimer();
            }
  			
  			 brush.drawString("time lived: " + p.getTimeLived(), 10, 50);
  	        brush.drawString("lives: " + p.getHealth(), 10, 30);
  	    	brush.drawString("Counter is " + counter,10,10);
  			
  		} else {
  			brush.setColor(Color.WHITE);
  			brush.drawString("Game Over", 500, 500);
  		}
  	}
  	
  	
  	private  boolean collisionCheck() {
  		for(Asteroid a : this.asteroids) {
  			if(a.getShape().collides(p.getShape())) {
  				return true;
  			}
  		}
  		return false;
  	}
  	
  	private void moveAsteroids() {
  		for(Asteroid a : asteroids) {
  			a.move(1000, p.getShape());
  			
  		}
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
