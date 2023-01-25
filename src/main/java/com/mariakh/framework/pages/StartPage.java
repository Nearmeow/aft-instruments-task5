package com.mariakh.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class StartPage extends BasePage {

    @FindBy(xpath = "//button[@class='kitt-cookie-warning__close']")
    private WebElement cookiesCloseButton;

    @FindBy(xpath = "//li[contains(@class,'kitt-top-menu__item_first')]/a[@role or @aria-expanded]")
    private List<WebElement> baseMenuList;

    @FindBy(xpath = "//a[@data-cga_click_top_menu]")
    private List<WebElement> dropDownMenuList;

    @Step("Закрытие окна Cookies")
    public StartPage closeCookies() {
        wait.until(ExpectedConditions.elementToBeClickable(cookiesCloseButton)).click();
        return this;
    }

    @Step("Клик на базовое меню '{baseMenuName}'")
    public StartPage selectBaseMenu(String baseMenuName) {
        for (WebElement menuItem : baseMenuList) {
            if (menuItem.getText().trim().equalsIgnoreCase(baseMenuName)) {
                wait.until(ExpectedConditions.elementToBeClickable(menuItem)).click();
                return this;
            }
        }
        Assertions.fail("Меню '" + baseMenuName + "' не было найдено на стартовой странице!");
        return this;
    }

    @Step("Клик на подменю '{nameSubMenu}'")
    public MortgagePage selectSubMenu(String nameSubMenu) {
        for (WebElement menuItem : dropDownMenuList) {
            if (menuItem.getText().equalsIgnoreCase(nameSubMenu)) {
                wait.until(ExpectedConditions.elementToBeClickable(menuItem)).click();
                return pageManager.getMortgagePage().checkOpenMortgagePage();  //.checkOpenInsurancePage();
            }
        }
        Assertions.fail("Подменю '" + nameSubMenu + "' не было найдено на стартовой странице!");
        return pageManager.getMortgagePage();
    }
}
