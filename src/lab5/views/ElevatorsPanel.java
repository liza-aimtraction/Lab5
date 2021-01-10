package lab5.views;

import javax.swing.*;
import java.awt.*;

public class ElevatorsPanel extends JPanel {

    public ElevatorsPanel() {

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new ElevatorPanel(0, 0,  Color.BLUE, "5"));
        add(new ElevatorPanel(60, 0, Color.RED, "5"));
        add(new ElevatorPanel(120, 0, Color.GREEN, "5"));

        setVisible(true);
    }



}
