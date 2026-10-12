package web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class RadioButtonPage {

    private static final SelenideElement radioButtonYes = $x("//input[@id='yesRadio']");
    private static final SelenideElement radioButtonImpressive = $x("//input[@id='impressiveRadio']");
    private static final SelenideElement radioButtonNo = $x("//input[@id='noRadio']");

    public RadioButtonPage(String url) {
        open(url);
    }

    public SelenideElement getRadioButtonYes() {
        return radioButtonYes;
    }

    public SelenideElement getRadioButtonImpressive() {
        return radioButtonImpressive;
    }

    public SelenideElement getRadioButtonNo() {
        return radioButtonNo;
    }
}
