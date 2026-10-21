package api.test.users;


import api.specification.ReqResSpecification;
import core.BaseReqResTest;
import config.TestConfig;
import io.restassured.path.json.JsonPath;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ReqResPutTest extends BaseReqResTest {

    private static final String USERS_ENDPOINT = TestConfig.get("reqres.endpoints.users");

    @Test
    @DisplayName("Проверка обновления пользователя")
    public void checkUpdateUserTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL),
                ReqResSpecification.responseSpecification(200)
        );

        Map<String, String> user = Map.of(
                "name", "morpheus",
                "job", "zion resident"
        );

        JsonPath response = given()
                .pathParam("id", 2)
                .body(user)
                .when()
                .put(USERS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract()
                .jsonPath();

        Assertions.assertEquals(user.get("name"), response.getString("name"));
        Assertions.assertEquals(user.get("job"), response.getString("job"));
        Assertions.assertNotNull(response.getString("updatedAt"));
        Assertions.assertDoesNotThrow(() -> Instant.parse(response.getString("updatedAt")));
    }
}
