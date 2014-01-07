import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/**
 * Sharks are carnivores. They can eat fishes except invisible fishes, explosive
 * fishes and giant squid, on condition that the prey's health is under 50
 * A shark can have up to children
 * @author Huijie Pan
 * @version 1.1
 */
public class Shark extends Carnivore {

    /**
     * The constructor of Shark initializes a fish's initial position
     * and the bounds within which it can move.
     * @param x the x coordinate of the initial position of the Shark
     * @param y the y coordinate of the initial position of the Shark
     * @param bounds the bounds within which the shark can move
     */
    public Shark(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        imageName = "shark.png";
        initializeImage();
        if (xCoordinate < bounds.getX()) {
            xCoordinate = (int) bounds.getX();
        }
        if ((xCoordinate + width) > (bounds.getX() + bounds.getWidth())) {
            xCoordinate = (int) (bounds.getX() + bounds.getWidth() - width);
        }
        if (yCoordinate < bounds.getY()) {
            yCoordinate = (int) bounds.getY();
        }
        if ((yCoordinate + height) > (bounds.getY() + bounds.getHeight())) {
            yCoordinate = (int) (bounds.getY() + bounds.getHeight() - height);
        }
    }

    protected void initializeImage() {
        File aFile = new File(imageName);
        image = null;
        try {
            image = ImageIO.read(aFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        width = image.getWidth();
        height = image.getHeight();
    }

    /**
     * Determines whether or not this shark can reproduce with the
     * parameter fish.
     * @param other another fish
     * @return true if the parameter is also a shark and either of
     * them has less than 2 children
     */
     @Override
    public boolean canReproduceWithFish(Fish other) {
        return ((other instanceof Shark) && collidesWithFish(other)
                && numberOfChildren < 2
                && other.numberOfChildren < 2);
    }

    /**
     * Reproduces a new invisible fish with the other fish
     * @param the other fish
     * @return a baby shark
     */
     @Override
    public Shark reproduceWithFish(Fish other) {
        numberOfChildren++;
        other.numberOfChildren++;
        if (yCoordinate < other.yCoordinate) {
            return new Shark((xCoordinate + other.xCoordinate) / 2,
            yCoordinate, bounds);
        } else {
            return new Shark((xCoordinate + other.xCoordinate) / 2,
            other.yCoordinate, bounds);
        }
    }

    /**
     * Determines whether or not this shark can eat the parameter fish
     * @param another fish
     * @return true if the shark can
     */
     @Override
    public boolean canEatFish(Fish another) {
        return (another.health < 50)
                && (!(another instanceof Shark))
                && (!(another instanceof GiantSquid))
                && (!(another instanceof ExplosiveFish))
                && (!(another instanceof InvisibleFish));
    }

    /**
     *Draws the shark
     */
     @Override
    public void draw(Graphics g) {
        g.drawImage(image, xCoordinate, yCoordinate, null);
    }
}
