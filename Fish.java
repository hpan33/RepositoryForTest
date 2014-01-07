import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.Graphics;

/**
 * This is the Fish class, which is the parent of all fishes.
 * @author Huijie Pan
 * @version 1.1
 */
public abstract class Fish {
    protected int health;
    protected int xCoordinate;
    protected int yCoordinate;
    protected int width;
    protected int height;
    protected Rectangle bounds;
    protected String imageName;
    protected BufferedImage image;
    protected int numberOfChildren;

    /**
     * The constructor of Fish initializes a fish's initial position and
     * the bounds within which it can move.
     * @param x the x coordinate of the initial position of the Fish
     * @param y the y coordinate of the initial position of the Fish
     * @param bounds the bounds within which the Fish can move
     */
    public Fish(int x, int y, Rectangle bounds) {
        health = 100;
        xCoordinate = x;
        yCoordinate = y;
        this.bounds = new Rectangle(bounds);
        numberOfChildren = 0;
    }

    protected abstract void initializeImage();

    /**
     * The collidesWithFish method determines whether or not this Fish
     * collides with another fish, which is specified by the parameter
     * @param another another Fish
     * @return true if the two Fishes collide
     */
    public boolean collidesWithFish(Fish another) {
        return ((((another.xCoordinate > xCoordinate)
                && another.xCoordinate < (xCoordinate + width))
                || (((another.xCoordinate + another.width) > xCoordinate)
                && ((another.xCoordinate + another.width)
                < (xCoordinate + width))))
                && ((another.yCoordinate > yCoordinate
                && another.yCoordinate < (yCoordinate + height))
                || (((another.yCoordinate + another.height) > yCoordinate)
                && (another.yCoordinate + another.height
                < (yCoordinate + height)))))
                || ((((xCoordinate > another.xCoordinate)
                && xCoordinate < (another.xCoordinate + another.width))
                || (((xCoordinate + width) > another.xCoordinate)
                && ((xCoordinate + width)
                < (another.xCoordinate + another.width))))
                && ((yCoordinate > another.yCoordinate
                && yCoordinate < (another.yCoordinate + another.height))
                || (((yCoordinate + height) > another.yCoordinate)
                && (yCoordinate + height
                < (another.yCoordinate + another.height)))));
    }

    /**
     * Determines if the Fish is dead
     * return true if the Fish is dead
     */
    public boolean isDead() {
        return (health <= 0);
    }

    /**
     * The fish loses 1 point of health
     */
    public void loseHealth() {
        health--;
    }

    /**
     * Determines if this Fish can eat another Fish
     * @param another another Fish
     * @return true if this Fish can eat the parameter Fish
     */
    public boolean canEatFish(Fish another) {
        return collidesWithFish(another)
                && (another.health < health)
                && (another.health < 50);
    }

    /**
     * The Fish eats the input Fish and the latter dies
     */
    public void eatFish(Fish another) {
        if (canEatFish(another)) {
            another.health = 0;
            health += 20;
        }
    }

    /**
     * Determines if this Fish can reproduce with the parameter Fish
     * @param another another Fish
     * @return true if it can
     */
    public abstract boolean canReproduceWithFish(Fish another);

    /**
     * Reproduces a new Fish with the parameter Fish
     * @param another Fish
     * @return Fish the reproduced Fish
     */
    public abstract Fish reproduceWithFish(Fish another);

    /**
     * This method moves the Fish randomly
     */
    public void move() {
        Random generator = new Random();
        double a = generator.nextDouble();
        if (a < 0.25) {
            xCoordinate -= 25;
            if (isOutOfBounds()) {
                xCoordinate += 25;
            }
        } else if (a < 0.5) {
            xCoordinate += 25;
            if (isOutOfBounds()) {
                xCoordinate -= 25;
            }
        } else if (a < 0.75) {
            yCoordinate -= 25;
            if (isOutOfBounds()) {
                yCoordinate += 25;
            }
        } else if (a <= 1) {
            yCoordinate += 25;
            if (isOutOfBounds()) {
                yCoordinate -= 25;
            }
        }
    }

    private boolean isOutOfBounds() {
        return ((xCoordinate < bounds.getX())
            || ((xCoordinate + width) > (bounds.getX() + bounds.getWidth()))
            || (yCoordinate < bounds.getY())
            || ((yCoordinate + height) > (bounds.getX() + bounds.getHeight())));
    }

    /**
     * This method draws the Fish
     */
    public abstract void draw(Graphics g);

    /**
     * Mutator for the fish's health
     * @param health health to be set for the fish
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Accessor for the fish's health
     * @return health the fish's health
     */
    public int getHealth() {
        return health;
    }
}
