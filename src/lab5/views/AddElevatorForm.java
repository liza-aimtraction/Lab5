package lab5.views;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.*;

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
    private MainForm formRef;

    AddElevatorForm(MainForm form) {
        formRef = form;
        JFrame frame = new JFrame("AddElevatorForm");
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setSize(300, 200);
        frame.setVisible(true);
        addButton.addActionListener(e -> {
            try{
                createElevator();
            } catch(Exception er) {
                showError("You entered wrong elevator data");
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        cancelButton.addActionListener(e -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
    }

    private void createElevator() {
        int selected = strategyBox.getSelectedIndex();
        double mass = Double.parseDouble(massInput.getText());
        double volume = Double.parseDouble(volumeInput.getText());
        formRef.addNewElevator(selected, mass, volume);
    }

    private void showError(String message) {
        showMessageDialog(null, message, "Wrong data", ERROR_MESSAGE);
    }
}
