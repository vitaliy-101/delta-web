package org.example.deltawebfacade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class DeltaWebFacadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeltaWebFacadeApplication.class, args);
    }

}
