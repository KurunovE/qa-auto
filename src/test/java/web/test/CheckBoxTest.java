package web.test;

import annotation.PageLoadStrategy;
import core.BaseSelenideTest;
import enumirate.LoadStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.page.CheckBoxPage;

@PageLoadStrategy(LoadStrategy.EAGER)
public class CheckBoxTest extends BaseSelenideTest {

    private static final String CHECKBOX_URL = "https://demoqa.com/checkbox";

    @Test
    @DisplayName("Выбор корневого checkbox Home")
    public void selectHomeCheckboxTest() {
        CheckBoxPage page = new CheckBoxPage(CHECKBOX_URL);

        page.selectCheckbox("Home");

        Assertions.assertTrue(page.isCheckboxSelected("Home"));
        Assertions.assertTrue(page.isResultDisplayed());
    }

    @Test
    @DisplayName("Снятие выбора с checkbox Home")
    public void unselectHomeCheckboxTest() {
        CheckBoxPage page = new CheckBoxPage(CHECKBOX_URL);

        page.selectCheckbox("Home");
        page.selectCheckbox("Home");

        Assertions.assertFalse(page.isCheckboxSelected("Home"));
        Assertions.assertFalse(page.isResultDisplayed());
    }

    @Test
    @DisplayName("Выбор Desktop отмечает вложенные элементы")
    public void selectDesktopCheckboxTest() {
        CheckBoxPage page = new CheckBoxPage(CHECKBOX_URL);

        page.expandNode("Home");
        page.expandNode("Desktop");
        page.selectCheckbox("Desktop");

        Assertions.assertTrue(page.isCheckboxSelected("Desktop"));
        Assertions.assertTrue(page.isCheckboxSelected("Notes"));
        Assertions.assertTrue(page.isCheckboxSelected("Commands"));
    }

    @Test
    @DisplayName("Выбор Notes не отмечает Commands")
    public void selectSingleCheckboxTest() {
        CheckBoxPage page = new CheckBoxPage(CHECKBOX_URL);

        page.expandNode("Home");
        page.expandNode("Desktop");
        page.selectCheckbox("Notes");

        Assertions.assertTrue(page.isCheckboxSelected("Notes"));
        Assertions.assertFalse(page.isCheckboxSelected("Commands"));
    }

    @Test
    @DisplayName("Частичный выбор дочерних элементов отмечает родительские")
    public void partiallySelectedParentTest() {
        CheckBoxPage page = new CheckBoxPage(CHECKBOX_URL);

        page.expandNode("Home");
        page.expandNode("Desktop");
        page.selectCheckbox("Notes");

        Assertions.assertTrue(page.isCheckboxPartiallySelected("Desktop"));
        Assertions.assertTrue(page.isCheckboxPartiallySelected("Home"));
    }

    @Test
    @DisplayName("Раскрытие всех узлов дерева")
    public void expandAllNodesTest() {
        CheckBoxPage page = new CheckBoxPage(CHECKBOX_URL);

        page.expandAll();

        Assertions.assertTrue(page.isNodeVisible("Notes"));
        Assertions.assertTrue(page.isNodeVisible("Commands"));
        Assertions.assertTrue(page.isNodeVisible("Downloads"));
        Assertions.assertTrue(page.isNodeVisible("React"));
        Assertions.assertTrue(page.isNodeVisible("General"));
    }

    @Test
    @DisplayName("Сворачивание всех узлов дерева")
    public void collapseAllNodesTest() {
        CheckBoxPage page = new CheckBoxPage(CHECKBOX_URL);

        page.expandAll();
        page.collapseAll();

        Assertions.assertFalse(page.isNodeVisible("Notes"));
        Assertions.assertFalse(page.isNodeVisible("Commands"));
        Assertions.assertTrue(page.isNodeVisible("Home"));

        page.expandNode("Home");
        Assertions.assertTrue(page.isNodeVisible("Desktop"));
        Assertions.assertFalse(page.isNodeVisible("Notes"));
    }

    @Test
    @DisplayName("Отображение выбранных элементов в результате")
    public void selectedItemsOutputTest() {
        CheckBoxPage page = new CheckBoxPage(CHECKBOX_URL);

        page.expandNode("Home");
        page.expandNode("Desktop");
        page.selectCheckbox("Notes");

        Assertions.assertTrue(page.isItemInResult("Notes"));
        Assertions.assertFalse(page.isItemInResult("Commands"));
    }
}
