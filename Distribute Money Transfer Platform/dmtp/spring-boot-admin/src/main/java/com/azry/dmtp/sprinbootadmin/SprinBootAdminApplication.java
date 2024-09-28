package com.azry.dmtp.sprinbootadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class SprinBootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SprinBootAdminApplication.class, args);
    }

}
