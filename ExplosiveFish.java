import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/**
 * The explosive fish, when encountering other visible fishes, explodes itself,
 * and kills the encountering fish. Explosive fishes cannot reproduce. They are
 * carnivores
 * @author Huijie Pan
 * @version 1.1
 */
public class ExplosiveFish extends Carnivore {

    /**
     * The constructor of ExplosiveFish initializes a fish's initial position
     * and the bounds within which it can move.
     * @param x the x coordinate of the initial position of the ExplosiveFish
     * @param y the y coordinate of the initial position of the ExplosiveFish
     * @param bounds the bounds within which the InvisibleFish can move
     */
    public ExplosiveFish(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
        imageName = "explosiveFish.jpg";
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
     * Explosive fishes cannot reproduce
     * @return false always
     */
     @Override
    public boolean canReproduceWithFish(Fish other) {
        return false;
    }

    /**
     * Explosive fishes cannot reproduce
     * @return null
     */
     @Override
    public ExplosiveFish reproduceWithFish(Fish other) {
        return null;
    }

    /**
     * The explosion process of an explosive fish can be viewed as another fish
     * "eaten" by the explosive fish, except that the explosive fish itself
     * dies
     * @param another another fish
     * @return true if the fish is not an invisible fish
     */
     @Override
    public boolean canEatFish(Fish another) {
        return !(another instanceof InvisibleFish);
    }

    /**
     * The fish explodes, and kills both itself and the target fish.
     * @param another another fish
     */
     @Override
    public void eatFish(Fish another) {
        health = 0;
        another.health = 0;
    }

    /**
     * Draws the explosive fish
     */
     @Override
    public void draw(Graphics g) {
        g.drawImage(image, xCoordinate, yCoordinate, null);
    }
}
