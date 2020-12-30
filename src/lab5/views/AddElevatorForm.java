package lab5.views;

import javax.swing.*;

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

    AddElevatorForm() {
        JFrame frame = new JFrame("AddElevatorForm");
        frame.setContentPane(new AddElevatorForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 400);
        frame.setVisible(true);
    }
}
