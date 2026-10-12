package web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object для страницы Check Box
 *
 * <p>Содержит элементы дерева, методы для выбора чекбоксов,
 * раскрытия и сворачивания узлов и проверки результатов выбора.</p>
 */
public class CheckBoxPage {

    private static final String TREE_WRAPPER_SELECTOR = ".check-box-tree-wrapper .rc-tree";
    private static final String RESULT_SELECTOR = "#result";
    private static final String TREE_NODE_SELECTOR = ".rc-tree-treenode";
    private static final String NODE_TITLE_SELECTOR = ".rc-tree-title";
    private static final String CHECKBOX_SELECTOR = ".rc-tree-checkbox";
    private static final String SWITCHER_CLOSE_SELECTOR = ".rc-tree-switcher_close";
    private static final String SWITCHER_OPEN_SELECTOR = ".rc-tree-switcher_open";
    private static final String RESULT_ITEM_SELECTOR = ".text-success";

    private static final String ARIA_EXPANDED = "aria-expanded";
    private static final String ARIA_CHECKED = "aria-checked";
    private static final String TRUE_VALUE = "true";
    private static final String MIXED_VALUE = "mixed";

    private final SelenideElement tree = $(TREE_WRAPPER_SELECTOR);
    private final SelenideElement result = $(RESULT_SELECTOR);

    public CheckBoxPage(String url) {
        open(url);
    }

    private SelenideElement node(String name) {
        return tree.$$(NODE_TITLE_SELECTOR)
                .findBy(exactText(name))
                .closest(TREE_NODE_SELECTOR);
    }

    private SelenideElement checkbox(String name) {
        return node(name).$(CHECKBOX_SELECTOR);
    }

    /**
     * Переключает состояние чекбокса указанного узла.
     *
     * @param name название узла дерева
     */
    public void selectCheckbox(String name) {
        checkbox(name).click();
    }

    /**
     * Раскрывает указанный узел дерева, если он свернут.
     *
     * @param name название узла дерева
     */
    public void expandNode(String name) {
        SelenideElement branch = node(name).shouldBe(visible);
        if (!branch.has(attribute(ARIA_EXPANDED, TRUE_VALUE))) {
            branch.$(SWITCHER_CLOSE_SELECTOR).click();
            branch.shouldHave(attribute(ARIA_EXPANDED, TRUE_VALUE));
        }
    }

    public void expandAll() {
        SelenideElement closeSwitcher = tree.$(SWITCHER_CLOSE_SELECTOR);
        while (closeSwitcher.exists()) {
            closeSwitcher.click();
        }
    }

    public void collapseAll() {
        ElementsCollection openSwitchers = tree.$$(SWITCHER_OPEN_SELECTOR);
        while (!openSwitchers.isEmpty()) {
            openSwitchers.last().click();
        }
    }

    /**
     * Проверяет, выбран ли чекбокс указанного узла полностью.
     *
     * @param name название узла дерева
     * @return true, если чекбокс выбран полностью
     */
    public boolean isCheckboxSelected(String name) {
        return checkbox(name).has(attribute(ARIA_CHECKED, TRUE_VALUE));
    }

    /**
     * Проверяет, находится ли чекбокс указанного узла в состоянии частичного выбора.
     *
     * @param name название узла дерева
     * @return true, если чекбокс выбран частично
     */
    public boolean isCheckboxPartiallySelected(String name) {
        return checkbox(name).has(attribute(ARIA_CHECKED, MIXED_VALUE));
    }

    /**
     * Проверяет видимость указанного узла дерева.
     *
     * @param name название узла дерева
     * @return true, если узел видим пользователю
     */
    public boolean isNodeVisible(String name) {
        return node(name).isDisplayed();
    }

    /**
     * Проверяет видимость блока с результатами выбора чекбоксов.
     *
     * @return true, если блок результатов видим пользователю
     */
    public boolean isResultDisplayed() {
        return result.isDisplayed();
    }

    /**
     * Проверяет наличие указанного элемента в результатах выбора.
     *
     * @param name название элемента в блоке результатов
     * @return true, если элемент присутствует в результатах
     */
    public boolean isItemInResult(String name) {
        return result.$$(RESULT_ITEM_SELECTOR)
                .findBy(exactText(name))
                .exists();
    }
}
