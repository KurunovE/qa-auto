package web.test;

import annotation.PageLoadStrategy;
import core.BaseSelenideTest;
import config.TestConfig;
import enumirate.LoadStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.page.CheckBoxPage;

@PageLoadStrategy(LoadStrategy.EAGER)
public class CheckBoxTest extends BaseSelenideTest {

    private static final String CHECKBOX_PAGE_URL = TestConfig.get("demoqa.checkbox-url");

    @Test
    @DisplayName("Выбор корневого checkbox Home")
    public void selectHomeCheckboxTest() {
        String checkboxHome = "Home";

        CheckBoxPage page = new CheckBoxPage(CHECKBOX_PAGE_URL);

        page.selectCheckbox(checkboxHome);

        Assertions.assertTrue(page.isCheckboxSelected(checkboxHome));
        Assertions.assertTrue(page.isResultDisplayed());
    }

    @Test
    @DisplayName("Снятие выбора с checkbox Home")
    public void unselectHomeCheckboxTest() {
        String checkboxHome = "Home";

        CheckBoxPage page = new CheckBoxPage(CHECKBOX_PAGE_URL);

        page.selectCheckbox(checkboxHome);
        page.selectCheckbox(checkboxHome);

        Assertions.assertFalse(page.isCheckboxSelected(checkboxHome));
        Assertions.assertFalse(page.isResultDisplayed());
    }

    @Test
    @DisplayName("Выбор Desktop отмечает вложенные элементы")
    public void selectDesktopCheckboxTest() {
        String checkboxHome = "Home";
        String checkboxDesktop = "Desktop";
        String checkboxNotes = "Notes";
        String checkboxCommands = "Commands";

        CheckBoxPage page = new CheckBoxPage(CHECKBOX_PAGE_URL);

        page.expandNode(checkboxHome);
        page.expandNode(checkboxDesktop);
        page.selectCheckbox(checkboxDesktop);

        Assertions.assertTrue(page.isCheckboxSelected(checkboxDesktop));
        Assertions.assertTrue(page.isCheckboxSelected(checkboxNotes));
        Assertions.assertTrue(page.isCheckboxSelected(checkboxCommands));
    }

    @Test
    @DisplayName("Выбор Notes не отмечает Commands")
    public void selectSingleCheckboxTest() {
        String checkboxHome = "Home";
        String checkboxDesktop = "Desktop";
        String checkboxNotes = "Notes";
        String checkboxCommands = "Commands";

        CheckBoxPage page = new CheckBoxPage(CHECKBOX_PAGE_URL);

        page.expandNode(checkboxHome);
        page.expandNode(checkboxDesktop);
        page.selectCheckbox(checkboxNotes);

        Assertions.assertTrue(page.isCheckboxSelected(checkboxNotes));
        Assertions.assertFalse(page.isCheckboxSelected(checkboxCommands));
    }

    @Test
    @DisplayName("Частичный выбор дочерних элементов отмечает родительские")
    public void partiallySelectedParentTest() {
        String checkboxHome = "Home";
        String checkboxDesktop = "Desktop";
        String checkboxNotes = "Notes";

        CheckBoxPage page = new CheckBoxPage(CHECKBOX_PAGE_URL);

        page.expandNode(checkboxHome);
        page.expandNode(checkboxDesktop);
        page.selectCheckbox(checkboxNotes);

        Assertions.assertTrue(page.isCheckboxPartiallySelected(checkboxDesktop));
        Assertions.assertTrue(page.isCheckboxPartiallySelected(checkboxHome));
    }

    @Test
    @DisplayName("Раскрытие всех узлов дерева")
    public void expandAllNodesTest() {
        String checkboxNotes = "Notes";
        String checkboxCommands = "Commands";
        String checkboxDownloads = "Downloads";
        String checkboxReact = "React";
        String checkboxGeneral = "General";

        CheckBoxPage page = new CheckBoxPage(CHECKBOX_PAGE_URL);

        page.expandAll();

        Assertions.assertTrue(page.isNodeVisible(checkboxNotes));
        Assertions.assertTrue(page.isNodeVisible(checkboxCommands));
        Assertions.assertTrue(page.isNodeVisible(checkboxDownloads));
        Assertions.assertTrue(page.isNodeVisible(checkboxReact));
        Assertions.assertTrue(page.isNodeVisible(checkboxGeneral));
    }

    @Test
    @DisplayName("Сворачивание всех узлов дерева")
    public void collapseAllNodesTest() {
        String checkboxHome = "Home";
        String checkboxDesktop = "Desktop";
        String checkboxNotes = "Notes";
        String checkboxCommands = "Commands";

        CheckBoxPage page = new CheckBoxPage(CHECKBOX_PAGE_URL);

        page.expandAll();
        page.collapseAll();

        Assertions.assertFalse(page.isNodeVisible(checkboxNotes));
        Assertions.assertFalse(page.isNodeVisible(checkboxCommands));
        Assertions.assertTrue(page.isNodeVisible(checkboxHome));

        page.expandNode(checkboxHome);
        Assertions.assertTrue(page.isNodeVisible(checkboxDesktop));
        Assertions.assertFalse(page.isNodeVisible(checkboxNotes));
    }

    @Test
    @DisplayName("Отображение выбранных элементов в результате")
    public void selectedItemsOutputTest() {
        String checkboxHome = "Home";
        String checkboxDesktop = "Desktop";
        String checkboxNotes = "Notes";
        String checkboxCommands = "Commands";

        CheckBoxPage page = new CheckBoxPage(CHECKBOX_PAGE_URL);

        page.expandNode(checkboxHome);
        page.expandNode(checkboxDesktop);
        page.selectCheckbox(checkboxNotes);

        Assertions.assertTrue(page.isItemInResult(checkboxNotes));
        Assertions.assertFalse(page.isItemInResult(checkboxCommands));
    }
}
