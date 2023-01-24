package com.mariakh.framework.model;

public class ResultInfo {

    private String monthlyPayment;
    private String creditSum;
    private String interestRate;
    private String requiredIncome;

    public ResultInfo(String monthlyPayment, String creditSum, String interestRate, String requiredIncome) {
        this.monthlyPayment = monthlyPayment;
        this.creditSum = creditSum;
        this.interestRate = interestRate;
        this.requiredIncome = requiredIncome;
    }

    public ResultInfo() {
    }

    public String getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(String monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public String getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(String creditSum) {
        this.creditSum = creditSum;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getRequiredIncome() {
        return requiredIncome;
    }

    public void setRequiredIncome(String requiredIncome) {
        this.requiredIncome = requiredIncome;
    }
}
