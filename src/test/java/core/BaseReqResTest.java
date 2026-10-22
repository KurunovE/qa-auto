package core;

import config.TestConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;

public abstract class BaseReqResTest {

    protected static final String REQ_RES_URL = TestConfig.get("reqres.base-url");
    protected static final String API_KEY = TestConfig.get("reqres.api-key");

    @AfterEach
    void resetSpecifications() {
        RestAssured.reset();
    }
}
