package com.parkinglotmanager.backend;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
})
public class BackendApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        Map<String, Object> envMap = dotenv.entries().stream()
            .collect(Collectors.toMap(DotenvEntry::getKey, DotenvEntry::getValue));

        StandardEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        MapPropertySource mapPropertySource = new MapPropertySource("dotenvProperties", envMap);
        propertySources.addFirst(mapPropertySource);

        SpringApplication app = new SpringApplication(BackendApplication.class);
        app.setEnvironment(environment);
        app.run(args);
	}
}
