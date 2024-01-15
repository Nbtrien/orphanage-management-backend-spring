package com.graduatebackend.batch.config;

import com.graduatebackend.batch.listener.MonthlyPayrollSavedEventListener;
import com.graduatebackend.batch.processor.MonthlyPayrollItemProcessor;
import com.graduatebackend.batch.processor.PayrollItemProcessor;
import com.graduatebackend.batch.writer.MonthlyPayrollItemWriter;
import com.graduatebackend.batch.writer.PayrollItemWriter;
import com.graduatebackend.entity.Employee;
import com.graduatebackend.entity.EmployeePayroll;
import com.graduatebackend.entity.MonthlyPayroll;
import com.graduatebackend.repository.EmployeePayrollRepository;
import com.graduatebackend.repository.EmployeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class PayrollBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeePayrollRepository employeePayrollRepository;

    @Bean
    public ItemReader<Employee> employeeItemReader() {
        List<Employee> employees = employeeRepository.findByHireDateBeforeNextMonth();
        return new ListItemReader<>(employees);
    }

    @Bean
    public ItemProcessor<Employee, EmployeePayroll> payrollItemProcessor() {
        return new PayrollItemProcessor();
    }

    @Bean
    public ItemWriter<EmployeePayroll> payrollItemWriter() {
        return new PayrollItemWriter();
    }

    @Bean
    public ItemProcessor<MonthlyPayroll, MonthlyPayroll> monthlyPayrollItemProcessor() {
        return new MonthlyPayrollItemProcessor();
    }

    @Bean
    public ItemWriter<MonthlyPayroll> monthlyPayrollItemWriter() {
        return new MonthlyPayrollItemWriter();
    }


    @Bean
    public Step addMonthlyPayrollStep(
            ItemProcessor<MonthlyPayroll, MonthlyPayroll> monthlyPayrollItemProcessor,
            ItemWriter<MonthlyPayroll> monthlyPayrollItemWriter,
            MonthlyPayrollSavedEventListener monthlyPayrollListener) {
        return stepBuilderFactory.get("addMonthlyPayrollStep")
                .<MonthlyPayroll, MonthlyPayroll>chunk(1)
                .reader(new ListItemReader<>(Collections.singletonList(new MonthlyPayroll())))
                .processor(monthlyPayrollItemProcessor)
                .writer(monthlyPayrollItemWriter)
                .build();
    }

    @Bean
    public Step employeePayrollStep(ItemReader<Employee> employeeItemReader,
                                    ItemProcessor<Employee, EmployeePayroll> employeePayrollItemProcessor,
                                    ItemWriter<EmployeePayroll> employeePayrollItemWriter) {
        return stepBuilderFactory.get("employeePayrollStep")
                .<Employee, EmployeePayroll>chunk(10)
                .reader(employeeItemReader)
                .processor(employeePayrollItemProcessor)
                .writer(employeePayrollItemWriter)
                .build();
    }

    @Bean("employeePayrollJob")
    public Job employeePayrollJob(Step employeePayrollStep, Step addMonthlyPayrollStep) {
        return jobBuilderFactory.get("employeePayrollJob")
                .incrementer(new RunIdIncrementer())
                .start(addMonthlyPayrollStep)
                .next(employeePayrollStep)
                .build();
    }
}
