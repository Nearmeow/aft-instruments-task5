package com.mariakh.framework.tests;

import com.mariakh.framework.tests.base.BaseTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MyTest extends BaseTests {

    @Test
    @DisplayName("Проверка заполнения фрейма.")
    public void mainTest() {

        pageManager.getStartPage().closeCookies()
                .selectBaseMenu("Ипотека")
                .selectSubMenu("Ипотека на вторичное жильё")
                .checkOpenMortgagePage()
                .checkFrameAndSwitch()
                .clickCheckbox()
                .fillFrameField("Стоимость недвижимости", "5180000")
                .fillFrameField("Первоначальный взнос", "3058000")
                .fillFrameField("Срок кредита", "30")
                .prepareResultInfo()
                .compareExpectedResult("Ежемесячный платеж", "21664")
                .compareExpectedResult("Сумма кредита", "2122000")
                .compareExpectedResult("Процентная ставка", "11")
                .compareExpectedResult("Необходимый доход", "36829");
    }
}
