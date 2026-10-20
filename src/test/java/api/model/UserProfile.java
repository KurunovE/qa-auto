package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserProfile(
        Long id,

        String email,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("last_name")
        String lastName,

        String avatar
) {
}
