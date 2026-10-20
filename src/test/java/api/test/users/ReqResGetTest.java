package api.test.users;

import api.model.UserProfile;
import api.model.UsersResponse;
import api.specification.UsersReqResSpecification;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ReqResGetTest {

    private static final String URL = "https://reqres.in";
    private static final String USERS_ENDPOINT = "/api/users";
    private static final String EMAIL_POSTFIX = "@reqres.in";

    @Test
    @DisplayName("Проверка аватар содержит id и email оканчивается на '@reqres.in'")
    public void checkAvatarContainsIdAndEmailEndsWithReqResTest() {
        UsersReqResSpecification.installSpecification(
                UsersReqResSpecification.requestSpecification(URL),
                UsersReqResSpecification.responseSpecification(200)
        );

        UsersResponse usersResponse = given()
                .when()
                .get(USERS_ENDPOINT)
                .then().log().all()
                .extract()
                .as(UsersResponse.class);

        Assertions.assertNotNull(usersResponse);
        Assertions.assertNotNull(usersResponse.data());
        Assertions.assertFalse(usersResponse.data().isEmpty());
        usersResponse.data().forEach(
                user -> {
                    Assertions.assertTrue(user.email().endsWith(EMAIL_POSTFIX));
                    Assertions.assertTrue(user.avatar().contains(user.id().toString()));
                }
        );
    }

    @Test
    @DisplayName("Проверка получения пользователя по id")
    public void checkUserByIdTest() {
        UsersReqResSpecification.installSpecification(
                UsersReqResSpecification.requestSpecification(URL),
                UsersReqResSpecification.responseSpecification(200)
        );

        Long userId = 2L;

        UserProfile user = given()
                .pathParam("id", userId)
                .when()
                .get(USERS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract()
                .jsonPath()
                .getObject("data", UserProfile.class);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(userId, user.id());
        Assertions.assertNotNull(user.firstName());
        Assertions.assertFalse(user.firstName().isBlank());
        Assertions.assertNotNull(user.lastName());
        Assertions.assertFalse(user.lastName().isBlank());
        Assertions.assertNotNull(user.email());
        Assertions.assertTrue(user.email().endsWith(EMAIL_POSTFIX));
        Assertions.assertNotNull(user.avatar());
        Assertions.assertTrue(user.avatar().contains(userId.toString()));
    }

    @Test
    @DisplayName("Проверка получения несуществующего пользователя")
    public void checkUserNotFoundTest() {
        UsersReqResSpecification.installSpecification(
                UsersReqResSpecification.requestSpecification(URL),
                UsersReqResSpecification.responseSpecification(404)
        );

        JsonPath response = given()
                .pathParam("id", 23)
                .when()
                .get(USERS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract()
                .jsonPath();

        Assertions.assertTrue(response.getMap("$").isEmpty());
    }
}
