
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
        Building building = new Building(logger, 300, 10);
        building.addPerson(new Person("ManuallyCreatedPerson1", logger));
        building.addPerson(new Person("ManuallyCreatedPerson2", logger));


        building.startupBuildingThreads();
        building.waitForAllThread();

        logger.saveLogs();
    }
}
