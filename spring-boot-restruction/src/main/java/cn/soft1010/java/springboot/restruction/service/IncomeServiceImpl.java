package cn.soft1010.java.springboot.restruction.service;

import cn.soft1010.java.springboot.restruction.model.Income;
import cn.soft1010.java.springboot.restruction.model.IncomeDetail;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjifu on 2020/12/11.
 */
@Service
public class IncomeServiceImpl implements IncomeService {

    @Override
    public void generate(String month) {
        //do sth get month income
        Income income = getMonthIncome(month);
        //do sth persistent income
        saveIncome(income);
        //do sth 计算 每天收入
        List<IncomeDetail> list = genIncomeDetails(income);
        // do sth 持久化每天收到IncomeDetail
        saveIncomeDetails(list);
    }

    private Income getMonthIncome(String month) {
        //do sth get month income
        return new Income();
    }

    private void saveIncome(Income income) {
        //do sth persistent income
    }

    private List<IncomeDetail> genIncomeDetails(Income income) {
        //do sth 计算 每天收入
        List<IncomeDetail> list = new ArrayList<>();
        return list;
    }

    private void saveIncomeDetails(List<IncomeDetail> details) {
        // do sth 持久化每天收到IncomeDetail
    }


}
