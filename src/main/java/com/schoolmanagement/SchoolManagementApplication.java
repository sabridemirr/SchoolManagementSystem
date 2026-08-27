package com.schoolmanagement; // Places the class in the project's main package.

import org.springframework.boot.SpringApplication; // Provides the method that starts the Spring Boot application.
import org.springframework.boot.autoconfigure.SpringBootApplication; // Provides the main Spring Boot configuration annotation.

/*
 * This is the starting class of the Spring Boot application.
 *
 * It replaces the old console-based Main class as the application's
 * main entry point. The old Main class will be kept temporarily while
 * we migrate the existing features.
 */
@SpringBootApplication // Enables configuration, automatic setup and component scanning.
public class SchoolManagementApplication {

    /* * The JVM starts the application from this method.
     * @param args optional command-line arguments  */
    public static void main(String[] args) {// Creates the Spring application context, discovers Spring classes and starts the embedded Tomcat web server.
        SpringApplication.run(SchoolManagementApplication.class, // Tells Spring which configuration class starts the application.
                args // Passes command-line arguments to Spring Boot.
        );
    }
}