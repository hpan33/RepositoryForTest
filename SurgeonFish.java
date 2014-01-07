import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/**
 * Surgeon fishes are herbivores. They can have up to 5 children
 * @author Huijie Pan
 * @version 1.1
 */
public class SurgeonFish extends Herbivore {

    /**
     * The constructor of SurgeonFish initializes a fish's initial position
     * and the bounds within which it can move.
     * @param x the x coordinate of the initial position of the surgeon fish
     * @param y the y coordinate of the initial position of the surgeon fish
     * @param bounds the bounds within which the surgeon fish can move
     */
    public SurgeonFish(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        imageName = "surgeonFish.png";
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
     * Determines whether or not this surgeon fish can reproduce with the
     * parameter fish.
     * @param other another fish
     * @return true if the parameter is also a surgeon fish and either of
     * them has less than 5 children
     */
     @Override
    public boolean canReproduceWithFish(Fish other) {
        return ((other instanceof SurgeonFish) && collidesWithFish(other)
                && numberOfChildren < 5
                && other.numberOfChildren < 5);
    }

    /**
     * Reproduces a new surgeon fish with the other fish
     * @param the other fish
     * @return a baby surgeon fish
     */
     @Override
    public SurgeonFish reproduceWithFish(Fish other) {
        numberOfChildren++;
        other.numberOfChildren++;
        if (yCoordinate < other.yCoordinate) {
            return new SurgeonFish((xCoordinate + other.xCoordinate) / 2,
            yCoordinate, bounds);
        } else {
            return new SurgeonFish((xCoordinate + other.xCoordinate) / 2,
            other.yCoordinate, bounds);
        }
    }

    /**
     * Draws the surgeon fish
     */
     @Override
    public void draw(Graphics g) {
        g.drawImage(image, xCoordinate, yCoordinate, null);
    }
}
