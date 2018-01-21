package com.selcukcihan.tutor.rds.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

@Component
public class PropertiesParser {
    private static final Logger LOGGER = Logger.getLogger(PropertiesParser.class.getName());

    private final String filename;

    public PropertiesParser(@Qualifier("propertiesFile") String filename) {
        this.filename = filename;
    }

    public Optional<String> getProperty(String name) throws FileNotFoundException {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(filename);

            properties.load(input);

            String property = properties.getProperty(name);
            return StringUtils.isEmpty(property)
                    ? Optional.empty()
                    : Optional.of(property);
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            LOGGER.severe(ex.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.severe(e.toString());
                }
            }
        }
        return Optional.empty();
    }
}
