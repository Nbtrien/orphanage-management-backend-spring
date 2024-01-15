package com.graduatebackend.batch.processor;

import com.graduatebackend.entity.Employee;
import com.graduatebackend.entity.EmployeePayroll;
import com.graduatebackend.entity.MonthlyPayroll;
import org.springframework.batch.item.ItemProcessor;

import java.sql.Date;
import java.util.Calendar;


public class PayrollItemProcessor implements ItemProcessor<Employee, EmployeePayroll> {

    private Integer monthlyPayrollId;

    public void setMonthlyPayrollId(Integer monthlyPayrollId) {
        this.monthlyPayrollId = monthlyPayrollId;
    }

    @Override
    public EmployeePayroll process(Employee employee) {
        EmployeePayroll payroll = new EmployeePayroll();
        payroll.setEmployee(employee);
        payroll.setMonthlyPayroll(MonthlyPayroll.builder().monthlyPayrollId(monthlyPayrollId).build());

        // check is hire date after current date
        if (isHireDateAfterCurrentDate(employee.getHireDate())) {
            // set payroll start date is hire date if hire date after current date
            payroll.setPayrollStartDate(employee.getHireDate());
        } else {
            // set payroll start date is current date if hire date before current date
            payroll.setPayrollStartDate(getFirstDayOfMonth());
        }
        payroll.setPayrollEndDate(getLastDayOfMonth());
        payroll.setDateOfPayment(null);
        payroll.setPayrollStatus(0);
        payroll.setSalary(employee.getSalary());
        payroll.setEmployeePayrollMonth(getMonth());
        payroll.setEmployeePayrollYear(getYear());
        payroll.setPayrollAmount((float) 0);
        payroll.setPayrollSalary(employee.getSalary().getSalaryAmount());
        return payroll;
    }

    private boolean isHireDateAfterCurrentDate(Date hireDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long millis = calendar.getTimeInMillis();
        Date currentDate = new Date(millis);

        return hireDate.after(currentDate);
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
