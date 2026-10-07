package web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object для страницы Text Box
 *
 * <p>Содержит элементы формы, методы для ввода данных,
 * отправки формы и получения отображаемых результатов.</p>
 */
public class TextBoxPage {

    private final SelenideElement fullNameInput = $("#userName");
    private final SelenideElement emailInput = $("#userEmail");
    private final SelenideElement currentAddressInput = $("#currentAddress");
    private final SelenideElement permanentAddressInput = $("#permanentAddress");

    private final SelenideElement submitButton = $("#submit");

    private final SelenideElement outputName = $("#name");
    private final SelenideElement outputEmail = $("#email");
    private final SelenideElement outputCurrentAddress = $("p#currentAddress");
    private final SelenideElement outputPermanentAddress = $("p#permanentAddress");

    public TextBoxPage(String url) {
        open(url);
    }

    public void setFullName(String fullName) {
        fullNameInput.setValue(fullName);
    }

    public void setEmail(String email) {
        emailInput.setValue(email);
    }

    public void setCurrentAddress(String address) {
        currentAddressInput.setValue(address);
    }

    public void setPermanentAddress(String address) {
        permanentAddressInput.setValue(address);
    }

    public void submit() {
        submitButton.scrollTo().click();
    }

    public String getOutputName() {
        return outputName.getText()
                .replace("Name:", "");
    }

    public String getOutputEmail() {
        return outputEmail.getText()
                .replace("Email:", "");
    }

    public String getOutputCurrentAddress() {
        return outputCurrentAddress.getText()
                .replace("Current Address :", "");
    }

    public String getOutputPermanentAddress() {
        return outputPermanentAddress.getText()
                .replace("Permananet Address :", "");
    }

    /**
     * Проверяет, находится ли поле email в состоянии ошибки валидации.
     *
     * @return true, если email отмечен как невалидный
     */
    public boolean isEmailInvalid() {
        String className = emailInput.getAttribute("class");

        return className != null && className.contains("field-error");
    }

    /**
     * Проверяет видимость блока с результатами формы Text Box.
     *
     * @return true, если блок результатов видим пользователю
     */
    public boolean isOutputDisplayed() {
        return $("#output").isDisplayed();
    }
}
