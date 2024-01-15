package com.graduatebackend.batch.listener;

import com.graduatebackend.batch.processor.PayrollItemProcessor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MonthlyPayrollSavedEventListener {

    @Autowired
    private PayrollItemProcessor payrollItemProcessor;

    @EventListener
    public void handleMonthlyPayrollSaved(MonthlyPayrollSavedEvent event) {
        Integer monthlyPayrollId = event.getMonthlyPayrollId();
        payrollItemProcessor.setMonthlyPayrollId(monthlyPayrollId);
    }
}

