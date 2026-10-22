package api.test.products;

import api.specification.ReqResSpecification;
import config.TestConfig;
import core.BaseReqResTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junitpioneer.jupiter.RetryingTest;

import java.time.Instant;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ReqResPatchTest extends BaseReqResTest {

    private static final String PRODUCTS_ENDPOINT = TestConfig.get("reqres.endpoints.products");

    @RetryingTest(maxAttempts = 3, suspendForMs = 5000)
    @DisplayName("Проверка частичного обновления продукта")
    public void checkUpdateProductNameTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL, API_KEY),
                ReqResSpecification.responseSpecification(200)
        );

        Map<String, String> product = Map.of("name", "rose");

        JsonPath response = given()
                .pathParam("id", 2)
                .body(product)
                .when()
                .patch(PRODUCTS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract()
                .jsonPath();

        Assertions.assertEquals(product.get("name"), response.getString("name"));
        Assertions.assertDoesNotThrow(() -> Instant.parse(response.getString("updatedAt")));
    }
}
