package core;

import annotation.PageLoadStrategy;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import enumirate.LoadStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.lang.reflect.Method;

public abstract class BaseSelenideTest {

    private static final String BROWSER = "firefox";
    private static final String BROWSER_SIZE = "1440x735";
    private static final boolean HEADLESS = false;
    private static final long PAGE_LOAD_TIMEOUT = 60_000L;
    private static final long ELEMENT_LOAD_TIMEOUT = 20_000L;

    private static final LoadStrategy DEFAULT_STRATEGY = LoadStrategy.NORMAL;

    public void setup(LoadStrategy strategy) {
        Configuration.browser = BROWSER;
        Configuration.browserSize = BROWSER_SIZE;
        Configuration.headless = HEADLESS;
        Configuration.pageLoadStrategy = strategy.toString();
        Configuration.pageLoadTimeout = PAGE_LOAD_TIMEOUT;
        Configuration.timeout = ELEMENT_LOAD_TIMEOUT;
    }

    @BeforeEach
    public void init(TestInfo testInfo) {
        LoadStrategy strategy = resolveLoadStrategy(testInfo);
        setup(strategy);
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    private LoadStrategy resolveLoadStrategy(TestInfo testInfo) {
        Method testMethod = testInfo.getTestMethod().orElse(null);

        if (testMethod != null) {
            PageLoadStrategy methodAnnotation = testMethod.getAnnotation(PageLoadStrategy.class);

            if (methodAnnotation != null) {
                return methodAnnotation.value();
            }
        }

        Class<?> testClass = testInfo.getTestClass().orElse(null);

        while (testClass != null) {
            PageLoadStrategy classAnnotation = testClass.getAnnotation(PageLoadStrategy.class);

            if (classAnnotation != null) {
                return classAnnotation.value();
            }

            testClass = testClass.getSuperclass();
        }

        return DEFAULT_STRATEGY;
    }
}
