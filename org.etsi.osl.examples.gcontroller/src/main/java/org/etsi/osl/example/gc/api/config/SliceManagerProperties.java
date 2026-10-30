package org.etsi.osl.example.gc.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slice-manager")
public record SliceManagerProperties(String url, String username, String password) {
}