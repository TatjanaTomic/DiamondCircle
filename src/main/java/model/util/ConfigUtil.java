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

    public static JSONArray ReadMatrixConfiguration(Path path) throws MissingConfigurationException, WrongConfigurationDefinitionException {

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

    public static Properties LoadResources(Path path) throws MissingConfigurationException, WrongConfigurationDefinitionException {

        InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream(path.toString());
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
}
