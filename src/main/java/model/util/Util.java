package model.util;

import model.exception.MissingConfigurationException;
import model.exception.WrongConfigurationDefinitionException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final String logsDirectory = "." + File.separator + "logs" + File.separator;

    public static void log(Class<?> C, Exception exception) {
        Logger logger = Logger.getLogger(C.getName());

        try {
            if(!Paths.get(logsDirectory).toFile().exists()) {
                Paths.get(logsDirectory).toFile().mkdir();
            }

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
        new Thread(() -> Util.log(C, exception)).start();
    }

    public static Properties LoadResources(Path path) throws MissingConfigurationException, WrongConfigurationDefinitionException {

        InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(path.toString());
        if(inputStream == null) {
            throw new MissingConfigurationException();
        }

        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;

        }catch (Exception e) {
            throw new WrongConfigurationDefinitionException();
        }
    }


}
