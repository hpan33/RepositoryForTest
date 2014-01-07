import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/**
 * Giant squid are carnivores. They can eat every fish except invisible fishes,
 * on condition that the prey's health is under 50
 * Giant squid cannont reproduce
 * @author Huijie Pan
 * @version 1.1
 */
public class GiantSquid extends Carnivore {

    /**
     * The constructor of GiantSquid initializes a fish's initial position
     * and the bounds within which it can move.
     * @param x the x coordinate of the initial position of the giant squid
     * @param y the y coordinate of the initial position of the giant squid
     * @param bounds the bounds within which the giant squid can move
     */
    public GiantSquid(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        imageName = "giantSquid.png";
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
     * A giant squid can never reproduce
     * @return false always
     */
     @Override
    public boolean canReproduceWithFish(Fish other) {
        return false;
    }

    /**
     * A giant squid can never reproduce
     * @return null
     */
     @Override
    public GiantSquid reproduceWithFish(Fish other) {
        return null;
    }

    /**
     * Determines whether or not this giant squid can eat the parameter fish
     * @param another fish
     * @return true if the giant squid can
     */
     @Override
    public boolean canEatFish(Fish another) {
        return (another.health < 50)
                && (!(another instanceof GiantSquid))
                && (!(another instanceof ExplosiveFish))
                && (!(another instanceof InvisibleFish));
    }

    /**
     * Draws the giant squid
     */
     @Override
    public void draw(Graphics g) {
        g.drawImage(image, xCoordinate, yCoordinate, null);
    }
}
