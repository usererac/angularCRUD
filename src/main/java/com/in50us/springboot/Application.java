package com.in50us.springboot;

import com.in50us.springboot.configuration.JpaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages = {"com.in50us.springboot"})
public class Application{

    public static void main(String... args){
        SpringApplication.run(Application.class,args);
    }
}
