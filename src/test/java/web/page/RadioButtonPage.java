package web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object для страницы Radio Button
 *
 * <p>Содержит радиокнопки Yes, Impressive и No
 * и методы для получения этих элементов.</p>
 */
public class RadioButtonPage {

    private final SelenideElement radioButtonYes = $x("//input[@id='yesRadio']");
    private final SelenideElement radioButtonImpressive = $x("//input[@id='impressiveRadio']");
    private final SelenideElement radioButtonNo = $x("//input[@id='noRadio']");

    public RadioButtonPage(String url) {
        open(url);
    }

    public void clickYes() {
        radioButtonYes.click();
    }

    public void clickImpressive() {
        radioButtonImpressive.click();
    }

    public boolean isYesSelected() {
        return radioButtonYes.isSelected();
    }

    public boolean isImpressiveSelected() {
        return radioButtonImpressive.isSelected();
    }

    public boolean isNoDisabled() {
        return radioButtonNo.is(disabled) && !radioButtonNo.isSelected();
    }
}
