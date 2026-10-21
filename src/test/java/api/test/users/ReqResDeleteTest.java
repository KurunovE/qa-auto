package api.test.users;

import api.specification.ReqResSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ReqResDeleteTest {

    private static final String URL = "https://reqres.in";
    private static final String USERS_ENDPOINT = "/api/users";

    @Test
    @DisplayName("Проверка удаления пользователя")
    public void checkDeleteUserTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(URL),
                ReqResSpecification.responseSpecification(204)
        );

        String response = given()
                .pathParam("id", 2)
                .when()
                .delete(USERS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract()
                .asString();

        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Проверка повторного удаления пользователя")
    public void checkDeleteUserTwiceTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(URL),
                ReqResSpecification.responseSpecification(204)
        );

        for (int attempt = 0; attempt < 2; attempt++) {
            String response = given()
                    .pathParam("id", 2)
                    .when()
                    .delete(USERS_ENDPOINT + "/{id}")
                    .then().log().all()
                    .extract()
                    .asString();

            Assertions.assertTrue(response.isEmpty());
        }
    }
}
