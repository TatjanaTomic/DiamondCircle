package model.util;

import model.exception.MissingConfigurationException;
import model.exception.WrongConfigurationDefinitionException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Util {
    public static Properties LoadConfiguration(Path configPath) throws MissingConfigurationException, WrongConfigurationDefinitionException {

        InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(configPath.toString());
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
