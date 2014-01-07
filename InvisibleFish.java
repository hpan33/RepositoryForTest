import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/**
 * Invisible fish are herbivores. They are invisible, which means that they
 * will not be seen by other fishes and thus will not be eaten whatsoever.
 * But they do eat seaweed
 * An invisible fish can only have one child
 * @author Huijie Pan
 * @version 1.1
 */
public class InvisibleFish extends Herbivore {

    /**
     * The constructor of InvisibleFish initializes a fish's initial position
     * and the bounds within which it can move.
     * @param x the x coordinate of the initial position of the InvisibleFish
     * @param y the y coordinate of the initial position of the InvisibleFish
     * @param bounds the bounds within which the InvisibleFish can move
     */
     public InvisibleFish(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        imageName = "invisibleFish.jpg";
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
     * Determines whether or not this Invisible Fish can reproduce with the
     * parameter fish.
     * @param other another fish
     * @return true if the parameter is also an invisible fish and either of
     * them doesn't have a child
     */
     @Override
    public boolean canReproduceWithFish(Fish other) {
        return ((other instanceof InvisibleFish)
                && numberOfChildren < 1
                && other.numberOfChildren < 1);
    }

    /**
     * Reproduces a new invisible fish with the other fish
     * @param the other fish
     * @return a baby invisible fish
     */
     @Override
    public InvisibleFish reproduceWithFish(Fish other) {
        numberOfChildren++;
        other.numberOfChildren++;
        if (yCoordinate < other.yCoordinate) {
            return new InvisibleFish((xCoordinate + other.xCoordinate) / 2,
            yCoordinate, bounds);
        } else {
            return new InvisibleFish((xCoordinate + other.xCoordinate) / 2,
            other.yCoordinate, bounds);
        }
    }

    /**
     * Draws the invisible fish.
     */
     @Override
    public void draw(Graphics g) {
        g.drawImage(image, xCoordinate, yCoordinate, null);
    }
}
