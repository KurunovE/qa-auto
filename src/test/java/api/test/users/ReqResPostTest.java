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

public class ReqResPostTest extends BaseReqResTest {

    private static final String USERS_ENDPOINT = TestConfig.get("reqres.endpoints.users");
    private static final String REGISTER_ENDPOINT = TestConfig.get("reqres.endpoints.register");
    private static final String LOGIN_ENDPOINT = TestConfig.get("reqres.endpoints.login");

    @Test
    @DisplayName("Проверка создания пользователя")
    public void checkCreateUserTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL),
                ReqResSpecification.responseSpecification(201)
        );

        Map<String, String> user = Map.of(
                "name", "morpheus",
                "job", "leader"
        );

        JsonPath response = given()
                .body(user)
                .when()
                .post(USERS_ENDPOINT)
                .then().log().all()
                .extract()
                .jsonPath();

        Assertions.assertEquals(user.get("name"), response.getString("name"));
        Assertions.assertEquals(user.get("job"), response.getString("job"));
        Assertions.assertNotNull(response.getString("id"));
        Assertions.assertFalse(response.getString("id").isBlank());
        Assertions.assertNotNull(response.getString("createdAt"));
        Assertions.assertDoesNotThrow(() -> Instant.parse(response.getString("createdAt")));
    }

    @Test
    @DisplayName("Проверка отказа регистрации без пароля")
    public void checkRegisterWithoutPasswordTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL),
                ReqResSpecification.responseSpecification(400)
        );

        Map<String, String> user = Map.of("email", "sydney@fife");

        JsonPath response = given()
                .body(user)
                .when()
                .post(REGISTER_ENDPOINT)
                .then().log().all()
                .extract()
                .jsonPath();

        Assertions.assertEquals("Missing password", response.getString("error"));
        Assertions.assertNull(response.getString("token"));
    }

    @Test
    @DisplayName("Проверка отказа входа без пароля")
    public void checkLoginWithoutPasswordTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL),
                ReqResSpecification.responseSpecification(400)
        );

        Map<String, String> user = Map.of("email", "peter@klaven");

        JsonPath response = given()
                .body(user)
                .when()
                .post(LOGIN_ENDPOINT)
                .then().log().all()
                .extract()
                .jsonPath();

        Assertions.assertEquals("Missing password", response.getString("error"));
        Assertions.assertNull(response.getString("token"));
    }
}
