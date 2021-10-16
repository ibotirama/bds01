package com.devsuperior.bds01.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	@Transactional(readOnly = true)
	public Page<EmployeeDTO> findAll(Pageable pageable) {
		Page<Employee> page = repository.findAll(pageable);
		List<EmployeeDTO> dtos = page.stream().map(entity -> new EmployeeDTO(entity)).collect(Collectors.toList());
		PageImpl<EmployeeDTO> pageSorted = new PageImpl<>(dtos, pageable, page.getTotalElements());
		
		return pageSorted;
	}

	@Transactional
	public EmployeeDTO insert(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		employee.setName(employeeDTO.getName());
		employee.setEmail(employeeDTO.getEmail());
		employee.setDepartment(new Department(employeeDTO.getId(), null));
		employee = repository.save(employee);

		return new EmployeeDTO(employee);
	}
	

}
