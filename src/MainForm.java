import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainForm {

    private JPanel mainPanel;

    public MainForm() {

    }

    public static void main(String[] args){

        EventLogger logger = new EventLogger("log.txt");
        logger.log("Cheer! I am on board");
        logger.saveLogs();
    }
}
