import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This is the ControlPanel for the Ocean. It allows the
 * user to pick which Fish it would like to add next.
 *
 * @author Huijie Pan
 * @version 1.1
 */
public class ControlPanel extends JPanel {
    private JButton surgeonfish, parrotfish, shark, giantSquid, seaweed,
            explosiveFish, invisibleFish;
    private JLabel beforeCurrent;
    private JLabel current;

    private String fishType;

    /**
     * Constructor of ControlPanel. It initializes essential buttons.
     */
    public ControlPanel() {
        setPreferredSize(new Dimension(150, OceanPanel.HEIGHT));

        surgeonfish = new JButton("SurgeonFish");
        surgeonfish.addActionListener(new ButtonListener("SurgeonFish"));
        // ***Make sure that the String you pass into ButtonListener matches the
        // exact class name of the object.
        add(surgeonfish);

        parrotfish = new JButton("ParrotFish");
        parrotfish.addActionListener(new ButtonListener("ParrotFish"));
        add(parrotfish);

        shark = new JButton("Shark");
        shark.addActionListener(new ButtonListener("Shark"));
        add(shark);

        giantSquid = new JButton("GiantSquid");
        giantSquid.addActionListener(new ButtonListener("GiantSquid"));
        add(giantSquid);
        //You are going to need to add more buttons when you add more
        //types of fish.
        explosiveFish = new JButton("ExplosiveFish");
        explosiveFish.addActionListener(new ButtonListener("ExplosiveFish"));
        add(explosiveFish);
        invisibleFish = new JButton("InvisibleFish");
        invisibleFish.addActionListener(new ButtonListener("InvisibleFish"));
        add(invisibleFish);
        seaweed = new JButton("Seaweed");
        seaweed.addActionListener(new ButtonListener("Seaweed"));
        add(seaweed);
        //default starting fish
        fishType = "SurgeonFish";
        beforeCurrent = new JLabel("Current Fish");
        add(beforeCurrent);
        current = new JLabel("SurgeonFish");
        add(current);

        //implement timer speed control if you feel adventurous
    }

    /**
     * Invoked by OceanPanel to determine which Fish
     * was chosen.
     *
     * @return The currently selected Fish type
     */
    public String getFishType() {
        return fishType;
    }

    public class ButtonListener implements ActionListener {
        private String name;

        public ButtonListener(String className) {
            name = className;
        }

        public void actionPerformed(ActionEvent e) {
            fishType = name;
            if (fishType.equals("Seaweed")) {
                beforeCurrent.setText("Current plant");
            } else {
                beforeCurrent.setText("Current fish");
            }
            current.setText(name);
        }
    }
}
