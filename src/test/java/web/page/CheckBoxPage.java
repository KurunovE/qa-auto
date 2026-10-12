package web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

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

    public void selectCheckbox(String name) {
        checkbox(name).click();
    }

    public void expandNode(String name) {
        SelenideElement branch = node(name).shouldBe(visible);
        if (!branch.has(attribute(ARIA_EXPANDED, TRUE_VALUE))) {
            branch.$(SWITCHER_CLOSE_SELECTOR).click();
            branch.shouldHave(attribute(ARIA_EXPANDED, TRUE_VALUE));
        }
    }

    public void expandAll() {
        // Selenide проксирует элементы: при каждом вызове tree.$(...)
        // происходит новый поиск в DOM, что идеально для динамических деревьев
        SelenideElement closeSwitcher = tree.$(SWITCHER_CLOSE_SELECTOR);
        while (closeSwitcher.exists()) {
            closeSwitcher.click();
        }
    }

    public void collapseAll() {
        ElementsCollection openSwitchers = tree.$$(SWITCHER_OPEN_SELECTOR);
        // Сворачиваем снизу вверх (last()), чтобы при исчезновении дочерних узлов
        // из DOM не ломать индексы оставшихся открытых элементов
        while (!openSwitchers.isEmpty()) {
            openSwitchers.last().click();
        }
    }

    public boolean isCheckboxSelected(String name) {
        return checkbox(name).has(attribute(ARIA_CHECKED, TRUE_VALUE));
    }

    public boolean isCheckboxPartiallySelected(String name) {
        return checkbox(name).has(attribute(ARIA_CHECKED, MIXED_VALUE));
    }

    public boolean isNodeVisible(String name) {
        return node(name).isDisplayed();
    }

    public boolean isResultDisplayed() {
        return result.isDisplayed();
    }

    public boolean isItemInResult(String name) {
        return result.$$(RESULT_ITEM_SELECTOR)
                .findBy(exactText(name))
                .exists();
    }
}