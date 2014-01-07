import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/**
 * Parrot fishes are herbivores. They can only have one child.
 */
public class ParrotFish extends Herbivore {

    /**
     * The constructor of ParrotFish initializes a fish's initial position
     * and the bounds within which it can move.
     * @param x the x coordinate of the initial position of the parrot fish
     * @param y the y coordinate of the initial position of the parrot fish
     * @param bounds the bounds within which the parrot fish can move
     */
     public ParrotFish(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        imageName = "parrotFish.png";
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
     * Determines whether or not this parrot fish can reproduce with the
     * parameter fish.
     * @param other another fish
     * @return true if the parameter is also a parrot fish and either of
     * them has less than 1 children
     */
     @Override
    public boolean canReproduceWithFish(Fish other) {
        return ((other instanceof ParrotFish)
                && numberOfChildren < 1
                && other.numberOfChildren < 1);
    }

    /**
     * Reproduces a new parrot fish with the other fish
     * @param the other fish
     * @return a baby parrot fish
     */
     @Override
    public ParrotFish reproduceWithFish(Fish other) {
        numberOfChildren++;
        other.numberOfChildren++;
        if (yCoordinate < other.yCoordinate) {
            return new ParrotFish((xCoordinate + other.xCoordinate) / 2,
            yCoordinate, bounds);
        } else {
            return new ParrotFish((xCoordinate + other.xCoordinate) / 2,
            other.yCoordinate, bounds);
        }
    }

    /**
     * Draws the parrot fish
     */
     @Override
    public void draw(Graphics g) {
        g.drawImage(image, xCoordinate, yCoordinate, null);
    }
}
