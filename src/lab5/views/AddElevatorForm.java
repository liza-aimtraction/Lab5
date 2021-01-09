package lab5.views;

import javax.swing.*;
import java.awt.event.WindowEvent;

/////////////////////////////////////////////////////
//LIZA + Swing = disappointment & aggression
/////////////////////////////////////////////////////
public class AddElevatorForm {
    private JButton addButton;
    private JButton cancelButton;
    private JComboBox strategyBox;
    private JTextField massInput;
    private JTextField volumeInput;
    private JPanel mainPanel;

    AddElevatorForm(MainForm form) {
        JFrame frame = new JFrame("AddElevatorForm");
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setSize(300, 200);
        frame.setVisible(true);
        addButton.addActionListener(e -> {
            double mass = Double.parseDouble(massInput.getText());
            double volume = Double.parseDouble(volumeInput.getText());
            form.addNewElevator(0, mass, volume);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        cancelButton.addActionListener(e -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
    }
}
