package cn.soft1010.java.springboot.restruction.model;

import java.math.BigDecimal;

/**
 * Created by zhangjifu on 2020/12/11.
 */
public class IncomeDetail {

    private String day;
    private BigDecimal income;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
