package web.test;

import annotation.PageLoadStrategy;
import core.BaseSelenideTest;
import enumirate.LoadStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.page.RadioButtonPage;

@PageLoadStrategy(LoadStrategy.EAGER)
public class RadioButtonTest extends BaseSelenideTest {

    private static final String RADIO_BUTTON_PAGE_URL = "https://demoqa.com/radio-button";

    @Test
    @DisplayName("Выбор Yes кнопки и проверка состояния checked")
    public void selectYesRadioButtonTest() {
        RadioButtonPage page = new RadioButtonPage(RADIO_BUTTON_PAGE_URL);

        page.clickYes();

        Assertions.assertTrue(page.isYesSelected());
    }

    @Test
    @DisplayName("Выбор Impressive кнопки и проверка состояния checked")
    public void selectImpressiveRadioButtonTest() {
        RadioButtonPage page = new RadioButtonPage(RADIO_BUTTON_PAGE_URL);

        page.clickImpressive();

        Assertions.assertTrue(page.isImpressiveSelected());
    }

    @Test
    @DisplayName("Выбор No кнопки и проверка состояния checked")
    public void selectNoRadioButtonTest() {
        RadioButtonPage page = new RadioButtonPage(RADIO_BUTTON_PAGE_URL);

        Assertions.assertTrue(page.isNoDisabled());
    }

    @Test
    @DisplayName("Переключение с Yes на Impressive, проверка снятия Yes")
    public void switchFromYesRadioButtonToImpressiveRadioButtonTest() {
        RadioButtonPage page = new RadioButtonPage(RADIO_BUTTON_PAGE_URL);

        page.clickYes();
        page.clickImpressive();

        Assertions.assertFalse(page.isYesSelected());
        Assertions.assertTrue(page.isImpressiveSelected());
    }
}
