package com.softwareEngeneering.ecommerce.repository;

import com.softwareEngeneering.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
