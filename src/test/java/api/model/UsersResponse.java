package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UsersResponse(
        Integer page,

        @JsonProperty("per_page")
        Integer perPage,

        Integer total,

        @JsonProperty("total_pages")
        Integer totalPages,

        List<UserProfile> data,

        Support support,

        @JsonProperty("_meta")
        Meta meta
) {

}
