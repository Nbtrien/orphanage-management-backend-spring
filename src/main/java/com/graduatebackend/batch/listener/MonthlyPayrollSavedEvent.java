package com.graduatebackend.batch.listener;

import org.springframework.context.ApplicationEvent;

public class MonthlyPayrollSavedEvent extends ApplicationEvent {

    private final Integer monthlyPayrollId;

    public MonthlyPayrollSavedEvent(Integer monthlyPayrollId) {
        super(monthlyPayrollId);  // Sử dụng monthlyPayrollId làm nguồn của sự kiện
        this.monthlyPayrollId = monthlyPayrollId;
    }

    public Integer getMonthlyPayrollId() {
        return monthlyPayrollId;
    }
}
