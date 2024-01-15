package com.graduatebackend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_position")
public class EmployeePosition extends BaseEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "EMPLOYEE_POSITION_ID")
	private Integer employeePositionId;
	@Basic
	@Column(name = "EMPLOYEE_POSITION_TITLE")
	private String employeePositionTitle;
	@Basic
	@Column(name = "EMPLOYEE_POSITION_DESCRIPTION")
	private String employeePositionDescription;

	@OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
	private List<Employee> employees;

	@OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
	private List<Salary> salaries;
}
