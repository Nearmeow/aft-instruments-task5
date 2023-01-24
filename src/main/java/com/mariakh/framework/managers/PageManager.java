package com.mariakh.framework.managers;

import com.mariakh.framework.pages.MortgagePage;
import com.mariakh.framework.pages.StartPage;

public class PageManager {

    private static PageManager instance;
    private StartPage startPage;
    private MortgagePage mortgagePage;

    private PageManager() {
    }

    public static PageManager getInstance() {
        if (instance == null) {
            instance = new PageManager();
        }
        return instance;
    }

    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    public MortgagePage getMortgagePage() {
        if (mortgagePage == null) {
            mortgagePage = new MortgagePage();
        }
        return mortgagePage;
    }
}
