package utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerHandler {
    static DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh-mm-ss");
    static Date date = new Date();

    static String LOG_FILE = "/home/coder/project/workspace/Project/src/main/logs/log.log"+"-"+dateFormat.format(date);
    
    public static final Logger log = Logger.getLogger(LoggerHandler.class.getName());
    
    static {
        try {
            LogManager.getLogManager().reset(); // Reset any existing logging configuration
            
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new java.util.logging.SimpleFormatter());
            log.addHandler(fileHandler);
            log.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Could not set up logger configuration: " + e.toString());
        }
    }
    
    public static Logger getLogger() {
        return log;
    }
}