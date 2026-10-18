package web.test;

import annotation.PageLoadStrategy;
import core.BaseSelenideTest;
import enumirate.LoadStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import web.page.ButtonPage;

import static web.page.ButtonPage.*;

@PageLoadStrategy(LoadStrategy.EAGER)
public class ButtonTest extends BaseSelenideTest {

    private static final String BUTTON_PAGE_URL = "https://demoqa.com/buttons";

    @Test
    public void checkTitle() {
        ButtonPage page = new ButtonPage(BUTTON_PAGE_URL);
        Assertions.assertEquals(TITLE_TEXT, page.getTitle());
    }

    @Test
    public void clickButtonDoubleClickMe() {
        ButtonPage page = new ButtonPage(BUTTON_PAGE_URL);

        page.clickToDoubleClickMe();

        Assertions.assertEquals(DOUBLE_CLICK_ME_RESULT_MESSAGE, page.getDoubleClickMeMessage());
    }

    @Test
    public void clickButtonRightClickMe() {
        ButtonPage page = new ButtonPage(BUTTON_PAGE_URL);

        page.clickToRightClickMe();

        Assertions.assertEquals(RIGHT_CLICK_ME_RESULT_MESSAGE, page.getRightClickMeMessage());
    }

    @Test
    public void clickButtonClickMe() {
        ButtonPage page = new ButtonPage(BUTTON_PAGE_URL);

        page.clickToClickMe();

        Assertions.assertEquals(CLICK_ME_RESULT_MESSAGE, page.getClickMeMessage());
    }

    @Test
    public void clickAllButtons() {
        ButtonPage page = new ButtonPage(BUTTON_PAGE_URL);

        page.clickToDoubleClickMe();
        page.clickToRightClickMe();
        page.clickToClickMe();

        Assertions.assertEquals(DOUBLE_CLICK_ME_RESULT_MESSAGE, page.getDoubleClickMeMessage());
        Assertions.assertEquals(RIGHT_CLICK_ME_RESULT_MESSAGE, page.getRightClickMeMessage());
        Assertions.assertEquals(CLICK_ME_RESULT_MESSAGE, page.getClickMeMessage());
    }
}
