import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Timer;
import java.awt.Point;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Random;

/**
 * This class represents the Ocean
 *
 * @author Huijie Pan
 * @version 1.1
 */
public class OceanPanel extends JPanel {
    public static final int WIDTH = 600, HEIGHT = 600;

    private ArrayList<Fish> fishies = new ArrayList<Fish>();
    private ArrayList<Seaweed> weeds = new ArrayList<Seaweed>();
    private ControlPanel cPanel;
    private Timer timer;
    private Rectangle bounds;

    /**
    * Constructor - initializes the panel
    *
    * @param control A ControlPanel instance which is used to determine
    * the next Fish to create
    */
    public OceanPanel(ControlPanel control) {
    //***Does not need to be edited
        cPanel = control;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLUE);
        bounds = new Rectangle(0, 0, WIDTH, HEIGHT);
        addMouseListener(new ClickListener());
        timer = new Timer(100, new TimerListener());
        timer.start();
    }

    /**
     * Move all the fish in the Ocean
     */
    public void moveAll() {
        //INSERT CODE HERE
        //Iterate through all of the Fish in fishies
        //and move each one. The move method should be part
        //of your Fish class.
        for (Fish a : fishies) {
            a.move();
        }
    }

    /**
     * Check to see whether any of the Fishs can eat
     * the other Fishs they are around. Then eats them.
     */
    public void checkFoodChain() {
    //***Does not need to be edited
        for (Fish a : fishies) {
            if (a instanceof Carnivore) {
                Carnivore c = (Carnivore) a;
                for (Fish other : fishies) {
                    if (a.collidesWithFish(other)) {
                        if (a != other && c.canEatFish(other)) {
                            c.eatFish(other);
                        }
                    }
                }
            }
        }
    }

    /**
     * Check to see whether any of the Fish can reproduce
     * with the other Fish they are around.
     */
    public void checkReproduce() {
        int currFishsSize = fishies.size();
        //you may want to add additional new baby reproduction limits
        for (int i = 0; i < currFishsSize; i++) {
            for (int j = i + 1; j < currFishsSize; j++) {
            //don't want double reproduction
                if (fishies.get(i).collidesWithFish(fishies.get(j))) {
                    if (fishies.get(i).canReproduceWithFish(fishies.get(j))) {
                        Random generator = new Random();
                        if (generator.nextDouble() <= 0.03) {
                            Fish baby =
                               fishies.get(i).reproduceWithFish(fishies.get(j));
                            if (baby != null) {
                            //Only add non-null babies if there is room
                                if (fishies.size() < 1000) {
                                // prevent heapsize crashes - limit # of fishies
                                    fishies.add(baby);
                            }
                        }
                            i++;
                            // prevent current parent from having too much fun
                        }
                    }
                }
            }
        }
    }

    /**
     * Circle of life. Dead fish go back to the depths and
     * become food for the Plankton's. Aka remove them from the list.
     *
     * Since Java Garbage collects, once the only reference to the
     * Fish is gone, it is deleted from memory.
     */
    public void checkDead() {
        //INSERT CODE HERE to iterate through all fish
        //and remove the ones that are dead (their health is
        //zero)
        Iterator<Fish> i = fishies.iterator();
        while (i.hasNext()) {
            if (i.next().isDead()) {
                i.remove();
                i = fishies.iterator();
            }
        }
    }

    /**
     * This isn't the humane society. This is the Ocean.
     * Old fish have to die.
     */
    public void euthanize() {
        //INSERT CODE HERE to kill all old fish
        //(Each fish that is "too old" should have their health sent to zero)
        for (Fish a : fishies) {
            if (a.getHealth() < 10) {
                a.setHealth(0);
            }
        }
    }

    /**
     * Check if some Seaweeds can be eaten by Herbivores)
     */
    public void checkSeaweed() {
        for (Fish a : fishies) {
            if (a instanceof Herbivore) {
                Herbivore c = (Herbivore) a;
                for (Seaweed other : weeds) {
                    if (other.collidesWithFish(c)) {
                        c.eatSeaweed(other);
                    }
                }
            }
        }
    }

    /**
     * Check for dead Seaweeds and kick them out
     */
    public void checkDeadSeaweed() {
        Iterator<Seaweed> i = weeds.iterator();
        while (i.hasNext()) {
            if (!i.next().isAlive()) {
                i.remove();
                i = weeds.iterator();
            }
        }
    }
    /**
     * Draw all the fish
     */
    public void paintComponent(Graphics g) {
    //***Does not need to be edited
        super.paintComponent(g); //Call to the super constructor to make sure
        //all of JPanel's paintComponent stuff is called first
        for (Fish a : fishies) {
            a.draw(g);
        }
        for (Seaweed b : weeds) {
            b.draw(g);
        }
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //INSERT CODE HERE
            //Call the methods in OceanPanel that you think need
            //to be called each time the timer is triggered.
            moveAll();
            for (Fish a : fishies) {
                a.loseHealth();
            }
            checkFoodChain();
            checkReproduce();
            checkDead();
            euthanize();
            checkSeaweed();
            checkDeadSeaweed();
            repaint();
        }
    }

    private class ClickListener extends MouseAdapter {
       //***You do not need to edit this private inner class
       // but you should know what it is accomplishing.
       /**
        * You are not required to know what exactly is going on in this method.
        * However, if you are curious, you should check out the Class API. Using
        * a tool called 'reflection', it is instantiating a Fish given a
        * String that represents the class name. This way, we don't need a long
        * series of 'if' statements to create a certain type of Fish.
        *@param Point p is where the fish will be placed to start.
        *@param String className is the class name for the type of Fish that
        *       we want to instantiate.
        *@return Fish that is exactly the type of Fish that we want to add
        *        to the panel.
        */
        public Fish instantiateFishSpecies(Point p, String className) {
            try {
                Class cl = Class.forName(className);
                    return (Fish) (cl.getDeclaredConstructor(
                        int.class, int.class, Rectangle.class).newInstance(
                            p.x, p.y, bounds));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InstantiationException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                System.exit(1);
            }
            return null;
        }

        public Seaweed instantiateSeaweeds(Point p, String className) {
            try {
                Class cl = Class.forName(className);
                    return (Seaweed) (cl.getDeclaredConstructor(
                        int.class, int.class, Rectangle.class).newInstance(
                            p.x, p.y, bounds));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InstantiationException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                System.exit(1);
            }
            return null;
        }

        public void mousePressed(MouseEvent e) {
            //Determine which type of Fish to create by asking ControlPanel
            //This information is based on the button that was last clicked
            String fishType = cPanel.getFishType();
            Point p = e.getPoint();
            //Create the corresponding fish
            if (fishType.equals("Seaweed")) {
                weeds.add(instantiateSeaweeds(p, fishType));
            } else {
                fishies.add(instantiateFishSpecies(p, fishType));
            }
            repaint();
        }
    }
}
