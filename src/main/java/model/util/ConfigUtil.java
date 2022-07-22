package model.util;

import model.exception.MissingConfigurationException;
import model.exception.WrongConfigurationDefinitionException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public abstract class ConfigUtil {

    private static final String FILE = "File ";
    private static final String NOT_EXISTS = " does not exist!";
    private static final String WRONG_FORMAT = " is not well formatted.";
    private static final String CANNOT_LOAD = "Unable to load ";
    private static final String CONFIG_FILE = " config file.";

    public static JSONArray ReadMatrixConfiguration(Path path) throws MissingConfigurationException, WrongConfigurationDefinitionException {

        Object jsonConfiguration;
        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(path.toFile())) {

            jsonConfiguration = jsonParser.parse(fileReader);

            return (JSONArray) jsonConfiguration;

        } catch (FileNotFoundException e) {
            throw new MissingConfigurationException(FILE + path.getFileName() + NOT_EXISTS);
        } catch (Exception e) {
            throw new WrongConfigurationDefinitionException(CANNOT_LOAD + path.getFileName() + CONFIG_FILE);
        }
    }

    public static Properties LoadResources(Path path) throws MissingConfigurationException, WrongConfigurationDefinitionException {

        InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream(path.toString());
        if(inputStream == null) {
            throw new MissingConfigurationException(FILE + path.getFileName() + NOT_EXISTS);
        }

        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            
            return properties;
        }catch (Exception e) {
            throw new WrongConfigurationDefinitionException(FILE + path.getFileName() + WRONG_FORMAT);
        }
    }
}
