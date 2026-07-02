package com.example.pagination.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.pagination.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);

	@Query("SELECT s FROM Employee s WHERE " +
		       "LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(s.surname) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(s.email) LIKE LOWER(CONCAT('%', :search, '%'))")
		Page<Employee> searchEmployee(@Param("search") String search, Pageable pageable);
}
