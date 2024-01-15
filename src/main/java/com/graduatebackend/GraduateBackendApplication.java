package com.graduatebackend;

import com.graduatebackend.utils.DefaultProfileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableAsync
public class GraduateBackendApplication {

    private final Environment env;

    public GraduateBackendApplication(Environment env) {
        this.env = env;
    }

    /**
     * Main method of Spring Application
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GraduateBackendApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
    }

}
