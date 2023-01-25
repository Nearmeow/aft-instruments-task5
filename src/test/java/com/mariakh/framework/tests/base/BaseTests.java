package com.mariakh.framework.tests.base;

import com.mariakh.framework.managers.DriverManager;
import com.mariakh.framework.managers.InitManager;
import com.mariakh.framework.managers.PageManager;
import com.mariakh.framework.utils.MyTestWatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MyTestWatcher.class)
public class BaseTests {
    protected PageManager pageManager = PageManager.getInstance();

    @BeforeAll
    public static void beforeAll() {
        InitManager.initFramework();
    }
    @BeforeEach
    public void before() {
        DriverManager.getInstance().getDriver().get("https://www.sberbank.ru/ru/person");
    }

    @AfterAll
    public static void afterAll() {
        InitManager.quitFramework();
    }
}
