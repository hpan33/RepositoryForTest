import java.awt.Rectangle;

/**
 * Carnivore represents fishes that eat other fishes. It extends Fish
 * @author Huijie Pan
 * @version 1.1
 */
public abstract class Carnivore extends Fish {

    /**
     * The constructor of Carnivore simply calls super()
     */
    public Carnivore(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
    }
}
