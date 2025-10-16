package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.util.Random;

class AsteroidDodger extends Game {
	static int rotation = 0;
	//keep asteroid instances outside of paint method for rotation
	private Asteroid[] asteroids;
	private Player p;
	private boolean exploding = false;
	private int explosionFrame = 0;
	private HealthPack healthPack = null;
	private int lastPackSeconds = -10;   // so one can spawn at start after 10s
	
	private interface explosion {
        /** 
         * advances one frame, return true when done
         * 
         * @return true when process ffinishes
         */
        public boolean tick();
        /** 
         * draw the effect for the current frame 
         * */
        public void draw(Graphics g);
    }
    private explosion explosionEffect = null;
    
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
	
	this.p = new Player(500,500,5,15);
	this.addKeyListener(this.p);
  }
  
  
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
					p.incrementHealth();
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
  			brush.setColor(Color.WHITE);
  			brush.drawString("Game Over", 460, 500);
  			brush.drawString("Time Alive: " + p.getTimeLived()/60 + "." + 
  					p.getTimeLived() % 60, 440, 530);
  		}
  		if (explosionEffect != null) {
  		    explosionEffect.draw(brush);
  		    if (explosionEffect.tick())
  		    {
  		    	explosionEffect = null;
  		    }
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
