package com.graduatebackend.batch.writer;

import com.graduatebackend.entity.EmployeePayroll;
import com.graduatebackend.repository.EmployeePayrollRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PayrollItemWriter implements ItemWriter<EmployeePayroll> {

    @Autowired
    private EmployeePayrollRepository employeePayrollRepository;

    @Override
    public void write(List<? extends EmployeePayroll> items) throws Exception {
        employeePayrollRepository.saveAll(items);
    }
}
