package hr.fer.shimun.packing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ContainerPackingSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainerPackingSpringBootApplication.class, args);
    }
}
