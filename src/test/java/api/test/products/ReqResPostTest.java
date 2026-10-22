package api.test.products;

import api.factory.ProductFactory;
import api.model.Product;
import api.specification.ReqResSpecification;
import config.TestConfig;
import core.BaseReqResTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junitpioneer.jupiter.RetryingTest;

import java.time.Instant;

import static io.restassured.RestAssured.given;

public class ReqResPostTest extends BaseReqResTest {

    private static final String PRODUCTS_ENDPOINT = TestConfig.get("reqres.endpoints.products");

    @RetryingTest(maxAttempts = 3, suspendForMs = 5000)
    @DisplayName("Проверка создания продукта")
    public void checkCreateProductTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL, API_KEY),
                ReqResSpecification.responseSpecification(201)
        );

        Product product = ProductFactory.fuchsiaRose();

        JsonPath response = given()
                .body(product)
                .when()
                .post(PRODUCTS_ENDPOINT)
                .then().log().all()
                .extract()
                .jsonPath();

        Assertions.assertEquals(product.name(), response.getString("name"));
        Assertions.assertEquals(product.year(), response.getInt("year"));
        Assertions.assertEquals(product.color(), response.getString("color"));
        Assertions.assertEquals(product.pantoneValue(), response.getString("pantone_value"));
        Assertions.assertNotNull(response.getString("id"));
        Assertions.assertFalse(response.getString("id").isBlank());
        Assertions.assertDoesNotThrow(() -> Instant.parse(response.getString("createdAt")));
    }
}
