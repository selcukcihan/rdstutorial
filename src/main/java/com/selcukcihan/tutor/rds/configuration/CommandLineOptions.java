package com.selcukcihan.tutor.rds.configuration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class CommandLineOptions {
    private static final Logger LOGGER = Logger.getLogger(CommandLineOptions.class.getName());
    private final Options options;

    @Autowired
    private ApplicationArguments applicationArguments;
    private CommandLine commandLine;

    public CommandLineOptions() {
        options = new Options();
        options.addOption(new Option("url", true, "jdbc connection url"))
                .addOption(new Option("u", "user", true, "database user"))
                .addOption(new Option("p", "password", true, "database password"));
    }

    @PostConstruct
    public void init() {
        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        try {
            commandLine = commandLineParser.parse(options, applicationArguments.getSourceArgs());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Overrides properties defined in sql.properties file", options);
            System.exit(1);
        }
    }

    public Optional<String> getUrl() {
        return commandLine.hasOption("url")
                ? Optional.of(commandLine.getOptionValue("url"))
                : Optional.empty();
    }

    public Optional<String> getUser() {
        return commandLine.hasOption("user")
                ? Optional.of(commandLine.getOptionValue("user"))
                : Optional.empty();
    }

    public Optional<String> getPassword() {
        return commandLine.hasOption("password")
                ? Optional.of(commandLine.getOptionValue("password"))
                : Optional.empty();
    }
}
