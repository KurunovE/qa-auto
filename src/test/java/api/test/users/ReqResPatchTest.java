package api.test.users;

import api.specification.UsersReqResSpecification;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ReqResPatchTest {

    private static final String URL = "https://reqres.in";
    private static final String USERS_ENDPOINT = "/api/users";

    @Test
    @DisplayName("Проверка частичного обновления имени пользователя")
    public void checkUpdateUserNameTest() {
        UsersReqResSpecification.installSpecification(
                UsersReqResSpecification.requestSpecification(URL),
                UsersReqResSpecification.responseSpecification(200)
        );

        Map<String, String> user = Map.of("name", "neo");

        JsonPath response = given()
                .pathParam("id", 2)
                .body(user)
                .when()
                .patch(USERS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract()
                .jsonPath();

        Assertions.assertEquals(user.get("name"), response.getString("name"));
        Assertions.assertNotNull(response.getString("updatedAt"));
        Assertions.assertDoesNotThrow(() -> Instant.parse(response.getString("updatedAt")));
    }

    @Test
    @DisplayName("Проверка частичного обновления должности пользователя")
    public void checkUpdateUserJobTest() {
        UsersReqResSpecification.installSpecification(
                UsersReqResSpecification.requestSpecification(URL),
                UsersReqResSpecification.responseSpecification(200)
        );

        Map<String, String> user = Map.of("job", "the one");

        JsonPath response = given()
                .pathParam("id", 2)
                .body(user)
                .when()
                .patch(USERS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract()
                .jsonPath();

        Assertions.assertEquals(user.get("job"), response.getString("job"));
        Assertions.assertNotNull(response.getString("updatedAt"));
        Assertions.assertDoesNotThrow(() -> Instant.parse(response.getString("updatedAt")));
    }
}
