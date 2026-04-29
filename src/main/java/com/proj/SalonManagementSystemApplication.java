package com.proj;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SalonManagementSystemApplication {

    public static void main(String[] args) {
        configureDatabaseUrl();
        SpringApplication.run(SalonManagementSystemApplication.class, args);
    }

    private static void configureDatabaseUrl() {
        String datasourceUrl = firstPresent("SPRING_DATASOURCE_URL", "DATABASE_URL");
        if (datasourceUrl == null || datasourceUrl.startsWith("jdbc:")) {
            return;
        }

        if (!datasourceUrl.startsWith("mysql://")) {
            return;
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

        System.setProperty("spring.datasource.url", jdbcUrl);

        String userInfo = uri.getUserInfo();
        if (userInfo != null && !userInfo.isBlank()) {
            String[] credentials = userInfo.split(":", 2);
            System.setProperty("spring.datasource.username", decode(credentials[0]));
            if (credentials.length > 1) {
                System.setProperty("spring.datasource.password", decode(credentials[1]));
            }
        }
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
}
