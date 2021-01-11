package lab5.views;

import lab5.ElevatorStrategies.BasicElevatorStrategy;
import lab5.ElevatorStrategies.PrioritizedElevatorStrategy;
import lab5.ElevatorStrategies.RoundRobinElevatorStrategy;
import lab5.IElevatorStrategy;

import javax.swing.*;
import java.awt.event.WindowEvent;

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

    private final IElevatorStrategy[] strategies = new IElevatorStrategy[] {
            new BasicElevatorStrategy(),
            new PrioritizedElevatorStrategy(),
            new RoundRobinElevatorStrategy()
    };

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

        fillElevatorStrategiesComboBox();
    }

    private void fillElevatorStrategiesComboBox() {
        for (IElevatorStrategy strategy : strategies) {
            strategyBox.addItem(strategy.getName());
        }
    }

    private void createElevator() {
        int selectedIndex = strategyBox.getSelectedIndex();
        IElevatorStrategy strategy = strategies[selectedIndex];
        double mass = Double.parseDouble(massInput.getText());
        double volume = Double.parseDouble(volumeInput.getText());
        formRef.addNewElevator(strategy, mass, volume);
    }

    private void showError(String message) {
        showMessageDialog(null, message, "Wrong data", ERROR_MESSAGE);
    }
}
