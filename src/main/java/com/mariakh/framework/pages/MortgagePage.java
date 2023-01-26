package com.mariakh.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MortgagePage extends BasePage {

    @FindBy(xpath = "//h1[contains(@class, 'product-teaser-full-width__header')]")
    private WebElement title;

    @FindBy(xpath = "//iframe[@sandbox]")
    private WebElement mortgageIframe;

    @FindBy(xpath = "//div[@class='_2RwsL_oKFrEK9oh3oTaIT0']")
    private List<WebElement> checkBoxList;

    @FindBy(xpath = "//div[@data-test-id='realty-cost-input']//input")
    private WebElement houseCostInput;

    @FindBy(xpath = "//div[@data-test-id='initial-fee-input']//input")
    private WebElement initialFeeInput;

    @FindBy(xpath = "//label[contains(text(), 'Срок кредита')]/../input")
    private WebElement creditTermInput;

    @FindBy(xpath = "//div[@data-test-id='main-results-block']")
    private List<WebElement> mainResultBlockList;

    private Map<String, String> expectedValuesMap = new HashMap<>();

    @Step("Проверка перехода на ссылке из подменю.")
    public MortgagePage checkOpenMortgagePage() {
        assertEquals("https://www.sberbank.ru/ru/person/credits/home/buying_complete_house"
                , driverManager.getDriver().getCurrentUrl(), "Текущий url не соответствует ожидаемому.");
        return this;
    }

    @Step("Проверка доступности фрейма и переключение на него.")
    public MortgagePage checkFrameAndSwitch() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(mortgageIframe));
        return this;
    }

    @Step("Переключение чекбокса 'Страхование жизни и здоровья'.")
    public MortgagePage clickCheckbox() {
        for (WebElement elem : checkBoxList) {
            if (elem.findElement(By.xpath("./span[1]")).getText().equals("Страхование жизни и здоровья")) {
                WebElement checkBox = elem.findElement(By.xpath(".//input"));
                scrollToElementJs(checkBox);
                checkBox.click();
                wait.until(ExpectedConditions.attributeToBe(checkBox, "aria-checked", "false"));
            }
        }
        return this;
    }

    @Step("Заполнить поле '{fieldName}' значением - '{fieldValue}'")
    public MortgagePage fillFrameField(String fieldName, String fieldValue) {
        switch (fieldName) {
            case "Стоимость недвижимости":
                fillField(houseCostInput, fieldValue);
                break;
            case "Первоначальный взнос":
                fillField(initialFeeInput, fieldValue);
                break;
            case "Срок кредита":
                fillField(creditTermInput, fieldValue);
                break;
            default:
                Assertions.fail("Введено неверное имя поля для заполнения");
        }
        return this;
    }

    @Step("Собрать результирующие значения.")
    public MortgagePage prepareResultInfo() {
        WebElement resultBlock = getVisibleResultsBlock();
        scrollToElementJs(creditTermInput);
        getAndSaveResultInfo(resultBlock,"Ежемесячный платеж");
        getAndSaveResultInfo(resultBlock,"Сумма кредита");
        getAndSaveResultInfo(resultBlock,"Процентная ставка");
        getAndSaveResultInfo(resultBlock,"Необходимый доход");
        return this;
    }

    @Step("Проверить значение поля '{fieldName}', ожидаемое значение - '{value}'")
    public MortgagePage compareExpectedResult(String fieldName, String value) {
        waitStabilityPage(5000, 250);
        assertEquals(value, expectedValuesMap.get(fieldName), String.format("Значение поля '%s' не соответствует ожидаемому.", fieldName));
        return this;
    }

    private void fillField(WebElement inputElement, String value) {
        clickFieldAndClean(inputElement);
        waitStabilityPage(5000, 250);
        sendKeysByOneChar(value);
        waitStabilityPage(5000, 250);
        assertEquals(((Integer.parseInt(value) > 999) ? getFormattedString(value) : value)
                , inputElement.getAttribute("value"), "Значение в поле не соответствует введенному.");
    }

    private void getAndSaveResultInfo(WebElement resultBlock, String field) {

        String value = "";
        switch(field) {
            case "Ежемесячный платеж":
                value = resultBlock.findElement(By
                        .xpath(".//span[contains(text(), 'Ежемесячный платеж')]/..//span[contains(text(), '₽')]")).getText();
                break;
            case "Сумма кредита":
                value = resultBlock.findElement(By
                        .xpath(".//span[contains(text(), 'Сумма кредита')]/..//span[contains(text(), '₽')]")).getText();
                break;
            case "Процентная ставка":
                value = resultBlock.findElement(By
                        .xpath(".//span[contains(text(), 'Процентная ставка')]/../div/span[1]/span")).getText();
                break;
            case "Необходимый доход":
                value = resultBlock.findElement(By
                                .xpath("//div[@class='hint-target-0-9-4']//span[contains(text(), 'Необходимый доход')]/..//span[contains(text(), '₽')]"))
                        .getText();
        }
        expectedValuesMap.put(field, cleanString(value));
    }

    private WebElement getVisibleResultsBlock() {
        for (WebElement element : mainResultBlockList) {
            if (element.isDisplayed()) {
                return element;
            }
        }
        Assertions.fail("Не нашли видимый блок с итоговой информацией.");
        return null;
    }

    private void clickFieldAndClean(WebElement element) {
        element.click();
        element.sendKeys(Keys.HOME);
        element.sendKeys(Keys.chord(Keys.SHIFT, Keys.END));
        //element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
    }

    private void sendKeysByOneChar(String value) {
        String[] valueChars = value.split("");
        for (String valueChar : valueChars) {
            actions.sendKeys(valueChar).pause(clickDelay).build().perform();
        }
    }
}
