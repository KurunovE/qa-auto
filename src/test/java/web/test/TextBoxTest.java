package web.test;

import core.BaseSelenideTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.page.TextBoxPage;

public class TextBoxTest extends BaseSelenideTest {

    private static final String TEXT_BOX_URL = "https://demoqa.com/text-box";

    @Test
    @DisplayName("Отправка формы с валидными данными")
    public void submitFormWithValidData() {
        String name = "Egor Kurunov";
        String email = "egor@example.com";
        String currentAddress = "Amsterdam";
        String permanentAddress = "Saint Petersburg";

        TextBoxPage textBoxPage = new TextBoxPage(TEXT_BOX_URL);

        textBoxPage.setFullName(name);
        textBoxPage.setEmail(email);
        textBoxPage.setCurrentAddress(currentAddress);
        textBoxPage.setPermanentAddress(permanentAddress);
        textBoxPage.submit();

        Assertions.assertEquals(name, textBoxPage.getOutputName());
        Assertions.assertEquals(email, textBoxPage.getOutputEmail());
        Assertions.assertEquals(currentAddress, textBoxPage.getOutputCurrentAddress());
        Assertions.assertEquals(permanentAddress, textBoxPage.getOutputPermanentAddress());
    }

    @Test
    @DisplayName("Отправка формы только с заполненным именем")
    public void submitFormWithOnlyName() {
        String name = "Egor Kurunov";

        TextBoxPage textBoxPage = new TextBoxPage(TEXT_BOX_URL);

        textBoxPage.setFullName(name);
        textBoxPage.submit();

        Assertions.assertEquals(name, textBoxPage.getOutputName());
    }

    @Test
    @DisplayName("Отклонение формы при невалидном email")
    public void rejectFormWithInvalidEmail() {
        String email = "IIIIiiiiii";

        TextBoxPage textBoxPage = new TextBoxPage(TEXT_BOX_URL);

        textBoxPage.setEmail(email);
        textBoxPage.submit();

        Assertions.assertTrue(textBoxPage.isEmailInvalid());
        Assertions.assertFalse(textBoxPage.isOutputDisplayed());
    }

    @Test
    @DisplayName("Отправка формы с валидным email")
    public void submitFormWithValidEmail() {
        String email = "egor.test@example.com";

        TextBoxPage textBoxPage = new TextBoxPage(TEXT_BOX_URL);

        textBoxPage.setEmail(email);
        textBoxPage.submit();

        Assertions.assertEquals(email, textBoxPage.getOutputEmail());
    }

    @Test
    @DisplayName("Обновление результата после повторной отправки формы")
    public void updateOutputAfterResubmittingForm() {
        String firstName = "Egor";
        String secondName = "Igor";

        TextBoxPage textBoxPage = new TextBoxPage(TEXT_BOX_URL);

        textBoxPage.setFullName(firstName);
        textBoxPage.submit();

        Assertions.assertEquals(firstName, textBoxPage.getOutputName());

        textBoxPage.setFullName(secondName);
        textBoxPage.submit();

        Assertions.assertEquals(secondName, textBoxPage.getOutputName());
    }

    @Test
    @DisplayName("Результат не отображается при отправке пустой формы")
    public void keepOutputHiddenWhenFormIsEmpty() {
        TextBoxPage textBoxPage = new TextBoxPage(TEXT_BOX_URL);

        textBoxPage.submit();

        Assertions.assertFalse(textBoxPage.isOutputDisplayed());
    }
}
