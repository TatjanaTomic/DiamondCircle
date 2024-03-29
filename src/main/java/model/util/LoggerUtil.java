package model.util;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class LoggerUtil {

    private static final String logsDirectory = "." + File.separator + "logs" + File.separator;
    private static final String LOG = ".log";
    private static final String TIME_FORMAT = "hh-mm-ss";

    public static void createLogsDirectory() {
        Path logsPath = Path.of(logsDirectory);
        if(logsPath.toFile().exists()) {
            try {
                 FileUtils.deleteDirectory(logsPath.toFile());
            }
            catch (IOException e) {
                log(LoggerUtil.class, e);
            }
        }

        logsPath.toFile().mkdir();
    }

    public static void log(Class<?> C, Exception exception) {
        Logger logger = Logger.getLogger(C.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);

        try {
            String filePath = logsDirectory + C.getSimpleName() + "-" + simpleDateFormat.format(new Date()) + LOG;
            Handler handler = new FileHandler(filePath);

            logger.addHandler(handler);
            logger.log(Level.SEVERE, exception.getMessage());

            handler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logAsync(Class<?> C, Exception exception) {
        new Thread(() -> LoggerUtil.log(C, exception)).start();
    }

}
