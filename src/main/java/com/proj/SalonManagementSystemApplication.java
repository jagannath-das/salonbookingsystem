package com.proj;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.MapPropertySource;

@SpringBootApplication
public class SalonManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SalonManagementSystemApplication.class);
        app.addInitializers(context -> {
            Map<String, Object> properties = configureDatabaseProperties();
            if (!properties.isEmpty()) {
                context.getEnvironment().getPropertySources()
                        .addFirst(new MapPropertySource("normalizedDatabaseProperties", properties));
            }
        });
        app.run(args);
    }

    private static Map<String, Object> configureDatabaseProperties() {
        Map<String, Object> properties = new HashMap<>();
        String datasourceUrl = firstPresent("SPRING_DATASOURCE_URL", "DATABASE_URL");
        if (datasourceUrl == null) {
            return properties;
        }

        datasourceUrl = datasourceUrl.trim();
        datasourceUrl = trimQuotes(datasourceUrl);
        if (datasourceUrl.startsWith("jdbc:")) {
            return properties;
        }

        if (!datasourceUrl.startsWith("mysql://")) {
            return properties;
        }

        URI uri = URI.create(datasourceUrl);
        String jdbcUrl = "jdbc:mysql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath();
        String query = uri.getQuery();
        if (query != null && !query.isBlank()) {
            jdbcUrl += "?" + query.replace("ssl-mode", "sslMode");
        }

        if (!jdbcUrl.contains("sslMode=")) {
            jdbcUrl += jdbcUrl.contains("?") ? "&sslMode=REQUIRED" : "?sslMode=REQUIRED";
        }

        properties.put("spring.datasource.url", jdbcUrl);

        String userInfo = uri.getUserInfo();
        if (userInfo != null && !userInfo.isBlank()) {
            String[] credentials = userInfo.split(":", 2);
            properties.put("spring.datasource.username", decode(credentials[0]));
            if (credentials.length > 1) {
                properties.put("spring.datasource.password", decode(credentials[1]));
            }
        }
        return properties;
    }

    private static String firstPresent(String... names) {
        for (String name : names) {
            String value = System.getenv(name);
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private static String trimQuotes(String value) {
        if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
}
