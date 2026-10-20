package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Meta(
        @JsonProperty("powered_by")
        String poweredBy,

        @JsonProperty("docs_url")
        String docsUrl,

        @JsonProperty("upgrade_url")
        String upgradeUrl,

        @JsonProperty("example_url")
        String exampleUrl,

        String variant,

        String message,

        Cta cta,

        String context
) {
}
