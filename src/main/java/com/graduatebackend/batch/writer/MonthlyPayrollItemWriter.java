package com.graduatebackend.batch.writer;

import com.graduatebackend.batch.listener.MonthlyPayrollSavedEvent;
import com.graduatebackend.entity.MonthlyPayroll;
import com.graduatebackend.repository.MonthlyPayrollRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonthlyPayrollItemWriter implements ItemWriter<MonthlyPayroll> {

    @Autowired
    private MonthlyPayrollRepository monthlyPayrollRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Override
    public void write(List<? extends MonthlyPayroll> monthlyPayrolls) throws Exception {
        if (!monthlyPayrolls.isEmpty()) {
            MonthlyPayroll monthlyPayroll = monthlyPayrolls.get(0);
            MonthlyPayroll monthlyPayrollSaved = monthlyPayrollRepository.save(monthlyPayroll);
            Integer monthlyPayrollId = monthlyPayrollSaved.getMonthlyPayrollId();

            eventPublisher.publishEvent(new MonthlyPayrollSavedEvent(monthlyPayrollId));
        }
    }

}
