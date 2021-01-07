package lab5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Oikawa
 * @param {boolean} alsoLogToConsole - set it manually to also log to console
 *
 * Poor design from me(Oikawa), sorry, but you must pass logger as parameter to all
 * objects that need to log.
 *
 * --------------Sequence of use--------------
 *  lab5.EventLogger logger = new lab5.EventLogger("log.txt");
 *
 *  logger.log("Dima entered elevator");
 *  logger.log("Dima entered elevator");
 *  logger.log("Dima entered elevator");
 *
 *  // for now nothing is written to log file
 *  logger.saveLogs();
 *  // now you can`t log
 *
 *  logger.log("Dima is my boy"); // error
 *
 */
public class EventLogger {
    private final String logFileRelPath;
    private FileWriter writer;
    private SimpleDateFormat formatter;
    private boolean alsoLogToConsole = true;

    /**
     * @param logFileRelPath - relative path to file
     * Create if don`t exist or clear file content if file exist
     */
    public EventLogger(String logFileRelPath){
        this.logFileRelPath = logFileRelPath;

        try {
            // this object can`t be used to write into file     xDDDD <3 Java
            new File(logFileRelPath).createNewFile();
            System.out.println("Log file creted");

            writer = new FileWriter(logFileRelPath);

            formatter= new SimpleDateFormat("HH:mm:ss:SS");
        } catch (IOException e) {
            System.out.println("Error occurred while creating file");
            e.printStackTrace();
        }

    }

    /**
     * @param logText - text to log without new line character in end of the string
     */
    public synchronized void log(String logText){
        try {
            Date date = new Date(System.currentTimeMillis());
            String logTime = formatter.format(date);
            String fullLogText = logTime + " : " + logText + "\n";
            writer.write(fullLogText);
            if(alsoLogToConsole){
                System.out.print(fullLogText);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while logging");
            e.printStackTrace();
        }
    }

    /**
     * Saves all logs to file, closes writes, so can`t log after this call
     * Use before quit main
     */
    public void saveLogs(){
        try {
            writer.close();
            System.out.println("Logs saved to file " + logFileRelPath);
        } catch (IOException e) {
            System.out.println("Error occurred while saving logs"); // writer probably closed already
        }
    }
}
