package api.test.products;

import api.specification.ReqResSpecification;
import config.TestConfig;
import core.BaseReqResTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junitpioneer.jupiter.RetryingTest;

import static io.restassured.RestAssured.given;

public class ReqResDeleteTest extends BaseReqResTest {

    private static final String PRODUCTS_ENDPOINT = TestConfig.get("reqres.endpoints.products");

    @RetryingTest(maxAttempts = 3, suspendForMs = 5000)
    @DisplayName("Проверка удаления продукта")
    public void checkDeleteProductTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL, API_KEY),
                ReqResSpecification.responseSpecification(204)
        );

        String response = given()
                .pathParam("id", 2)
                .when()
                .delete(PRODUCTS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract()
                .asString();

        Assertions.assertTrue(response.isEmpty());
    }
}
