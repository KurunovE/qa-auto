package config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.io.InputStream;

public final class TestConfig {

    private static final String CONFIG_PATH = "/config.yaml";
    private static final JsonNode VALUES = load();

    private TestConfig() {
    }

    public static String get(String key) {
        JsonNode value = findValue(key);
        if (!value.isTextual() || value.textValue().isBlank()) {
            throw new IllegalArgumentException("Missing or non-string configuration value: " + key);
        }
        return value.textValue();
    }

    public static boolean getBoolean(String key) {
        JsonNode value = findValue(key);
        if (!value.isBoolean()) {
            throw new IllegalArgumentException("Missing or non-boolean configuration value: " + key);
        }
        return value.booleanValue();
    }

    public static long getLong(String key) {
        JsonNode value = findValue(key);
        if (value.isIntegralNumber() && value.canConvertToLong()) {
            return value.longValue();
        }
        if (value.isTextual() && value.textValue().matches("[+-]?[0-9]+(?:_[0-9]+)*")) {
            try {
                return Long.parseLong(value.textValue().replace("_", ""));
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Configuration value is outside long range: " + key, exception);
            }
        }
        throw new IllegalArgumentException("Missing or non-integer configuration value: " + key);
    }

    private static JsonNode findValue(String key) {
        JsonNode value = VALUES;
        for (String part : key.split("\\.", -1)) {
            value = value.path(part);
        }
        return value;
    }

    private static JsonNode load() {
        try (InputStream input = TestConfig.class.getResourceAsStream(CONFIG_PATH)) {
            if (input == null) {
                throw new IllegalStateException(CONFIG_PATH + " not found in test resources");
            }
            JsonNode values = new YAMLMapper().readTree(input);
            if (values == null || !values.isObject()) {
                throw new IllegalStateException(CONFIG_PATH + " must contain a YAML object");
            }
            return values;
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load " + CONFIG_PATH, exception);
        }
    }
}
