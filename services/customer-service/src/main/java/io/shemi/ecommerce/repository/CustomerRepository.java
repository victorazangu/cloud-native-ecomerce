package io.shemi.ecommerce.repository;

import io.shemi.ecommerce.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,String> {
    public Customer findByEmail(String email);
}
