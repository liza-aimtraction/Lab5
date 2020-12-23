import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
        --------------Sequence of use--------------

        EventLogger logger = new EventLogger("log.txt");

        logger.log("Dima entered elevator");
        logger.log("Dima entered elevator");
        logger.log("Dima entered elevator");

        // for now noting is written to log file
        logger.saveLogs();
        // now you can`t log


        logger.log("Dima is my boy"); // error

 */


public class EventLogger {
    private final String logFileRelPath;
    private FileWriter writer;
    private SimpleDateFormat formatter;


    public EventLogger(String logFileRelPath){
        this.logFileRelPath = logFileRelPath;

        try {
            // this object can`t be used to write into file     xDDDD <3 Java
            new File(logFileRelPath).createNewFile(); // creates or opens new
            System.out.println("Log file creted");

            writer = new FileWriter(logFileRelPath);

            formatter= new SimpleDateFormat("HH:mm:ss:SS");
        } catch (IOException e) {
            System.out.println("Error occurred while creating file");
            e.printStackTrace();
        }

    }

    public synchronized void log(String logText){
        try {
            Date date = new Date(System.currentTimeMillis());
            String logTime = formatter.format(date);
            writer.write(logTime + " : " + logText + "\n");
        } catch (IOException e) {
            System.out.println("Error occurred while logging");
            e.printStackTrace();
        }
    }

    // use before quit main
    public void saveLogs(){
        try {
            writer.close();
            System.out.println("Logs saved to file " + logFileRelPath);
        } catch (IOException e) {
            System.out.println("Error occurred while saving logs"); // writer probably closed already
        }
    }
}
