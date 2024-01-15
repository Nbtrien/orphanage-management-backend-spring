package com.graduatebackend.batch.processor;

import com.graduatebackend.entity.Employee;
import com.graduatebackend.entity.EmployeePayroll;
import com.graduatebackend.entity.MonthlyPayroll;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Date;
import java.util.Calendar;

public class MonthlyPayrollItemProcessor implements ItemProcessor<MonthlyPayroll, MonthlyPayroll> {
    @Override
    public MonthlyPayroll process(MonthlyPayroll item) throws Exception {
        MonthlyPayroll monthlyPayroll = new MonthlyPayroll();
        monthlyPayroll.setMonth(getMonth());
        monthlyPayroll.setYear(getYear());
        monthlyPayroll.setStartDate(getFirstDayOfMonth());
        monthlyPayroll.setEndDate(getLastDayOfMonth());
        monthlyPayroll.setPayrollStatus(0);
        return monthlyPayroll;
    }

    private Date getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long millis = calendar.getTimeInMillis();
        return new Date(millis);
    }

    private int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    private int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    private Date getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long millis = calendar.getTimeInMillis();
        return new Date(millis);
    }
}
