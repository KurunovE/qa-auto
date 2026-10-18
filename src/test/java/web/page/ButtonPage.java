package web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ButtonPage {

    private static final String DOUBLE_CLICK_ME_RESULT_SELECTOR = "//p[@id='doubleClickMessage']";
    private static final String RIGHT_CLICK_ME_RESULT_SELECTOR = "//p[@id='rightClickMessage']";
    private static final String CLICK_ME_RESULT_SELECTOR = "//p[@id='dynamicClickMessage']";

    public static final String TITLE_TEXT = "Buttons";
    public static final String DOUBLE_CLICK_ME_RESULT_MESSAGE = "You have done a double click";
    public static final String RIGHT_CLICK_ME_RESULT_MESSAGE = "You have done a right click";
    public static final String CLICK_ME_RESULT_MESSAGE = "You have done a dynamic click";

    private final SelenideElement title = $x("//h1[@class='text-center']");
    private final SelenideElement doubleClickMeButton = $x("//button[text()='Double Click Me']");
    private final SelenideElement rightClickMeButton = $x("//button[text()='Right Click Me']");
    private final SelenideElement clickMeButton = $x("//button[text()='Click Me']");

    public ButtonPage(String url) {
        open(url);
    }

    public String getTitle() {
        return title.shouldBe(visible).getText();
    }

    public void clickToDoubleClickMe() {
        doubleClickMeButton.shouldBe(visible)
                .scrollIntoView("{block: 'center'}")
                .hover()
                .doubleClick();
    }

    public void clickToRightClickMe() {
        rightClickMeButton
                .shouldBe(visible)
                .scrollIntoView("{block: 'center'}")
                .hover()
                .contextClick();
    }

    public void clickToClickMe() {
        clickMeButton.shouldBe(visible)
                .scrollIntoView("{block: 'center'}")
                .hover()
                .click();
    }

    public String getDoubleClickMeMessage() {
        return $x(DOUBLE_CLICK_ME_RESULT_SELECTOR)
                .shouldBe(Condition.visible)
                .getText();
    }

    public String getRightClickMeMessage() {
        return $x(RIGHT_CLICK_ME_RESULT_SELECTOR)
                .shouldBe(Condition.visible)
                .getText();
    }

    public String getClickMeMessage() {
        return $x(CLICK_ME_RESULT_SELECTOR)
                .shouldBe(Condition.visible)
                .getText();
    }
}
