package core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseSelenideTest {

    public void setup() {
        Configuration.browser = "firefox";
        Configuration.browserSize = "1440x735";
        Configuration.headless = false;
    }

    @BeforeEach
    public void init() {
        setup();
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
