package com.mariakh.framework.pages;

import com.mariakh.framework.model.ResultInfo;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

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

    private Duration clickDelay = Duration.ofMillis(300);

    private ResultInfo expectedInfo;

    public MortgagePage checkOpenMortgagePage() {
        assertEquals("https://www.sberbank.ru/ru/person/credits/home/buying_complete_house"
                , driverManager.getDriver().getCurrentUrl(), "Текущий url не соответствует ожидаемому.");
        return this;
    }

    public MortgagePage checkFrameAndSwitch() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(mortgageIframe));
        return this;
    }

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

    public MortgagePage fillHouseCost(String costValue) {
        fillField(houseCostInput, costValue);
        return this;
    }

    public MortgagePage fillInitialFree(String feeValue) {
        fillField(initialFeeInput, feeValue);
        return this;
    }

    public MortgagePage fillCreditTerm(String termValue) {
        fillField(creditTermInput, termValue);
        return this;
    }

    private void fillField(WebElement inputElement, String value) {
        clickFieldAndClean(inputElement);
        sendKeysByOneChar(value);
        waitStabilityPage(5000, 250);
        assertEquals(((Integer.parseInt(value) > 999) ? getFormattedString(value) : value)
                , inputElement.getAttribute("value"));
    }

    public ResultInfo prepareResultInfo(WebElement resultBlock) {
        ResultInfo resultInfo = new ResultInfo();

        String monthlyPayment = resultBlock.findElement(By
                .xpath(".//span[contains(text(), 'Ежемесячный платеж')]/..//span[contains(text(), '₽')]")).getText();
        String creditSum = resultBlock.findElement(By
                .xpath(".//span[contains(text(), 'Сумма кредита')]/..//span[contains(text(), '₽')]")).getText();
        String interestRate = resultBlock.findElement(By
                .xpath(".//span[contains(text(), 'Процентная ставка')]/../div/span[1]/span")).getText();
        String requiredIncome = resultBlock.findElement(By
                        .xpath("//div[@class='hint-target-0-9-4']//span[contains(text(), 'Необходимый доход')]/..//span[contains(text(), '₽')]"))
                .getText();

        resultInfo.setCreditSum(cleanString(creditSum));
        resultInfo.setMonthlyPayment(cleanString(monthlyPayment));
        resultInfo.setRequiredIncome(cleanString(requiredIncome));
        resultInfo.setInterestRate(cleanString(interestRate));

        return resultInfo;
    }

    public MortgagePage fillExpectedInfo(String monthlyPayment, String creditSum, String interestRate, String requiredIncome) {
        expectedInfo = new ResultInfo(monthlyPayment, creditSum, interestRate, requiredIncome);
        return this;
    }
    public void compareTotalInfo() {
        waitStabilityPage(5000, 250);
        WebElement mainResults = getVisibleResultsBlock();
        scrollToElementJsTop(mainResults);
        ResultInfo info = prepareResultInfo(mainResults);

        Assertions.assertAll(
                () -> assertEquals(expectedInfo.getCreditSum(), info.getCreditSum(), "Сумма кредита не совпадает с ожидаемой."),
                () -> assertEquals(expectedInfo.getMonthlyPayment(), info.getMonthlyPayment(), "Ежемесячный платёж не совпадает с ожидаемым."),
                () -> assertEquals(expectedInfo.getRequiredIncome(), info.getRequiredIncome(), "Необходимый доход не совпадает с ожидаемым."),
                () -> assertEquals(expectedInfo.getInterestRate(), info.getInterestRate(), "Процентная ставка не совпадает с ожидаемой.")
        );
    }

    public WebElement getVisibleResultsBlock() {
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
        element.sendKeys(Keys.BACK_SPACE);
    }

    private void sendKeysByOneChar(String value) {
        String[] valueChars = value.split("");
        for (String valueChar : valueChars) {
            actions.sendKeys(valueChar).pause(clickDelay).build().perform();
        }
    }
}
