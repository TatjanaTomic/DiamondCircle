package model.util;

import model.exception.MissingConfigurationException;
import model.exception.WrongConfigurationDefinitionException;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class LoggerUtil {

    private static final String logsDirectory = "." + File.separator + "logs" + File.separator;

    public static void createLogsDirectory() {
        if(Paths.get(logsDirectory).toFile().exists()) {
            try {
                 FileUtils.deleteDirectory(Paths.get(logsDirectory).toFile());
            }
            catch (IOException e) {
                log(LoggerUtil.class, e);
            }
        }

        Paths.get(logsDirectory).toFile().mkdir();
    }

    public static void log(Class<?> C, Exception exception) {
        Logger logger = Logger.getLogger(C.getName());

        try {
            String filePath = logsDirectory + C.getName() + "-" + LocalDateTime.now().toLocalTime().toString().replace(':', '_') + ".log";
            Handler handler = new FileHandler(filePath);

            logger.addHandler(handler);
            logger.log(Level.SEVERE, exception.getMessage(), exception);

            handler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logAsync(Class<?> C, Exception exception) {
        new Thread(() -> LoggerUtil.log(C, exception)).start();
    }

}
