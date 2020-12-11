package cn.soft1010.java.springboot.restruction.model;

import java.math.BigDecimal;

/**
 * Created by zhangjifu on 2020/12/11.
 */
public class Income {
    private String month;
    private BigDecimal income;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
