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

public class Util {

    //TODO : Razdvoji funkcionalnosti, napravi posebne Util-e

    private static final String logsDirectory = "." + File.separator + "logs" + File.separator;

    public static Properties LoadResources(Path path) throws MissingConfigurationException, WrongConfigurationDefinitionException {

        InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(path.toString());
        if(inputStream == null) {
            throw new MissingConfigurationException("File " + path.getFileName() + " does not exist.");
        }

        try {
            Properties properties = new Properties();
            properties.load(inputStream);

            return properties;
        }catch (Exception e) {
            throw new WrongConfigurationDefinitionException("File " + path.getFileName() + " is not well formatted.");
        }
    }

    public static void createLogsDirectory() {
        if(Paths.get(logsDirectory).toFile().exists()) {
            try {
                 FileUtils.deleteDirectory(Paths.get(logsDirectory).toFile());
            }
            catch (IOException e) {
                log(Util.class, e);
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
        new Thread(() -> Util.log(C, exception)).start();
    }

    public static JSONArray ReadMatrixConfiguration(Path path) throws MissingConfigurationException, WrongConfigurationDefinitionException{

        Object jsonConfiguration;
        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(path.toFile())) {

            jsonConfiguration = jsonParser.parse(fileReader);

            return (JSONArray) jsonConfiguration;

        } catch (FileNotFoundException e) {
            throw new MissingConfigurationException("File " + path.getFileName() + " does not exist!");
        } catch (Exception e) {
            throw new WrongConfigurationDefinitionException("Unable to load " + path.getFileName() + " config file");
        }
    }
}
