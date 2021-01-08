package lab5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Oikawa
 * @param {boolean} alsoLogToConsole - set it manually to also log to console
 */
public class EventLogger {
    private static  String logFileRelPath;
    private static FileWriter writer;
    private static SimpleDateFormat formatter;
    private static boolean alsoLogToConsole = true;
    private static HashMap<String, ArrayList<String>> threadLogs;

    /**
     * @param logFileRelPath - relative path to file
     * Create if don`t exist or clear file content if file exist
     */
    public static void InitEventLogger(String logFileRelPath){
        threadLogs = new HashMap<String, ArrayList<String>>();
        EventLogger.logFileRelPath = logFileRelPath;

        try {
            // this object can`t be used to write into file     xDDDD <3 Java
            new File(logFileRelPath).createNewFile();
            System.out.println("Log file creted");

            EventLogger.writer = new FileWriter(logFileRelPath);

            EventLogger.formatter= new SimpleDateFormat("HH:mm:ss:SS");
        } catch (IOException e) {
            System.out.println("Error occurred while creating file");
            e.printStackTrace();
        }

    }

    /**
     * @param logText - text to log without new line character in end of the string
     */
    public static synchronized void log(String logText, String threadName){
        try {
            Date date = new Date(System.currentTimeMillis());
            String logTime = formatter.format(date);
            String fullLogText = logTime + " : " + logText + "\n";
            writer.write(fullLogText);
            if(!threadLogs.containsKey(threadName)){
                threadLogs.put(threadName, new ArrayList<>());
            }
            threadLogs.get(threadName).add(fullLogText);
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
    public static void saveLogs(){
        try {
            for(Map.Entry<String, ArrayList<String>> entry : threadLogs.entrySet()) {
                String key = entry.getKey();
                ArrayList<String> value = entry.getValue();

                writer.write("\n\n\n\n\n-----------------------------------" + key + "-----------------------------------\n");
                for (String log: value) {
                    writer.write(log);
                }
            }
            writer.close();
            System.out.println("Logs saved to file " + logFileRelPath);
        } catch (IOException e) {
            System.out.println("Error occurred while saving logs"); // writer probably closed already
        }
    }
}
