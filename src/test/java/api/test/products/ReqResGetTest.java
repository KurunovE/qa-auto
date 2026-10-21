package api.test.products;

import api.factory.ProductFactory;
import api.model.Product;
import api.model.ProductsResponse;
import api.specification.ReqResSpecification;
import config.TestConfig;
import core.BaseReqResTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqResGetTest extends BaseReqResTest {

    private static final String PRODUCTS_ENDPOINT = TestConfig.get("reqres.endpoints.products");

    @Test
    @DisplayName("Проверка получения первой страницы продуктов и проверка всех полей")
    public void checkProductsFirstPageTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL),
                ReqResSpecification.responseSpecification(200)
        );

        ProductsResponse response = given()
                .queryParam("page", 1)
                .when()
                .get(PRODUCTS_ENDPOINT)
                .then().log().all()
                .extract()
                .as(ProductsResponse.class);

        Assertions.assertNotNull(response);

        Assertions.assertEquals(1, response.page());
        Assertions.assertEquals(6, response.perPage());
        Assertions.assertEquals(12, response.total());
        Assertions.assertEquals(2, response.totalPages());
        Assertions.assertEquals(ProductFactory.firstPageProducts(), response.data());
        Assertions.assertNotNull(response.support());
        Assertions.assertNotNull(response.support().url());
        Assertions.assertFalse(response.support().url().isBlank());
        Assertions.assertNotNull(response.support().text());
        Assertions.assertFalse(response.support().text().isBlank());
    }

    @Test
    @DisplayName("Проверка получения второй страницы продуктов")
    public void checkProductsSecondPageTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL),
                ReqResSpecification.responseSpecification(200)
        );

        ProductsResponse response = given()
                .queryParam("page", 2)
                .when()
                .get(PRODUCTS_ENDPOINT)
                .then().log().all()
                .extract()
                .as(ProductsResponse.class);

        Assertions.assertNotNull(response);

        Assertions.assertEquals(2, response.page());
        Assertions.assertEquals(6, response.perPage());
        Assertions.assertEquals(12, response.total());
        Assertions.assertEquals(2, response.totalPages());
        Assertions.assertNotNull(response.data());
        Assertions.assertEquals(List.of(7, 8, 9, 10, 11, 12),
                response.data().stream().map(Product::id).toList());
        response.data().forEach(
                product -> {
                    Assertions.assertNotNull(product.name());
                    Assertions.assertFalse(product.name().isBlank());
                    Assertions.assertEquals(1999 + product.id(), product.year());
                    Assertions.assertNotNull(product.color());
                    Assertions.assertTrue(product.color().matches("#[0-9A-Fa-f]{6}"));
                    Assertions.assertNotNull(product.pantoneValue());
                    Assertions.assertTrue(product.pantoneValue().matches("\\d{2}-\\d{4}"));
                }
        );
    }

    @Test
    @DisplayName("Проверка получения продукта по id")
    public void checkProductByIdTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL),
                ReqResSpecification.responseSpecification(200)
        );

        Integer productId = 2;

        Product product = given()
                .pathParam("id", productId)
                .when()
                .get(PRODUCTS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract()
                .jsonPath()
                .getObject("data", Product.class);

        Assertions.assertNotNull(product);
        Assertions.assertEquals(ProductFactory.fuchsiaRose(), product);
    }

    @Test
    @DisplayName("Проверка получения несуществующего продукта")
    public void checkProductNotFoundTest() {
        ReqResSpecification.installSpecification(
                ReqResSpecification.requestSpecification(REQ_RES_URL),
                ReqResSpecification.responseSpecification(404)
        );

        JsonPath response = given()
                .pathParam("id", 23)
                .when()
                .get(PRODUCTS_ENDPOINT + "/{id}")
                .then().log().all()
                .extract()
                .jsonPath();

        Assertions.assertTrue(response.getMap("$").isEmpty());
    }
}
