import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;

/**
 * This is the Seaweed class, which represents a kind of plant that can be
 * eaten by Herbivores
 * @author Huijie Pan
 * @version 1.1
 */
public class Seaweed {
    private boolean alive;
    private int xCoordinate;
    private int yCoordinate;
    private int width;
    private int height;
    private Rectangle bounds;
    private String imageName;
    private BufferedImage image;

    /**
     * The constructor of Seaweed initializes a Seaweed's initial position and
     * the bounds within which it can be initialized.
     * @param x the x coordinate of the initial position of the Seaweed
     * @param y the y coordinate of the initial position of the Seaweed
     * @param bounds the bounds within which the Seaweed can be initialized
     */
    public Seaweed(int x, int y, Rectangle bounds) {
        alive = true;
        xCoordinate = x;
        yCoordinate = y;
        this.bounds = new Rectangle(bounds);
        imageName = "seaWeed.jpg";
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

    private void initializeImage() {
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
     * The collidesWithFish method determines whether or not this Seaweed
     * collides with another fish, which is specified by the parameter
     * @param another another Fish
     * @return true if the they collide
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
     * This method set the living status of the Seaweed
     * @param alive whether or nor the Seaweed is to be alive
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * This method returns true if the Seaweed is alive
     * @return true if the Seaweed is alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * This method draws the Seaweed
     */
    public void draw(Graphics g) {
        g.drawImage(image, xCoordinate, yCoordinate, null);
    }
}
