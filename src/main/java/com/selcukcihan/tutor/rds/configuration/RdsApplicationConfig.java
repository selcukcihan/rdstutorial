package com.selcukcihan.tutor.rds.configuration;

import com.selcukcihan.tutor.rds.configuration.database.ConnectionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

@Component
public class RdsApplicationConfig {
    private static final Logger LOGGER = Logger.getLogger(RdsApplicationConfig.class.getName());

    private final CommandLineOptions commandLineOptions;
    private final PropertiesParser propertiesParser;

    @Autowired
    public RdsApplicationConfig(CommandLineOptions commandLineOptions, PropertiesParser propertiesParser) {
        this.commandLineOptions = commandLineOptions;
        this.propertiesParser = propertiesParser;
    }


    @Bean
    public ConnectionProperties getConnectionProperties() {
        ConnectionProperties.Builder connectionPropertiesBuilder = new ConnectionProperties.Builder();

        try {
            propertiesParser.getProperty("url").ifPresent(url -> connectionPropertiesBuilder.withUrl(url));
            propertiesParser.getProperty("user").ifPresent(user -> connectionPropertiesBuilder.withUser(user));
            propertiesParser.getProperty("password").ifPresent(password -> connectionPropertiesBuilder.withPassword(password));
        } catch (FileNotFoundException ex) {
            LOGGER.info("Properties file not found, hope we get something from the command line.");
        }

        commandLineOptions.getUrl().ifPresent(url -> connectionPropertiesBuilder.withUrl(url));
        commandLineOptions.getUser().ifPresent(user -> connectionPropertiesBuilder.withUser(user));
        commandLineOptions.getPassword().ifPresent(password -> connectionPropertiesBuilder.withPassword(password));

        return connectionPropertiesBuilder.build();
    }
}
