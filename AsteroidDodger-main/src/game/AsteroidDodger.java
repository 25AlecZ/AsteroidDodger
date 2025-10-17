package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.util.Random;

import game.Point;

/**
 * Control center of the game. Controls the explosion effect animation at the end of 
 * the game, checks for collisions, and contains our inner classes.
 * 
 *  @author Alec, Vedant, and Saanvi
 */
class AsteroidDodger extends Game {
	static int rotation = 0;
	//keep asteroid instances outside of paint method for rotation
	private Asteroid[] asteroids;
	private Player p;
	private boolean exploding = false;
	private int explosionFrame = 0;
	private HealthPack healthPack = null;
	private int lastPackSeconds = -10;   // so one can spawn at start after 10s
	
	/**
	 * Defines the methods needed to be performed for an explosion to happen
	 * 
	 *  @author Alec, Vedant, and Saanvi
	 */
	private interface explosion {
        /** 
         * advances one frame, return true when done
         * 
         * @return true when process finishes
         */
        public boolean tick();
        /** 
         * draw the effect for the current frame 
         * */
        public void draw(Graphics g);
    }
	
	/**
	 * Functional interface for lambda expression
	 */
	public interface Action {
	    void execute();
	}
	
    private explosion explosionEffect = null;
    
    /**
     * Stores the asteroids made at the start of the game, the player character, and
     * instantiates a KeyListener
     * 
     */
    public AsteroidDodger() {
		super("Asteroid Dodger", 1000, 1000);
		this.setFocusable(true);
		this.requestFocus();
		this.asteroids = new Asteroid[] { 
				new Asteroid(100, 150, 5, 7), 
				new Asteroid(300, 400, 7, 7),
				new Asteroid(500, 250, 7, 6), 
				new Asteroid(800, 600, 9, 6), 
				new Asteroid(200, 700, 6, 7),
				new Asteroid(900, 300, 10, 5), 
				new Asteroid(400, 850, 8, 6), 
				};

		this.p = new Player(500, 500, 15, 10);
		this.addKeyListener(this.p);
  }
  
    /**
	 * 
	 * Draws the different components of the game include asteroid and player based 
	 * on the stage of the game. It only moves the asteroids if the game isn't over, 
	 * and enacts the explosion if the player is dead. 
	 * 
	 * @param brush
	 */
  	public void paint(Graphics brush) {
  		brush.setColor(Color.black);
    	brush.fillRect(0,0,width,height);
  		if(!p.isGameOver()) {
  			if (explosionEffect == null)
  			{
	  			moveAsteroids();
	  			if(collisionCheck()) {
	  				p.decrementHealth();
	  			} else {
	  				moveAsteroids();
	  			}
  			}

  	        // Apply damage only if player is not invincible
  			
  			if (p.getHealth() <= 0 && explosionEffect == null) {
                exploding = true;
                explosionFrame = 0;

                final int FRAMES = 90;
                //anonymous inner class
                explosionEffect = new explosion() {
                    int frame = 0;

                    /**
                	 * Creates the game's clock; ensures that the frames are being 
                	 * iterated, so that if the frame count goes over, then the game 
                	 * will be over
                	 * 
                	 */
                    @Override
                    public boolean tick() {
                        explosionFrame = frame;
                        frame++;
                        if (frame > FRAMES) {
                            exploding = false;
                            p.setGameOver();
                            return true;
                        }
                        return false;
                    }

                    /**
                	 * Draws the polygon on the canvas
                	 * @param Graphics g
                	 */
                    @Override
                    public void draw(Graphics g) {
                        int alpha = Math.max(0, 255 - frame * 3);
                        g.setColor(new Color(255, 180, 0, alpha));
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };
            }
  			
  			
  			p.move(1000);
  			
  			int currentSeconds = p.getTimeLived() / 60;
			if (explosionEffect == null && 
					((healthPack == null) || !healthPack.isActive()) && 
					currentSeconds - lastPackSeconds >= 10) {
				Random r = new Random();
				int x = 50 + r.nextInt(900);
				int y = 50 + r.nextInt(900);
				healthPack = new HealthPack(x, y, 40);
				lastPackSeconds = currentSeconds;
			}
			
			if (explosionEffect == null && healthPack != null && 
					healthPack.isActive() && p.isActive()) {
				if (healthPack.getShape().collides(p.getShape())) {
					Action heal = () -> p.incrementHealth();
					heal.execute();
					healthPack.setActive(false);
					lastPackSeconds = currentSeconds;
				}
			}
  			
  			for(Asteroid a : asteroids) {
  				if (explosionEffect == null)
  				{
  					a.getShape().rotation+=5;
  				}
  				if (a.isActive())
  				{
  					a.draw(brush);
  				}
  			}
  			
  			if (healthPack != null && healthPack.isActive()) {
				healthPack.draw(brush);
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
  			EndScreen e = new EndScreen(350, 350);
  			e.draw(brush);
  			
  			
  		}
  		if (explosionEffect != null) {
  		    explosionEffect.draw(brush);
  		    if (explosionEffect.tick())
  		    {
  		    	explosionEffect = null;
  		    }
  		}
  	}
  	
  	/**
	 * Returns if the asteroid and player character have collided. If they did, then 
	 * it makes the asteroid move appropriately 
	 * 
	 * @param boolean
	 */
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
  	
  	/**
	 * Moves each of the asteroids stored in the array one by one
	 * 
	 */
  	private void moveAsteroids() {
  		for(Asteroid a : asteroids) {
  			a.move(1000);
  			
  		}
  	}
  	
  	private class EndScreen {
  		private Polygon shape;
  		private double score;
  		
  		public EndScreen(int x, int y) {
  			Point[] pts = {new Point(0,300), new Point(0,0), new Point(300, 0), new Point(300,300)};
  			this.shape = new Polygon(pts, new Point(x,y), 0);
  			this.score = ((p.getTimeLived()/60.0) * 20)/asteroids.length;
  		}
  		
  		public void draw(Graphics brush) {
  			brush.setColor(Color.GRAY);
  		    Point[] pts = shape.getPoints();
  		    int[] xs = new int[pts.length], ys = new int[pts.length];
  		    for (int i = 0; i < pts.length; i++) {
  		        xs[i] = (int) pts[i].x;
  		        ys[i] = (int) pts[i].y;
  		    }
  		    
  		    brush.fillPolygon(xs, ys, pts.length);
  		    brush.setColor(Color.WHITE);
			brush.drawString("Game Over", 470, 470);
			brush.drawString("Time Alive: " + p.getTimeLived()/60 + "." + 
					p.getTimeLived() % 60, 450, 500);
			brush.drawString("Score: " + Math.round(score * 100.0) / 100.0, 470, 530);
  		}
  	}
  	
  	/**
	 * Creates the AteroidDodger to begin the game
	 * 
	 * @param args
	 */
	public static void main (String[] args) {
   		AsteroidDodger a = new AsteroidDodger();
		a.repaint();
  }
}
