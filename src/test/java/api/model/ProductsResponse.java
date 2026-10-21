package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ProductsResponse(
        Integer page,

        @JsonProperty("per_page")
        Integer perPage,

        Integer total,

        @JsonProperty("total_pages")
        Integer totalPages,

        List<Product> data,

        Support support
) {
}
