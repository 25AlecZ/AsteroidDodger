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
	static int rotation = 0;
	//keep asteroid instances outside of paint method for rotation
	private Asteroid[] asteroids;
	private Player p;
	private boolean exploding = false;
	private int explosionFrame = 0;
	
  public AsteroidDodger() {
    super("Asteroid Dodger",1000,1000);
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
	
	this.p = new Player(500,500,1,15);
	
	
	this.addKeyListener(this.p);
  }
  
  
  	public void paint(Graphics brush) {
  		brush.setColor(Color.black);
    	brush.fillRect(0,0,width,height);
  		if(!p.isGameOver()) {
  			if (!exploding)
  			{
	  			moveAsteroids();
	  			if(collisionCheck()) {
	  				p.decrementHealth();
	  			} else {
	  				moveAsteroids();
	  			}
  			}

  	        // Apply damage only if player is not invincible
  	        
  			
  		    if (p.getHealth() <= 0 && !exploding) {
  		        exploding = true;
  		        explosionFrame = 0;

  		        new Thread() {
  		            @Override
  		            public void run() {
  		                for (int i = 0; i <= 90; i++) {
  		                    explosionFrame = i;
  		                    try { Thread.sleep(33); } catch (InterruptedException ignored) {}
  		                    repaint();
  		                }
  		                exploding = false;
  		                p.setGameOver();
  		            }
  		        }.start();
  		    }
  			
  			p.move(1000);
  			
  			for(Asteroid a : asteroids) {
  				if (a.isActive() && !exploding)
  				{
  					a.getShape().rotation+=5;
  				}
  				a.draw(brush);
  			}
  			
  			if (p.isActive() && !exploding)
  			{
	  			p.draw(brush);
  			}
            
  			if(!exploding) {
            	p.incrementTimer();
            }
  			
  			brush.setColor(Color.WHITE);
  			brush.drawString("Time Alive: " + p.getTimeLived()/60, 10, 40);
  	        brush.drawString("Lives Remaining: " + p.getHealth(), 10, 20);
  	        
  			
  		} else {
  			brush.setColor(Color.WHITE);
  			brush.drawString("Game Over", 460, 500);
  			brush.drawString("Time Alive: " + p.getTimeLived()/60 + "." + 
  					p.getTimeLived() % 60, 440, 530);
  		}
  		if (exploding) {
  		    int alpha = Math.max(0, 255 - explosionFrame * 3);
  		    brush.setColor(new Color(255, 180, 0, alpha));
  		    brush.fillRect(0, 0, getWidth(), getHeight());
  		}
  	}
  	
  	
  	private boolean collisionCheck() {
  		for(Asteroid a : this.asteroids) {
  			if(a.getShape().collides(p.getShape())) {
  				a.collideReverse();
  				a.moveAfterReverse();
  				return true;
  			}
  		}
  		return false;
  	}
  	
  	private void moveAsteroids() {
  		for(Asteroid a : asteroids) {
  			a.move(1000);
  			
  		}
  	}
	
	public static void main (String[] args) {
   		AsteroidDodger a = new AsteroidDodger();
		a.repaint();
  }
}
