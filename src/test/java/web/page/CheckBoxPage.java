package web.page;

import com.codeborne.selenide.SelenideElement;
import core.BaseSelenideTest;

import static com.codeborne.selenide.Selenide.open;

public class CheckBoxPage extends BaseSelenideTest {

    private final SelenideElement checkBox = null;

    public CheckBoxPage(String url) {
        open(url);
    }
}
