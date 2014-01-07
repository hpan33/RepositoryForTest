import java.awt.Rectangle;

/**
 * Herbivore represents fishes that eat Seaweed. It extends Fish
 * @author Huijie Pan
 * @version 1.1
 */
public abstract class Herbivore extends Fish {

    /**
     * The constructor of Carnivore simply calls super()
     */
    public Herbivore(int x, int y, Rectangle bounds) {
        super(x, y, bounds);
    }

    /**
     * Since herbivores don't eat other fishes, this method always return false
     * @return false always
     */
     @Override
    public boolean canEatFish(Fish another) {
        return false; //Herbivores can never eat other fishies;
    }


    /**
     * Herbivores can eat seaweed. After that, seaweed dies
     * @param weed Seaweed to be eaten
     */
    public void eatSeaweed(Seaweed weed) {
            health = 100;
            weed.setAlive(false);
    }
}
