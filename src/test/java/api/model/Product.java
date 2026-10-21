package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Product(
        Integer id,

        String name,

        Integer year,

        String color,

        @JsonProperty("pantone_value")
        String pantoneValue
) {
}
